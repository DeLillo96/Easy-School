package Client.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class Error implements EventHandler<ActionEvent> {
    private Button errorButtonClose;
    private Parent root;
    private Scene mainScene;

    public void render(String message, Scene mainScene) {
        try {
            root = FXMLLoader.load(getClass().getResource("../Views/error.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextArea errorTextArea = (TextArea) root.lookup("#errorTextArea");
        errorTextArea.setText(message);

        errorButtonClose = (Button) root.lookup("#errorButtonClose");
        errorButtonClose.setOnAction(this);

        this.mainScene = mainScene;
        Pane mainRoot = (Pane) mainScene.getRoot();
        mainRoot.getChildren().add(root);
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == errorButtonClose) {
            removeErrorsPane();
        }
    }

    private void removeErrorsPane() {
        Pane mainRoot = (Pane) mainScene.getRoot();
        mainRoot.getChildren().remove(root);
    }

}
