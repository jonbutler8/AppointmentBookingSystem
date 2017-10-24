package view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.protocols.ModifyBookingView;

public class FXModifyBookingView extends FXNewBookingTimeView implements ModifyBookingView {
    private static final Logger logger =
            LogManager.getLogger(FXModifyBookingView.class);
}
