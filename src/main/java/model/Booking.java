package model;

import java.time.LocalDate;

public class Booking {
    public final int id;
    public final int customerID;
    public final int employeeID;
    public final int serviceID;
    public final int duration;
    public final int time;
    public final LocalDate date;
    public final int businessID;

    public Booking(int id, int customerID, int employeeID, int serviceID, int duration, int date, int time, int businessID){
        this.id = id;
        this.customerID = customerID;
        this.employeeID = employeeID;
        this.serviceID = serviceID;
        this.duration = duration;
        this.date = LocalDate.of(date/10000, (date/100)%100, date%100);
        this.time = time;
        this.businessID = businessID;
    }
}
