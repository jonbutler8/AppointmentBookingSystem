package view.protocols;

import model.EmployeeRecord;
import model.TimeOfDay;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface NewBookingTimeView extends BannerView {
    void setMessage(String message);
    void setDate(LocalDate date);
    void setService(String service);
    void setEmployee(String employee);
    void setEmployee(List<EmployeeRecord> employees);
    void setPreviousDayOptionVisibility(boolean visible);
    void setNextDayOptionVisibility(boolean visible);
    void setNextDayOptionDisable(boolean disable);
    void setSubmitDisable(boolean disable);
    void setDayOfMonth(String dayOfMonth);
    void displayDay(LocalDate currentDate, TimeOfDay startTime,
      TimeOfDay endTime, ArrayList<TimeOfDay> availableTimes,
      TimeOfDay selectedTime);
    void goBack();
    void submit();
}
