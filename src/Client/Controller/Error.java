package Client.Controller;

import Client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class Error {
    @FXML private Button errorButtonClose;
    @FXML private TextArea errorTextArea;

    public Parent render(String message, Scene mainScene) {
        Parent root = null;
        try {
             root = FXMLLoader.load(getClass().getResource("../Views/error.fxml"));

            TextArea errorTextArea = (TextArea) root.lookup("#errorTextArea");
            errorTextArea.setText(message);

            Pane mainRoot = (Pane) mainScene.getRoot();
            mainRoot.getChildren().add(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    @FXML private void remove() {
        Client.getInstance().removeNotify();
    }

}
