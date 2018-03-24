package Client.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Home implements EventHandler<ActionEvent> {
    private Stage stage;

    public void render(Stage mainStage) throws IOException {
        stage = mainStage;
        TabPane root = FXMLLoader.load(getClass().getResource("../Views/home.fxml"));

        mainStage.setScene(new Scene(root));
    }

    @Override
    public void handle(ActionEvent event) {

    }
}
