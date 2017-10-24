package model;

public class BookingSummary {

    public final int id;
    public final String customerName;
    public final String date;
    public final String employeeName;
    public final String startTime;
    public final String service;

    public BookingSummary(int id, String customerName, String bookingDate,
      String employeeName, int startTime, String service) {
        this.id = id;
        this.customerName = customerName;
        this.date = bookingDate;
        this.employeeName = employeeName;
        this.service = service;

        TimeOfDay start = new TimeOfDay(startTime);
        this.startTime = String.format("%d:%02d", start.getHours(), start
          .getMinutes());
    }
}
