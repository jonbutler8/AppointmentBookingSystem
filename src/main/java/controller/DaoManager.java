package controller;

import controller.protocols.*;

public class DaoManager {
    public final BookingDao bookingDao;
    public final EmployeeDao employeeDao;
    public final PermissionDao permissionDao;
    public final ServiceDao serviceDao;
    public final UserDao userDao;
    public final WorkingTimesDao workingTimesDao;
    public final CustomerDao customerDao;
    public final BusinessDao businessDao;

    public DaoManager(DatabaseController databaseController) {
        this.businessDao = new SQLiteBusinessDao(databaseController);
        this.bookingDao = new SQLiteBookingDao(databaseController);
        this.employeeDao = new SQLiteEmployeeDao(databaseController);
        this.permissionDao = new SQLitePermissionDao(databaseController);
        this.serviceDao = new SQLiteServiceDao(databaseController);
        this.customerDao = new SQLiteCustomerDao(databaseController);
        this.userDao = new SQLiteUserDao(databaseController, customerDao,
          businessDao);
        this.workingTimesDao = new SQLiteWorkingTimesDao(databaseController);
    }
}
