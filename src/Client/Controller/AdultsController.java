package Client.Controller;

import Client.ControllerManager;
import Client.Model.Adults;
import Client.Model.Children;
import Client.Remote.RemoteManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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
    @FXML private Parent parent;

    @FXML private TableView<Adults> adultTableView;

    private Children child;

    public AdultsController() throws Exception {
        super( RemoteManager.getInstance().getRemoteServicesManager().getAdultService() );
    }

    public void setChild(Children child) {
        this.child = child;
        filter();
    }

    @FXML
    public void initialize() { }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<Adults> list = search();
            ArrayList<Adults> listTwo;

            //TODO VERY VERY VERY VERY VERY VERY VERY VERY VERY VERY UGLY SERVICE DECLARATION --> FIX IT
            JSONObject response = RemoteManager.getInstance().getRemoteServicesManager().getAdultService().readParentsByChild(child.getStringFiscalCode());
            if((boolean) response.get("success")) {

                JSONObject data = (JSONObject) response.get("data");
                listTwo = parseIntoRows(data);

            } else throw new Exception("Read from server error");

            for (Adults a:list) {
                for (Adults p:listTwo) {
                    if(a.equals(p)) {
                        a.getSelect().setSelected(true);
                    }
                }
            }

            ObservableList<Adults> items = FXCollections.observableArrayList(list);
            adultTableView.setItems(items);

        } catch (Exception e ) {
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

            list.add(new Adults(this, id, name, surname, fiscalCode, new Date(), telephone, new CheckBox()));
        }
        return list;
    }

    public void remove() {
        ControllerManager.getInstance().removePopup();
    }
}
