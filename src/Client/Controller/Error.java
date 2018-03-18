package Client.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Error {

    public void render(String message, Scene mainScene) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../Views/error.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextArea errorTextArea = (TextArea) root.lookup("#errorTextArea");
        errorTextArea.setText(message);
        errorTextArea.setWrapText(true);

        root.setOpacity(0.85);

        Pane mainRoot = (Pane) mainScene.getRoot();
        mainRoot.getChildren().add(root);
    }
}
