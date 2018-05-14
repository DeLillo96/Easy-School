package Client.Controller;

import Client.ControllerManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Error extends AbstractNotifyController {
    @FXML
    private Button errorButtonClose;
    @FXML
    private TextArea errorTextArea;

    @FXML
    private void remove() {
        ControllerManager.getInstance().removeNotify();
    }

    @Override
    public void setMessage(String errorMessage) {
        errorTextArea.setText(errorMessage);
    }
}
