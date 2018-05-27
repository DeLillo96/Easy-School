package Client.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class used to handle the application's main tabPane
 */
public class Home {
    private Stage stage;
    @FXML
    private TabPane mainTabPane;
    @FXML
    private Tab homeTab;
    @FXML
    private Tab childrenTab;
    @FXML
    private Tab canteenTab;
    @FXML
    private Tab dayTripsTab;
    @FXML
    private Tab staffTab;

    /**
     * For each tab in the tabPane, this methos loads an FXML file resource and set it to a specific tab
     * @throws IOException
     */
    @FXML
    public void initialize() throws IOException {
        AnchorPane homePane = FXMLLoader.load(getClass().getResource("../Views/calendar.fxml"));
        homeTab.setContent(homePane);

        AnchorPane childrenPane = FXMLLoader.load(getClass().getResource("../Views/children.fxml"));
        childrenTab.setContent(childrenPane);

        AnchorPane canteenPane = FXMLLoader.load(getClass().getResource("../Views/menu.fxml"));
        canteenTab.setContent(canteenPane);

        AnchorPane dayTripsPane = FXMLLoader.load(getClass().getResource("../Views/daytrips.fxml"));
        dayTripsTab.setContent(dayTripsPane);

        AnchorPane staffPane = FXMLLoader.load(getClass().getResource("../Views/staff.fxml"));
        staffTab.setContent(staffPane);
    }
}
