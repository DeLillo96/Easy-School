package Client.Controller;

import Client.ControllerManager;
import Client.Model.Menu;
import Client.Remote.RemoteManager;
import Shared.BaseService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Controller which generates four different tableViews for dish creation and assignation
 */
public class DishesController {
    @FXML
    private HBox container;


    /**
     * Renders four differents dish tables, one for each ish type
     * @param menu (Menu used for assignation)
     */
    public void render(Menu menu) {
        try {
            container.getChildren().addAll(
                    renderDishTable(menu, RemoteManager.getInstance().getRemoteServicesManager().getFirstDishService()),
                    renderDishTable(menu, RemoteManager.getInstance().getRemoteServicesManager().getSecondDishService()),
                    renderDishTable(menu, RemoteManager.getInstance().getRemoteServicesManager().getSideDishService()),
                    renderDishTable(menu, RemoteManager.getInstance().getRemoteServicesManager().getSweetDishService())
            );
        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError("Communication error (Can't call dish services)");
        }
    }

    /**
     * Render a single dish table
     * @param menu (Menu used for assignation)
     * @param service (Services called for dish creation and assignation)
     * @return
     * @throws IOException
     */
    private AnchorPane renderDishTable(Menu menu, BaseService service) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/dish.fxml"));
        AnchorPane table = loader.load();

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
