package Client.Controller;

import Client.ControllerManager;
import Client.Remote.RMIServicesManager;
import Client.Remote.RemoteManager;
import Client.Remote.RemoteServicesManager;
import Client.Remote.SocketServicesManager;
import Shared.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

public class Login {
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox remoteChoiceBox;

    @FXML
    public void loginButtonAction() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            setRemoteService();
            RemoteServicesManager remoteServicesManager = RemoteManager.getInstance().getRemoteServicesManager();
            UserService userService = remoteServicesManager.getUserService();
            JSONObject response = userService.login(username, password);

            if ((boolean) response.get("success")) {
                ControllerManager.getInstance().renderHome();
            } else throw new Exception(response.get("messages").toString());
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    private void setRemoteService() throws Exception {
        Object value = remoteChoiceBox.getValue();
        if (value.equals("RMI")) {
            RemoteManager.getInstance().setService(new RMIServicesManager());
        } else {
            RemoteManager.getInstance().setService(new SocketServicesManager());
        }
    }
}
