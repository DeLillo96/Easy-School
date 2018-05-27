package Client.Controller;

import Client.ControllerManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * Class used to render error notifications with related error message
 */
public class Error extends AbstractNotifyController {
    @FXML
    private Button errorButtonClose;
    @FXML
    private TextArea errorTextArea;

    /**
     * Removes the current notify popup
     */
    @FXML
    private void remove() {
        ControllerManager.getInstance().removeNotify();
    }

    @Override
    public void setMessage(String errorMessage) {
        errorTextArea.setText(errorMessage);
    }
}
