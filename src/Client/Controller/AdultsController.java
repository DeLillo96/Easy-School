package Client.Controller;

import Client.Model.Adults;
import Client.Remote.RemoteManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Date;

public class AdultsController extends AbstractTableController {
    /* FILTERS */
    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private DatePicker birthDatePicker;
    @FXML private TextField fiscalCodeTextField;
    @FXML private TextField telephoneTextField;
    @FXML private Button searchButton;
    @FXML private Button addButton;

    @FXML private TableView<Adults> adultTableView;

    public AdultsController() throws Exception {
        super( RemoteManager.getInstance().getRemoteServicesManager().getAdultService() );
    }

    @FXML
    public void initialize() {
        filter();
    }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<Adults> list = search();
            ObservableList<Adults> items = FXCollections.observableArrayList(list);
            adultTableView.setItems(items);
        } catch (Exception e ) {
            e.printStackTrace();
            //todo render error
        }
    }

    @FXML
    public void add() throws Exception {
        adultTableView.getItems().add(new Adults(this));
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("name", nameTextField.getText());
        filters.put("surname", surnameTextField.getText());
        filters.put("fiscalCode", fiscalCodeTextField.getText());
        filters.put("telephone", telephoneTextField.getText());

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Adults> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject adult = (JSONObject) data.get(i);

            Integer id = Integer.parseInt((String) adult.get("id"));
            String name = (String) adult.get("name");
            String surname = (String) adult.get("surname");
            String fiscalCode = (String) adult.get("fiscalCode");
            String telephone = (String) adult.get("telephone");

            list.add(new Adults(this, id, name, surname, fiscalCode, new Date(), telephone));
        }
        return list;
    }
}
