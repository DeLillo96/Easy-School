package Client;

import Client.Remote.RemoteManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {

    public static void main(String args[]) {
        launch(args);
    }

    /**
     * Method which launches the client
     * @param primaryStage (Main window)
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        ControllerManager main = ControllerManager.getInstance();
        main.setStage(primaryStage);

        main.renderLogin();

        primaryStage.setTitle("Easy School");
        primaryStage.setOnCloseRequest(event -> {
            try {
                RemoteManager.getInstance().closeServices();
            } catch (Exception ignored) {
            }
        });
        primaryStage.show();
    }
}
