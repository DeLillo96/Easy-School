package Client.Controller;

import Client.Model.Children;
import Client.Remote.RemoteManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Date;

public class ChildrenController extends AbstractTableController {
    /* FILTERS */
    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private DatePicker birthDatePicker;
    @FXML private TextField fiscalCodeTextField;
    @FXML private Button searchButton;
    @FXML private Button addButton;

    /* TABLE */
    @FXML private TableView<Children> childTableView;
    @FXML private TableColumn buttonsColumn;
    @FXML private TableColumn nameColumn;
    @FXML private TableColumn surnameColumn;
    @FXML private TableColumn dateColumn;
    @FXML private TableColumn fiscalCodeColumn;

    public ChildrenController() throws Exception {
        super( RemoteManager.getInstance().getRemoteServicesManager().getChildrenService() );
    }

    @FXML
    public void initialize() {
        filter();
    }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<Children> list = search();
            ObservableList<Children> items = FXCollections.observableArrayList(list);
            childTableView.setItems(items);
        } catch (Exception e ) {
            //todo render error
        }
    }

    @FXML
    public void add() throws Exception {
        childTableView.getItems().add(new Children(this));
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("name", nameTextField.getText());
        filters.put("surname", surnameTextField.getText());
        //todo parse form datePicker
        filters.put("fiscalCode", fiscalCodeTextField.getText());

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Children> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject child = (JSONObject) data.get(i);

            Integer id = Integer.parseInt((String) child.get("id"));
            String name = (String) child.get("name");
            String surname = (String) child.get("surname");
            String fiscalCode = (String) child.get("fiscalCode");

            list.add(new Children(this, id, name, surname, fiscalCode, new Date()));
        }

        return list;
    }
}
