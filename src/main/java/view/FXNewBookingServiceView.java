package view;

import controller.NewBookingServiceController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.fxml.ServiceBox;
import view.protocols.BannerView;
import view.protocols.NewBookingServiceView;

import java.net.URL;
import java.util.*;

public class FXNewBookingServiceView implements NewBookingServiceView, BannerView {

    private static final Logger logger =
      LogManager.getLogger(FXNewBookingServiceView.class);

    private NewBookingServiceController controller;

    @FXML
    private ImageView banner;
    @FXML
    private VBox leftColumn;
    @FXML
    private VBox rightColumn;

    public void setController(NewBookingServiceController controller) {
        this.controller = controller;
    }

    @Override
    public void setBannerImage(Image image) {
        banner.setImage(image);
    }

    @Override
    public void displayServices(HashMap<Service, ArrayList<Integer>> services) {
        int i = 0;
        List<Service> serviceList = new ArrayList<>();
        serviceList.addAll(services.keySet());
        Collections.sort(serviceList);
        for(Service service : serviceList) {
            logger.debug("Durations Size: "+ services.get(service).size());
            if(++i % 2 == 1) {
                leftColumn.getChildren().add(new ServiceBox(controller, service,
                  services.get(service)));
            }
            else {
                rightColumn.getChildren().add(new ServiceBox(controller,
                  service, services.get(service)));
            }
        }
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

    }
}
