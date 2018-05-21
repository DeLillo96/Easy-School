package Client.Controller;

import Client.ControllerManager;
import Client.Model.Menu;
import Client.Remote.RemoteManager;
import Shared.BaseService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DishesController {
    @FXML
    private HBox container;

    public void render(Menu menu) {
        try {
            container.getChildren().addAll(
                    renderDishTable(menu, RemoteManager.getInstance().getRemoteServicesManager().getFirstDishService()),
                    renderDishTable(menu, RemoteManager.getInstance().getRemoteServicesManager().getSecondDishService()),
                    renderDishTable(menu, RemoteManager.getInstance().getRemoteServicesManager().getSideDishService()),
                    renderDishTable(menu, RemoteManager.getInstance().getRemoteServicesManager().getSweetDishService())
            );
        } catch (Exception e) {
            //todo render error
            e.printStackTrace();
        }
    }

    private VBox renderDishTable(Menu menu, BaseService service) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/dish.fxml"));
        VBox table = loader.load();

        DishController dishController = loader.getController();
        dishController.setService(service);
        dishController.setMenu(menu);
        dishController.filter();

        return table;
    }

    @FXML
    public void remove() {
        ControllerManager.getInstance().removePopup();
    }

}
