package Client;

import Client.Controller.AddAdultsController;
import Client.Controller.AdultsController;
import Client.Model.Children;
import Client.Controller.AbstractNotifyController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class ControllerManager {
    private static ControllerManager instance;
    private static Stage stage;
    private static Parent notify;
    private static Stack<Parent> popup = new Stack<>();

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

            addNotify(loader, errorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifySuccess(String successMessage) {
        if(notify != null) removeNotify();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/success.fxml"));
            notify = loader.load();

            addNotify(loader, successMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void addNotify(FXMLLoader loader, String message) {
        AbstractNotifyController controller = loader.getController();
        controller.setMessage(message);

        Pane mainRoot = (Pane) getScene().getRoot();
        mainRoot.getChildren().add(notify);
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

    public void renderAddAdults(Children child) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/adults.fxml"));
            Parent parent = loader.load();

            AdultsController adultsController = loader.getController();
            adultsController.setChild(child);

            VBox vBox = new VBox();
            Pane mainRoot = (Pane) getScene().getRoot();
            //vBox.getStyleClass().add("row-HBox");
            vBox.setAlignment(Pos.CENTER);
            AnchorPane.setTopAnchor(vBox, 0d);
            AnchorPane.setBottomAnchor(vBox, 0d);
            AnchorPane.setLeftAnchor(vBox, 0d);
            AnchorPane.setRightAnchor(vBox, 0d);
            vBox.getChildren().add(parent);
            mainRoot.getChildren().add(vBox);
            popup.push(vBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removePopup() {
        Pane mainRoot = (Pane) getScene().getRoot();
        mainRoot.getChildren().remove(popup.pop());
    }

    private void renderFXML(String location) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(location));
        getStage().setScene(new Scene(root));
    }
}
