package Client.Controller;

import Client.ControllerManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.Timer;
import java.util.TimerTask;


public class Success extends AbstractNotifyController {
    @FXML
    private TextArea successTextArea;

    @FXML
    public void initialize() {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> ControllerManager.getInstance().removeNotify());
                    }
                },
                4500
        );
    }

    @Override
    public void setMessage(String successMessage) {
        successTextArea.setText(successMessage);
    }
}
