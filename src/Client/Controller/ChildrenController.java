package Client.Controller;

import Client.Model.Children;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class ChildrenController {
    /* FILTERS */
    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private DatePicker birthDatePicker;
    @FXML private TextField fiscalCodeTextField;
    @FXML private Button searchButton;
    @FXML private Button refreshButton;
    @FXML private Button addButton;

    /* TABLE */
    @FXML private TableView<Children> childTableView;
    @FXML private TableColumn buttonsColumn;
    @FXML private TableColumn nameColumn;
    @FXML private TableColumn surnameColumn;
    @FXML private TableColumn dateColumn;
    @FXML private TableColumn fiscalCodeColumn;

    @FXML
    public void initialize() throws Exception {
        refresh();
    }

    @FXML
    public void refresh() throws Exception {
        Client.Model.Children childrenModel = new Children();
        ArrayList<Children> data = childrenModel.read();

        ObservableList<Children> items = FXCollections.observableArrayList(data);

        childTableView.setItems(items);
    }

    @FXML
    public void search() throws Exception {
        Client.Model.Children childrenModel = new Children();
        ArrayList<Children> data = childrenModel.read();

        ObservableList<Children> items = FXCollections.observableArrayList(data);

        childTableView.setItems(items);
    }

    @FXML
    public void add() throws Exception {
        childTableView.getItems().add(new Children());
    }
}
