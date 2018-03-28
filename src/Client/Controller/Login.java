package Client.Controller;

import Client.Client;
import Client.Remote.RMIServicesManager;
import Client.Remote.RemoteManager;
import Client.Remote.RemoteServicesManager;
import Client.Remote.SocketServicesManager;
import Shared.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import java.io.IOException;

public class Login {
    @FXML private Button loginButton;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ChoiceBox remoteChoiceBox;

    public void render(Stage mainStage) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("../Views/login.fxml"));

        ObservableList<String> collection = FXCollections.observableArrayList("RMI", "Socket");

        mainStage.setScene(new Scene(root));
    }

    @FXML public void loginButtonAction() throws InterruptedException {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            setRemoteService();
            RemoteServicesManager remoteServicesManager = RemoteManager.getInstance().getRemoteServicesManager();
            UserService userService = remoteServicesManager.getUserService();
            JSONObject response = userService.login(username, password);

            if((boolean) response.get("success")) {
                Client.getInstance().renderHome();
            } else throw new Exception(response.get("messages").toString());
        } catch (Exception e) {
            Client.getInstance().renderError(e.getMessage());
        }
    }

    private void setRemoteService() throws Exception {
        if(remoteChoiceBox.getValue() == "RMI") {
            RemoteManager.getInstance().setService(new RMIServicesManager());
        } else {
            RemoteManager.getInstance().setService(new SocketServicesManager());
        }
    }
}
