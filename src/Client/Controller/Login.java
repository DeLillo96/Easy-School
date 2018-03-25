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
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import java.io.IOException;

public class Login implements EventHandler<ActionEvent> {
    private Button loginButton;
    private TextField usernameField;
    private PasswordField passwordField;
    private ChoiceBox remoteChoiceBox;
    private Stage stage;

    public void render(Stage mainStage) throws IOException {
        stage = mainStage;
        Pane root = FXMLLoader.load(getClass().getResource("../Views/login.fxml"));

        loginButton = (Button) root.lookup("#loginButton");
        usernameField = (TextField) root.lookup("#usernameTextField");
        passwordField = (PasswordField) root.lookup("#passwordTextField");
        remoteChoiceBox = (ChoiceBox) root.lookup("#remoteChoiceBox");

        ObservableList<String> collection = FXCollections.observableArrayList("RMI", "Socket");
        remoteChoiceBox.setItems(collection);
        remoteChoiceBox.setValue("RMI");
        loginButton.setOnAction(this);

        mainStage.setScene(new Scene(root));
    }

    @Override
    public void handle(ActionEvent event) {
        stage.getScene().setCursor(Cursor.WAIT);
        if(event.getSource() == loginButton) {
            loginEvent();
        }
        stage.getScene().setCursor(Cursor.DEFAULT);
    }

    private void loginEvent() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            setRemoteService();
            RemoteServicesManager remoteServicesManager = RemoteManager.getInstance().getRemoteServicesManager();
            JSONObject response = remoteServicesManager.getUserService().login(username, password);

            if((boolean) response.get("success")) {
                openHome();
            } else throw new Exception(response.get("messages").toString());
        } catch (Exception e) {
            Error error = new Error();
            error.render(e.getMessage(), stage.getScene());
        }
    }

    private void setRemoteService() throws Exception {
        if(remoteChoiceBox.getValue() == "RMI") {
            RemoteManager.getInstance().setService(new RMIServicesManager());
        } else {
            RemoteManager.getInstance().setService(new SocketServicesManager());
        }
    }

    private void openHome() {
        Home home = new Home();
        try {
            home.render(stage);
        } catch (IOException e) {
            Error error = new Error();
            error.render("Internal Error, could not render home page", stage.getScene());
        }
    }
}
