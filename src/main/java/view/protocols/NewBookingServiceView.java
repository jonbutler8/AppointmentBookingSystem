package view.protocols;

import model.Service;

import java.util.ArrayList;
import java.util.HashMap;

public interface NewBookingServiceView extends BannerView {
    void displayServices(HashMap<Service, ArrayList<Integer>> services);
}
