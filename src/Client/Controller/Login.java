package Client.Controller;

import Client.ControllerManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import java.io.IOException;

public class Login implements EventHandler<ActionEvent> {
    private Button buttonLogin;
    private TextField usernameField;
    private PasswordField passwordField;
    private Stage stage;

    public void render(Stage mainStage) throws IOException {
        stage = mainStage;
        Pane root = FXMLLoader.load(getClass().getResource("../Views/login.fxml"));

        buttonLogin = (Button) root.lookup("#loginButton");
        buttonLogin.setOnAction(this);

        usernameField = (TextField) root.lookup("#usernameTextField");
        passwordField = (PasswordField) root.lookup("#passwordTextField");

        mainStage.setScene(new Scene(root));
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == buttonLogin) {
            try {
                String username = usernameField.getText();
                String password = passwordField.getText();
                JSONObject response = ControllerManager.getUserController().login(username, password);


                if((boolean) response.get("success")) {
                    //todo things
                } else throw new Exception(response.get("messages").toString());
            } catch (Exception e) {
                Error error = new Error();
                error.render(e.getMessage(), stage.getScene());
                //e.printStackTrace(); //FOR DEBUG!!!
            }
        }
    }

}
