package controller;

import controller.protocols.BannerViewController;
import controller.protocols.ServiceDao;
import controller.validator.ServiceNameValidator;
import controller.validator.ValidationResult;
import controller.validator.protocols.Validator;
import javafx.scene.control.CheckBox;
import model.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.FXServiceConfigView;
import view.protocols.ServiceConfigView;

import java.util.ArrayList;
import java.util.Collections;

public class ServiceConfigController extends BannerViewController {

    private static final int SERVICE_DURATION_MULTIPLE = 30;
    private static final int NUM_DURATIONS = 3;
    private static final Logger logger = LogManager.getLogger(ServiceConfigController.class);
    public static final String BLANK_ERROR = "This field is required";
    public static final String[] DURATION_STRINGS = {"half hour", "one hour", "90 minute"};
    private ServiceDao serviceDao;
    private PermissionManager permissionManager;
    private FXServiceConfigView view;
    private Service oldService;

    ServiceConfigController(ServiceConfigView view, ServiceDao serviceDao,
      PermissionManager permissionManager) {
        super(view, permissionManager.getLoggedInUser().businessID());
        this.view = (FXServiceConfigView) view;
        this.serviceDao = serviceDao;
        this.permissionManager = permissionManager;
    }

    public void checkForUpdate() {
        checkForUpdate(false);
    }

    private void checkForUpdate(boolean forceUpdate) {
        Service newService = view.getSelectedService();

        logger.debug("Updating modify services form");

        // Can't update if we don't have enough information yet
        if (newService == null) {
            return;
        }

        // Only update if the employee has changed or if force update is enabled
        if (!newService.equals(oldService) || forceUpdate) {
            oldService = newService;
            applyUpdate(newService);
        }
    }

    private void toggleServiceTime(int serviceID, int minuteDuration, boolean selected) {
        logger.debug("ServiceID + " + selected);
        serviceDao.toggleServiceTime(serviceID, minuteDuration);
    }

    private void populateServices() {
        view.disableAllBoxes();
        ArrayList<Service> serviceList = serviceDao.getAllExistingServices(
                permissionManager.getLoggedInUser().businessID());
        Collections.sort(serviceList);
        view.setServiceItems(serviceList);
    }

    protected void applyUpdate(Service newService) {
        view.setDeleteText(newService.name());

        ArrayList<Integer>serviceTimes =
                   serviceDao.getAllServices(
                           permissionManager.getLoggedInUser().businessID())
                   .get(newService);

        ArrayList<CheckBox> durationBoxes = new ArrayList<CheckBox>();
        for (int i = 0; i < NUM_DURATIONS; i ++) {
            int minutes = (i+1) * SERVICE_DURATION_MULTIPLE;

            CheckBox durationBox = new CheckBox();
            durationBox.setText("Has " + DURATION_STRINGS[i] + " sessions");

            if (serviceTimes != null && serviceTimes.contains(minutes)) {
                durationBox.setSelected(true);
            }
            durationBox.selectedProperty().addListener(event -> {
                toggleServiceTime(newService.id(), minutes, durationBox.isSelected());
            });
            durationBoxes.add(durationBox);
        }
        view.setDurations(durationBoxes);
    }

    public boolean validateServiceName(String name) {
        return validateServiceName(name, false);
    }

    private boolean validateServiceName(String name, boolean isFormSubmit) {
        boolean valid = true;
        Validator validator = new ServiceNameValidator();
        ValidationResult validationResult = validator.validate(name);
        if(!validationResult.valid()) {
            view.setFormError(validationResult.message());
            valid = false;
        }
        if (name.equals("") && isFormSubmit) {
            view.setFormError(BLANK_ERROR);
            valid = false;
        }
        return valid;
    }

    public void tryAddService() {
        String serviceName = view.getEnteredServiceName().trim();
        view.setEnteredServiceName(serviceName);

        boolean valid = validateServiceName(serviceName, true) && !checkServiceExists(serviceName);

        if (valid && serviceDao.createService(serviceName,
                permissionManager.getLoggedInUser().businessID())) {
            logger.info("Successfully created service " + serviceName);
            setDefaults();
            view.setFormMessage("Service \"" + serviceName + "\" created");
        }
    }

    boolean checkServiceExists(String service) {
        if (serviceDao.serviceExists(service,
                permissionManager.getLoggedInUser().businessID())) {
            view.setFormError("That service already exists");
            return true;
        }
        return false;
    }

    public void setDefaults() {
        view.setDeleteText("");
        view.setFormError("");
        view.setFormMessage("");
        view.setEnteredServiceName("");
        populateServices();
    }

    public void deleteService() {
        Service toDelete = view.getSelectedService();
        if (serviceDao.deleteService(toDelete.id())) {
            setDefaults();
        }

    }

}
