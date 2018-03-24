package Client;

import Client.Controller.Login;
import Client.Remote.RemoteManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {
    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Easy School");
        Login login = new Login();
        login.render(primaryStage);

        primaryStage.setOnCloseRequest(event -> {
            try {
                RemoteManager.getInstance().closeServices();
            } catch (Exception ignored) {}
        });

        primaryStage.show();
    }
}
