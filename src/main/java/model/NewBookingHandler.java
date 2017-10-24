package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class NewBookingHandler {

    private static final Logger logger =
      LogManager.getLogger(NewBookingHandler.class);

    public final int customerID;
    public final int businessID;
    private LocalDate date;
    private int serviceID;
    private int serviceDuration;
    private EmployeeRecord employee;
    private TimeOfDay time;

    public NewBookingHandler(int customerID, int businessID) {
        this.customerID = customerID;
        this.businessID = businessID;
    }

    public NewBookingHandler(int customerID, Booking booking) {
        this.customerID = customerID;
        this.businessID = booking.businessID;
        this.date = booking.date;
        this.serviceID = booking.serviceID;
        this.serviceDuration = booking.duration;
        this.employee = new EmployeeRecord(0, "Anyone", "", booking.businessID);
        this.time = new TimeOfDay(booking.time);
    }

    public void setBookingDate(LocalDate date) {
        this.date = date;
        log();
    }

    public LocalDate date() {
        return date;
    }

    public int fullDate() {
        return date.getYear() * 10000 + date.getMonth().getValue() *
          100 + date.getDayOfMonth();
    }

    public void setBookingService(int id, int duration) {
        this.serviceID = id;
        this.serviceDuration = duration;
        log();
    }

    public int serviceID() {
        return serviceID;
    }

    public int serviceDuration() {
        return serviceDuration;
    }

    public void setEmployee(EmployeeRecord employee) {
        this.employee = employee;
        log();
    }

    public EmployeeRecord employee() {
        return employee;
    }

    public void setTime(TimeOfDay time) {
        this.time = time;
        log();
    }

    public TimeOfDay time() {
        return time;
    }

    public void clear() {
        date = null;
        serviceID = 0;
        serviceDuration = 0;
        employee = null;
        time = null;
    }

    private void log() {
        if(date != null)
            logger.debug("Date: "+date.toString());
        if(serviceID > 0)
            logger.debug("Service: "+serviceID);
        if(serviceDuration > 0)
            logger.debug("Service Duration: "+serviceDuration);
        if(employee != null)
            logger.debug("EmployeeID: "+employee.id);
        if(time != null)
            logger.debug("Time: "+time.asInt());
    }

}
