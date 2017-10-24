package controller;

import controller.protocols.EmployeeDao;
import controller.protocols.ServiceDao;
import controller.protocols.ViewController;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import model.EmployeeRecord;
import model.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.protocols.BannerView;
import view.protocols.EmployeeServiceToggleView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EmployeeServiceToggleController extends EmployeePickerController {
    private EmployeeServiceToggleView view;
    private BannerView bannerView;
    private HashMap<Service, ArrayList<Integer>> services = null;
    private ServiceDao serviceDao;
    private ViewController viewController;

    private static final Logger logger =
      LogManager.getLogger(EmployeeServiceToggleController.class);

    EmployeeServiceToggleController(ViewController viewController,
      EmployeeServiceToggleView view, EmployeeDao employeeDao,
      ServiceDao serviceDao, PermissionManager permissionManager) {
        super(viewController, view, employeeDao, permissionManager);
        this.viewController = viewController;
        this.view = view;
        this.bannerView = (BannerView) view;
        this.serviceDao = serviceDao;
    }

    public void populateView() {
        services = serviceDao.getAllServices(
          permissionManager.getLoggedInUser().businessID());

        for (Service service : services.keySet()) {
            if(service == null)
                viewController.gotoError("There are no services!");
        }

        String path = "data/"+permissionManager.getLoggedInUser().businessID() +
          "-banner.png";
        File file = new File(path);
        if(file.exists()) {
            bannerView.setBannerImage(new Image(file.toURI().toString()));
        }
    }

    @Override
    protected void applyUpdate(EmployeeRecord newEmployee) {
        if (services == null) {
            services = serviceDao.getAllServices(
              permissionManager.getLoggedInUser().businessID());
        }

        ArrayList<CheckBox> serviceBoxes = new ArrayList<CheckBox>();
        List<Service> serviceList = new ArrayList<>();
        serviceList.addAll(services.keySet());
        Collections.sort(serviceList);
        for (Service thisService : serviceList) {
            CheckBox servCheck = new CheckBox();
            servCheck.setText(thisService.name());

            if(serviceDao.canDoService(newEmployee, thisService.id()))
                servCheck.setSelected(true);

            servCheck.selectedProperty().addListener(event -> {
                toggleService(thisService.id(), servCheck.isSelected());
            });
            serviceBoxes.add(servCheck);
        }
        view.setServices(serviceBoxes);
    }

    private void toggleService(int serviceID, boolean selected) {
        logger.debug("ServiceID + " + selected);
        serviceDao.toggleService(view.getSelectedEmployee(), serviceID);
    }
}
