package Client.Controller;

import Client.Remote.RMIServicesManager;
import Client.Remote.RemoteManager;
import Client.Remote.RemoteServicesManager;
import Client.Remote.SocketServicesManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.rmi.RemoteException;

public class Login implements EventHandler<ActionEvent> {
    private Button buttonLogin;
    private TextField usernameField;
    private PasswordField passwordField;
    private ChoiceBox remoteChoiceBox;
    private Stage stage;

    public void render(Stage mainStage) throws IOException {
        stage = mainStage;
        Pane root = FXMLLoader.load(getClass().getResource("../Views/login.fxml"));

        buttonLogin = (Button) root.lookup("#loginButton");
        usernameField = (TextField) root.lookup("#usernameTextField");
        passwordField = (PasswordField) root.lookup("#passwordTextField");
        remoteChoiceBox = (ChoiceBox) root.lookup("#remoteChoiceBox");

        ObservableList<String> collection = FXCollections.observableArrayList("RMI", "Socket");
        remoteChoiceBox.setItems(collection);
        remoteChoiceBox.setValue("RMI");
        buttonLogin.setOnAction(this);

        mainStage.setScene(new Scene(root));
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == buttonLogin) {
            loginEvent();
        }
    }

    private void loginEvent() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            setRemoteService();
            RemoteServicesManager remoteServicesManager = RemoteManager.getInstance().getRemoteServicesManager();
            JSONObject response = remoteServicesManager.getUserService().login(username, password);

            System.out.println(response.toJSONString());
            if((boolean) response.get("success")) {
                //todo things
            } else throw new Exception(response.get("messages").toString());
        } catch (Exception e) {
            Error error = new Error();
            error.render(e.getMessage(), stage.getScene());
            //e.printStackTrace(); //FOR DEBUG!!!
        }
    }

    private void setRemoteService() throws RemoteException {
        if(remoteChoiceBox.getValue() == "RMI") {
            RemoteManager.getInstance().setService(new RMIServicesManager());
        } else {
            RemoteManager.getInstance().setService(new SocketServicesManager());
        }
    }
}
