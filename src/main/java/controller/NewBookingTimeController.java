package controller;

import controller.protocols.*;
import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.protocols.NewBookingTimeView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewBookingTimeController extends BannerViewController implements TimeSelectController {

    private static final Logger logger =
      LogManager.getLogger(NewBookingTimeController.class);

    protected ViewController viewController;
    protected NewBookingTimeView view;
    protected NewBookingHandler newBookingHandler;
    protected ServiceDao serviceDao;
    protected EmployeeDao employeeDao;
    protected WorkingTimesDao workingTimesDao;
    protected BookingDao bookingDao;
    protected BusinessDao businessDao;
    protected PermissionManager permissionManager;

    NewBookingTimeController(ViewController viewController,
      NewBookingTimeView view, NewBookingHandler newBookingHandler,
      DaoManager daoManager, PermissionManager permissionManager) {
        super(view, permissionManager.getLoggedInUser()
          .businessID());
        this.viewController = viewController;
        this.view = view;
        this.newBookingHandler = newBookingHandler;
        this.serviceDao = daoManager.serviceDao;
        this.employeeDao = daoManager.employeeDao;
        this.workingTimesDao = daoManager.workingTimesDao;
        this.bookingDao = daoManager.bookingDao;
        this.businessDao = daoManager.businessDao;
        this.permissionManager = permissionManager;
    }

    /**
     * Populate the view with everything that is static, then update view
     * with everything dynamic. This is only run once when the view is created.
     * Sets the service label and Employee dropdown list.
     */
    @Override
    public void populateView() {
        super.populateView();
        int businessID = permissionManager.getLoggedInUser().businessID();
        int serviceID = newBookingHandler.serviceID();
        Service service = serviceDao.getService(serviceID);
        String serviceName = service.name();
        List<EmployeeRecord> employeeList;
        List<EmployeeRecord> allowedEmployees = new ArrayList<>();

        // Build a list of employees capable of providing the service
        employeeList = employeeDao.getAllWorkingEmployees(businessID);
        for(EmployeeRecord employee : employeeList) {
            if(serviceDao.canDoService(employee, serviceID))
                allowedEmployees.add(employee);
        }
        // Add "Anyone" option if more than 1 employee is capable
        if(allowedEmployees.size() > 1) {
            allowedEmployees.add(0, new EmployeeRecord(0, "Anyone", "", businessID));
            view.setEmployee(allowedEmployees);
        }
        else if(allowedEmployees.size() == 1) {
            view.setEmployee(allowedEmployees.get(0).toString());
        }
        else {
            viewController.gotoError("Sorry, this service is currently " +
              "unavailable at this time.");
        }
        if(newBookingHandler.date() != null) {
            view.setDate(newBookingHandler.date());
            view.setMessage(null);
        }
        if(!allowedEmployees.isEmpty()) {
            newBookingHandler.setEmployee(allowedEmployees.get(0));
        }
        view.setService(String.format("%s - %s mins.", serviceName,
          newBookingHandler.serviceDuration()));
        updateView();
    }

    /**
     * Updates the view with anything that's dynamic.
     */
    private void updateView() {
        LocalDate currentDate = newBookingHandler.date();
        if(currentDate != null) {
            // Set the day of month label, adjusting case
            String currentMonth = adjustCase(currentDate.getMonth().toString());
            String currentDay = adjustCase(currentDate.getDayOfWeek().toString());
            view.setDayOfMonth(currentDay + " " + currentMonth +
              " " + currentDate.getDayOfMonth());

            // Get available times for current employee
            TimeOfDay openTime = businessDao.getOpeningTime(permissionManager
              .getLoggedInUser().businessID(), currentDate.getDayOfWeek()
              .getValue());
            TimeOfDay closeTime = businessDao.getClosingTime(permissionManager
              .getLoggedInUser().businessID(), currentDate.getDayOfWeek()
              .getValue());

            ArrayList<TimeOfDay> availableTimes = getEmployeeAvailability();
            Collections.sort(availableTimes);
            view.displayDay(currentDate, openTime, closeTime, availableTimes,
              newBookingHandler.time());
            view.setNextDayOptionVisibility(findAvailableDay(+1, false));
            view.setPreviousDayOptionVisibility(findAvailableDay(-1, false));
        }
    }

    private String adjustCase(String s) {
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }

    /**
     * Sets the selected time for an appointment, or removes the time if the
     * selection is identical to currently selected date/time.
     * @param time The start time of the booking.
     */
    @Override
    public void setTime(int time) {
        boolean setNewTime = false;
        TimeOfDay selectedTime = new TimeOfDay(time);
        if (newBookingHandler.time() != null) {
            if (selectedTime.asInt() == newBookingHandler.time().asInt()) {
                view.setSubmitDisable(true);
                newBookingHandler.setTime(null);
            }
            else
                setNewTime = true;
        }
        else
            setNewTime = true;

        if(setNewTime) {
            newBookingHandler.setTime(selectedTime);
            view.setSubmitDisable(false);
        }
        updateView();
    }

    public void setDate(LocalDate date) {
        newBookingHandler.setBookingDate(date);
        view.setMessage(null);
        clearSelectionUpdateView();
    }

    /**
     * Submits the selected date / time for the booking and moves to the
     * success view.
     */
    public void submit() {
        logger.debug("Booking Date: " + newBookingHandler.date().toString());
        logger.debug("Booking Time: " + newBookingHandler.time().asInt());
        logger.debug("Booking Service: " + newBookingHandler.serviceID());
        logger.debug("Booking Duration: " +
          newBookingHandler.serviceDuration());
        logger.debug("Booking Employee: " +
          newBookingHandler.employee().toString());
        // TODO: Add check to validate booking time is still available
        if(bookingDao.addBooking(newBookingHandler)) {
            viewController.gotoNewBookingSuccess(newBookingHandler);
        }
        else {
            viewController.gotoError("Sorry, there was an error booking your " +
              "appointment :(");
        }
    }

    /**
     * Move forward one day until reaching a day with available times.
     */
    public void gotoNextDay() {
        findAvailableDay(+1, true);
        view.setPreviousDayOptionVisibility(true);
        clearSelectionUpdateView();
    }

    /**
     * Move backward one day until reaching a day with available times.
     */
    public void gotoPreviousDay() {
        findAvailableDay(-1, true);
        clearSelectionUpdateView();
    }

    private boolean findAvailableDay(int delta, boolean move) {
        EmployeeRecord employee = newBookingHandler.employee();
        LocalDate currentDate = newBookingHandler.date();
        LocalDate now = LocalDate.now();
        for(int i = 1; i <= 7; i++) {
            if(delta > 0) {
                currentDate = currentDate.plusDays(1);
            }
            else if(delta < 0) {
                if(currentDate.minusDays(1).isBefore(now)) {
                    break;
                }
                currentDate = currentDate.minusDays(1);
            }

            ArrayList<TimeOfDay> availableTimes =
              availableTimesByDateForEmployee(employee.id, currentDate,
                newBookingHandler.serviceDuration());
            if(!availableTimes.isEmpty()) {
                if(move) {
                    newBookingHandler.setBookingDate(currentDate);
                    view.setDate(currentDate);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Switch to another employee. This is only capable when the customer
     * selects "Anyone" as their massage therapist in a previous screen.
     * @param employee The employee selected in the employee ComboBox.
     */
    public void setEmployee(EmployeeRecord employee) {
        newBookingHandler.setEmployee(employee);
        clearSelectionUpdateView();
    }

    private void clearSelectionUpdateView() {
        newBookingHandler.setTime(null);
        view.setSubmitDisable(true);
        updateView();
    }

    /**
     * Provides the availability for the current employee.
     * @return Returns a list of TimeOfDay objects with all available times
     * for the current employee.
     */
    private ArrayList<TimeOfDay> getEmployeeAvailability() {
        EmployeeRecord employee = newBookingHandler.employee();
        LocalDate currentDate = newBookingHandler.date();
        int duration = newBookingHandler.serviceDuration();

        return availableTimesByDateForEmployee(employee.id, currentDate,
          duration);
    }


    /**
     * Algorithm for determining available work times of employees based on
     * the date and duration of the desired booking.
     * @param employeeID The potential employee for the booking.
     * @param date The potential date for the booking.
     * @param duration The duration of the service.
     * @return Returns a list of TimeOfDay objects, each representing a 30
     * min block of which the booking can be booked for.
     */
    private ArrayList<TimeOfDay> availableTimesByDateForEmployee(
      int employeeID, LocalDate date, int duration) {

        final int HALF_HOUR = 30;
        final int CLOSE_TIME = 1050;

        ArrayList<TimeOfDay> availableTimes = new ArrayList<>();
        ArrayList<TimeOfDay> workTimes = new ArrayList<>();
        ArrayList<TimeOfDay> unavailableTimes = new ArrayList<>();
        ArrayList<TimeOfDay> endOfShiftTimes = new ArrayList<>();

        // 1. Get all working times of an employee for the day
        ArrayList<Integer> employeeWorkTimes =
          workingTimesDao.getEmployeeWorkTimes(employeeID,
          date.getDayOfWeek().getValue());

        // 1.1 Add all working times to array, Add all working blocks that
        // don't have a block after them to endOfShiftTimes array
        for(int i = 0; i < employeeWorkTimes.size(); i++) {
            int workTime = employeeWorkTimes.get(i);
            if(i + 1 < employeeWorkTimes.size()) {
                if (workTime + HALF_HOUR != employeeWorkTimes.get(i + 1))
                    endOfShiftTimes.add(new TimeOfDay(workTime));
            }
            else {
                if(workTime+HALF_HOUR != CLOSE_TIME)
                    endOfShiftTimes.add(new TimeOfDay(workTime+HALF_HOUR));
            }
            workTimes.add(new TimeOfDay(workTime));
        }

        // 2. Get all bookings for employee on selected date

        ArrayList<Booking> bookings =
          bookingDao.getBookingsByDateForEmployee(employeeID, date, permissionManager.getLoggedInUser().businessID());

        for(Booking booking : bookings) {
            TimeOfDay bookingStartTime = new TimeOfDay(booking.time);
            unavailableTimes.add(bookingStartTime);

            // 3. Remove unavailable blocks surrounding bookings

            if(booking.duration > HALF_HOUR) {
                int blocks = booking.duration / HALF_HOUR;
                for (int i = 1; i < blocks; i++) {

                    // 3.1 Add following time blocks as unavailable

                    TimeOfDay bookingTimeBlockForward = new TimeOfDay(
                      bookingStartTime.asInt());
                    unavailableTimes.add(bookingTimeBlockForward.plusMinutes(
                      HALF_HOUR * i));

                    // 3.2 Add preceding time blocks for bookings unavailable

                    TimeOfDay bookingTimeBlockBackward =
                      new TimeOfDay(bookingStartTime.asInt());
                    unavailableTimes.add(bookingTimeBlockBackward
                      .plusMinutes(-HALF_HOUR * i));
                }
            }
        }

        if(duration > HALF_HOUR) {
            int blocks = duration / HALF_HOUR;
            for (int i = 1; i < blocks; i++) {
                // 3.3 Add end of day time blocks as unavailable

                TimeOfDay endOfDay = new TimeOfDay(CLOSE_TIME);
                unavailableTimes.add(endOfDay.plusMinutes(-HALF_HOUR * i));

                // 3.4 Add before end of shift blocks as unavailable

                for(TimeOfDay workTime : endOfShiftTimes) {
                    unavailableTimes.add(workTime
                      .plusMinutes(-HALF_HOUR * i));
                }
            }
        }

        // 4. Cross reference available times with unavailable times

        for(TimeOfDay workTime : workTimes) {
            boolean found = false;
            for(TimeOfDay unavailableTime : unavailableTimes) {
                if (unavailableTime.asInt() == workTime.asInt()) {
                    found = true;
                }
            }
            if(!found)
                availableTimes.add(workTime);
        }

        for(Integer workTime : employeeWorkTimes)
            logger.debug("Working Time: " + workTime);
        for(TimeOfDay unavailableTime : unavailableTimes)
            logger.debug("Booking: "+ unavailableTime.asInt());
        for(TimeOfDay availableTime : availableTimes)
            logger.debug("Available Time: " + availableTime.asInt());
        for(TimeOfDay endOfShiftTime : endOfShiftTimes)
            logger.debug("End Of Shift Time: " + endOfShiftTime.asInt());

        return availableTimes;
    }

    public void goBack() {
        viewController.gotoNewBookingService(newBookingHandler);
    }
}
