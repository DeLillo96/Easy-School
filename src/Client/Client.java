package Client;

import Client.Controller.Error;
import Client.Controller.Home;
import Client.Controller.Login;
import Client.Remote.RemoteManager;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {
    private Stage stage;
    private Parent notify;
    private static Client instance;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        getInstance().setStage(primaryStage);

        renderLogin();

        primaryStage.setTitle("Easy School");
        primaryStage.setOnCloseRequest(event -> {
            try {
                RemoteManager.getInstance().closeServices();
            } catch (Exception ignored) {}
        });
        primaryStage.show();
    }

    public static Client getInstance() {
        if (instance == null) instance = new Client();
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return stage.getScene();
    }

    public void renderLogin() throws IOException {
        Login login = new Login();
        login.render(instance.getStage());
    }

    public void notifyError(String errorMessage) {
        Error error = new Error();
        notify = error.render(errorMessage, getScene());
    }

    public void removeNotify() {
        Pane mainRoot = (Pane) getScene().getRoot();
        mainRoot.getChildren().remove(notify);
        notify = null;
    }

    public void renderHome() throws IOException {
        Home home = new Home();
        home.render(stage);
    }
}
