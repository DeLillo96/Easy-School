package Client;

import Client.Controller.Error;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class ControllerManager {
    private static ControllerManager instance;
    private static Stage stage;
    private static Parent notify;

    public static ControllerManager getInstance() {
        if (instance == null) instance = new ControllerManager();
        return instance;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        ControllerManager.stage = stage;
    }

    public Scene getScene() {
        return getStage().getScene();
    }

    public void renderLogin() throws IOException {
        renderFXML("Views/login.fxml");
    }

    public void notifyError(String errorMessage) {
        if(notify != null) removeNotify();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/error.fxml"));
            notify = loader.load();

            Error errorController = loader.getController();
            errorController.setErrorMessage(errorMessage);

            Pane mainRoot = (Pane) getScene().getRoot();
            mainRoot.getChildren().add(notify);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeNotify() {
        Pane mainRoot = (Pane) getScene().getRoot();
        mainRoot.getChildren().remove(notify);
        notify = null;
    }

    public void renderHome() throws IOException {
        renderFXML("Views/home.fxml");
    }

    public void renderChildren() throws IOException {
        renderFXML("Views/children.fxml");
    }

    private void renderFXML(String location) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(location));
        getStage().setScene(new Scene(root));
    }

    private void renderFXML(String location, String s) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(location));
        getStage().setScene(new Scene(root));
    }
}
