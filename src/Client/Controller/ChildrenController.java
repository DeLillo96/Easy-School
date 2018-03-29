package Client.Controller;

import Client.Model.Children;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.ArrayList;

public class ChildrenController {
    @FXML private TableView<Children> childTableView;
    @FXML private TableColumn nameColumn;
    @FXML private TableColumn surnameColumn;
    @FXML private TableColumn dateColumn;
    @FXML private TableColumn fiscalCodeColumn;

    @FXML
    public void initialize() throws Exception {
        Client.Model.Children childrenModel = new Children();
        ArrayList<Children> data = childrenModel.read();

        ObservableList<Children> items = FXCollections.observableArrayList(data);

        childTableView.setItems(items);
    }
}
