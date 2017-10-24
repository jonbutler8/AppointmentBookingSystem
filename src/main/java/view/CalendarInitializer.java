package view;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.FXViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import view.protocols.CalendarOuterView;
import view.protocols.EmployeeTimesView;

/*** Helper class for creating the inner calendar schedule component ***/ 
public class CalendarInitializer {
    
    private static final Logger logger =
            LogManager.getLogger(CalendarInitializer.class);
    private static final String CALENDAR_XML = "FXScheduleCalendar.fxml";

    public CalendarInitializer() {
        
    }
    
    public FXCalendarComponent initialize(CalendarOuterView parentview, AnchorPane calendarOuterBox) {
        FXCalendarComponent innerView = null;
        FXMLLoader loader;
        Node node;
        try {
            loader = new FXMLLoader(getClass().getResource(FXViewController.FXML_RESOURCE_DIR +
                    CALENDAR_XML));
            node = loader.load();

            calendarOuterBox.getChildren().add(node);
            calendarOuterBox.getChildren().setAll(node);
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            
            

            AnchorPane.setLeftAnchor(node, 0.0);
            innerView = loader.getController();
            if(innerView == null)
                throw new NullPointerException();
            innerView.setParentView(parentview);
            return innerView;
         
        }
        catch(IOException | NullPointerException e) {
            logger.fatal(e.getMessage(), e);
            return null;
        }
    }
}
