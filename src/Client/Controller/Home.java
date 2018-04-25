package Client.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Home {
    private Stage stage;
    @FXML private TabPane mainTabPane;
    @FXML private Tab childrenTab;
    @FXML private Tab canteenTab;
    @FXML private Tab adultsTab;

    @FXML
    public void initialize() throws IOException {
        AnchorPane childrenPane = FXMLLoader.load(getClass().getResource("../Views/children.fxml"));
        childrenTab.setContent(childrenPane);

        AnchorPane canteenPane = FXMLLoader.load(getClass().getResource("../Views/menu.fxml"));
        canteenTab.setContent(canteenPane);

        AnchorPane adultsPane = FXMLLoader.load(getClass().getResource("../Views/adults.fxml"));
        adultsTab.setContent(adultsPane);
    }
}
