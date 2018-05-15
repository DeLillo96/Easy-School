package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Client.Model.Adults;
import Client.Model.Children;
import Client.Model.Staff;
import Client.Remote.RemoteManager;
import Shared.AssignService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class StaffController extends AbstractTableController {
    /* FILTERS */
    @FXML
    private Text childName;
    @FXML
    private Text childSurname;
    @FXML
    private Text childFiscalCode;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TextField fiscalCodeTextField;
    @FXML
    private TextField telephoneTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Button addButton;
    @FXML
    private Parent parent;

    @FXML
    private TableView<Staff> staffTableView;

    public StaffController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getAdultService());
    }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<Staff> list = search();

            ObservableList<Staff> items = FXCollections.observableArrayList(list);
            staffTableView.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    @Override
    public void delete(AbstractRowModel abstractRowModel) {
        staffTableView.getItems().remove(abstractRowModel);
    }

    @FXML
    public void add() throws Exception {
        Staff staff = new Staff(this);
        staffTableView.getItems().add(staff);
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
        ArrayList<Staff> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject staff = (JSONObject) data.get(i);

            Staff item = new Staff(this, staff);
            list.add(item);
        }
        return list;
    }

    public void remove() {
        ControllerManager.getInstance().removePopup();
    }
}
