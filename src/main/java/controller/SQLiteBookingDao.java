package controller;

import controller.protocols.BookingDao;
import controller.protocols.DatabaseController;
import model.Booking;
import model.BookingSummary;
import model.NewBookingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class SQLiteBookingDao implements BookingDao {

    private static final Logger logger =
            LogManager.getLogger(SQLiteBookingDao.class);

    private DatabaseController databaseController;

    SQLiteBookingDao(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    @Override
    public ArrayList<Booking> getBookingsByDateForEmployee(int employeeID, LocalDate localDate, int businessID) {
        ResultSet resultSet;
        String query;

        if(employeeID == 0)
            return getCommonBookingsByDate(localDate, businessID);

        int fullDate = localDate.getYear() * 10000 +
          localDate.getMonth().getValue() * 100 + localDate.getDayOfMonth();

        logger.debug(String.format("Get all bookings for EmployeeID: %d Date:" +
            " %d", employeeID, fullDate));

        query = "SELECT * FROM booking " +
                "WHERE employeeID = ? AND date = ?";

        resultSet = databaseController.select(query, new ArrayList<>(
                Arrays.asList(employeeID, fullDate)));

        ArrayList<Booking> bookings = new ArrayList<>();
        while (databaseController.resultSetHasNext(resultSet)) {
            bookings.add(extractBooking(resultSet));
        }
        databaseController.closeResultSet(resultSet);
        return bookings;
    }

    private ArrayList<Booking> getCommonBookingsByDate(LocalDate localDate,
      int businessID) {
        ResultSet resultSet;
        String query;

        int fullDate = localDate.getYear() * 10000 +
          localDate.getMonth().getValue() * 100 + localDate.getDayOfMonth();

        query = "SELECT *, min(duration) AS minDuration FROM booking " +
          "WHERE date = ?" +
          "AND businessID = ? " +
          "GROUP BY date, time " +
          "HAVING COUNT(*) >= 2";

        resultSet = databaseController.select(query, new ArrayList<>(
          Arrays.asList(fullDate, businessID)));

        ArrayList<Booking> bookings = new ArrayList<>();
        while (databaseController.resultSetHasNext(resultSet)) {
            bookings.add(extractBooking(resultSet));
            databaseController.getInt(resultSet, "minDuration");
        }
        databaseController.closeResultSet(resultSet);
        return bookings;
    }

    private Booking extractBooking(ResultSet resultSet) {
        int id = databaseController.getInt(resultSet, "id");
        int customerID = databaseController.getInt(resultSet, "customerID");
        int employeeID = databaseController.getInt(resultSet, "employeeID");
        int serviceID = databaseController.getInt(resultSet, "serviceID");
        int duration = databaseController.getInt(resultSet, "duration");
        int date = databaseController.getInt(resultSet, "date");
        int time = databaseController.getInt(resultSet, "time");
        int businessID = databaseController.getInt(resultSet, "businessID");

        return new Booking(id, customerID, employeeID, serviceID,  duration, date, time, businessID);
    }

    @Override
    public boolean addBooking(NewBookingHandler bookingHandler) {
        boolean result = false;
        String query;
        ArrayList<Integer> args = new ArrayList<>();

        // If no specific employee is selected, select one at random that is
        // able to, and add the booking
        if(bookingHandler.employee().id == 0) {
            query = "INSERT INTO `booking` " +
              "(`customerID`,`employeeID`,`serviceID`,`duration`,`date`," +
              "`time`,`businessID`)" +
              "VALUES (?, " +
                "(SELECT employee.id FROM employee " +
                "WHERE employee.id NOT IN " +
                  "(SELECT employee.id FROM employee, booking " +
                  "WHERE employee.id = booking.employeeID " +
                  "AND booking.date = ? " +
                  "AND booking.time >= ? " +
                  "AND booking.time <= ?) " +
                "AND employee.id NOT IN " +
                  "(SELECT employee.id FROM employee, booking " +
                  "WHERE employee.id = booking.employeeID " +
                  "AND booking.date = ? " +
                  "AND booking.time < ? " +
                  "AND booking.time + booking.duration > ?) " +
                "AND employee.id IN " +
                  "(SELECT employeeID FROM workingTimes " +
                  "WHERE day = ? " +
                  "AND start BETWEEN ? AND ? " +
                  "GROUP BY employeeID " +
                  "HAVING COUNT(*) = ?) " +
                "ORDER BY RANDOM() LIMIT 1), " +
              "?, ?, ?, ?, ?)";

            args.addAll(Arrays.asList(
              bookingHandler.customerID,
              bookingHandler.fullDate(),
              bookingHandler.time().asInt(),
              bookingHandler.time().asInt() +
                bookingHandler.serviceDuration() - 30,
              bookingHandler.fullDate(),
              bookingHandler.time().asInt(),
              bookingHandler.time().asInt(),
              bookingHandler.date().getDayOfWeek().getValue(),
              bookingHandler.time().asInt(),
              bookingHandler.time().asInt() +
                bookingHandler.serviceDuration() - 30,
              bookingHandler.serviceDuration() / 30,
              bookingHandler.serviceID(),
              bookingHandler.serviceDuration(),
              bookingHandler.fullDate(),
              bookingHandler.time().asInt(),
              bookingHandler.businessID));
        }
        else {
            query = "INSERT INTO `booking` " +
              "(`customerID`,`employeeID`,`serviceID`,`duration`,`date`," +
              "`time`,`businessID`)" +
              "VALUES (?, ?, ?, ?, ?, ?,?)";
            args.addAll(Arrays.asList(
              bookingHandler.customerID,
              bookingHandler.employee().id,
              bookingHandler.serviceID(),
              bookingHandler.serviceDuration(),
              bookingHandler.fullDate(),
              bookingHandler.time().asInt(),
              bookingHandler.businessID));
        }

        if (databaseController.insert(query, args) > 0) {
            result = true;
        }

        return result;
    }

    @Override
    public ArrayList<BookingSummary> getBookings(LocalDate startDate,
      LocalDate endDate, int businessID) {
        ResultSet resultSet;
        String query;

        int fullStartDate = startDate.getYear() * 10000 +
          startDate.getMonth().getValue() * 100 + startDate.getDayOfMonth();
        int fullEndDate = endDate.getYear() * 10000 +
          endDate.getMonth().getValue() * 100 + endDate.getDayOfMonth();

        query = "SELECT booking.id, booking.time, booking.date, " +
          "customer.firstName AS 'customerFirstName', " +
          "employee.firstName AS 'employeeFirstName', " +
          "employee.lastName AS 'employeeLastName', " +
          "service.name AS 'serviceName', " +
          "booking.duration AS 'serviceDuration' " +
          "FROM customer, booking, service, employee " +
          "WHERE booking.customerID = customer.id " +
          "AND booking.serviceID = service.id " +
          "AND booking.employeeID = employee.id " +
          "AND date >= ? AND date <= ? " +
          "AND booking.businessID = ? " +
          "ORDER BY date, time";

        resultSet = databaseController.select(query, new ArrayList<>(
          Arrays.asList(fullStartDate, fullEndDate, businessID)));

        ArrayList<BookingSummary> bookings = new ArrayList<>();
        if(resultSet != null) {
            while (databaseController.resultSetHasNext(resultSet)) {
                bookings.add(extractBookingSummary(resultSet));
            }
            databaseController.closeResultSet(resultSet);
        }
        return bookings;
    }

    @Override
    public ArrayList<BookingSummary> getBookings(int customerID,
      LocalDate startDate, LocalDate endDate, int businessID) {
        ResultSet resultSet;
        String query;

        int fullStartDate = startDate.getYear() * 10000 +
          startDate.getMonth().getValue() * 100 + startDate.getDayOfMonth();
        int fullEndDate = endDate.getYear() * 10000 +
          endDate.getMonth().getValue() * 100 + endDate.getDayOfMonth();

        query = "SELECT booking.id, booking.time, booking.date, " +
          "customer.firstName AS 'customerFirstName', " +
          "employee.firstName AS 'employeeFirstName', " +
          "employee.lastName AS 'employeeLastName', " +
          "service.name AS 'serviceName', " +
          "booking.duration AS 'serviceDuration' " +
          "FROM customer, booking, service, employee " +
          "WHERE booking.customerID = customer.id " +
          "AND booking.serviceID = service.id " +
          "AND booking.employeeID = employee.id " +
          "AND booking.customerID = ?" +
          "AND date >= ? AND date <= ? " +
          "AND booking.businessID = ? " +
          "ORDER BY date, time";

        resultSet = databaseController.select(query, new ArrayList<>(
          Arrays.asList(customerID, fullStartDate, fullEndDate, businessID)));

        ArrayList<BookingSummary> bookings = new ArrayList<>();
        if(resultSet != null) {
            while (databaseController.resultSetHasNext(resultSet)) {
                bookings.add(extractBookingSummary(resultSet));
            }
            databaseController.closeResultSet(resultSet);
        }
        logger.info("Customer bookings size: "+bookings.size());
        return bookings;
    }

    private BookingSummary extractBookingSummary(ResultSet resultSet) {
        int id = databaseController.getInt(resultSet, "id");
        String customerFirstName = databaseController
          .getString(resultSet, "customerFirstName");
        int date = databaseController.getInt(resultSet, "date");
        String employeeFirstName = databaseController.getString(resultSet,
          "employeeFirstName");
        String employeeLastName = databaseController.getString(resultSet,
          "employeeLastName");
        int time = databaseController.getInt(resultSet, "time");
        String serviceName = databaseController.getString(resultSet,
          "serviceName");
        int duration = databaseController.getInt(resultSet,
          "serviceDuration");

        LocalDate localDate = LocalDate.of(date/10000, (date/100)%100,
          date%100);
        return new BookingSummary(id,
          customerFirstName,
          localDate.toString(),
          employeeFirstName+" "+employeeLastName,
          time,
          serviceName + " - "+ duration+" mins.");
    }

    @Override
    public Booking getBooking(int bookingID) {
        Booking booking;
        ResultSet resultSet;
        String query;

        query = "SELECT * FROM booking WHERE id = ?";

        resultSet = databaseController.select(query, new ArrayList<>(
                Arrays.asList(bookingID)));

        booking = extractBooking(resultSet);
        databaseController.closeResultSet(resultSet);

        return booking;
    }

    @Override
    public boolean deleteBooking(int bookingID) {
        boolean result = false;
        String query;

        query = "DELETE FROM `booking` WHERE `id` = ?";

        if (databaseController.insert(query, new
          ArrayList<>(Arrays.asList(bookingID))) > 0)
            result = true;

        return result;
    }
}
