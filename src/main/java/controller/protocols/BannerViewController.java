package controller.protocols;

import javafx.scene.image.Image;
import view.protocols.BannerView;

import java.io.File;

public abstract class BannerViewController {

    private BannerView view;
    private int businessID;

    public BannerViewController(BannerView view, int businessID) {
        this.view = view;
        this.businessID = businessID;
    }

    public void populateView() {
        String path = "data/"+businessID+"-banner.png";
        File file = new File(path);
        if(file.exists()) {
            view.setBannerImage(new Image(file.toURI().toString()));
        }
    }
}
