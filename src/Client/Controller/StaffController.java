package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Client.Model.Staff;
import Client.Remote.RemoteManager;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StaffController extends AbstractTableController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private DatePicker birthDatePickerFrom;
    @FXML
    private DatePicker birthDatePickerTo;
    @FXML
    private TextField fiscalCodeTextField;
    @FXML
    private TextField mansionTextField;

    public StaffController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getStaffService());
    }

    @FXML
    public void initialize() {
        filter();
    }

    @FXML
    public void add() throws Exception {
        addIntoTable(new Staff(this));
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("name", nameTextField.getText());
        filters.put("surname", surnameTextField.getText());
        filters.put("fiscalCode", fiscalCodeTextField.getText());
        filters.put("mansion", mansionTextField.getText());
        if ((birthDatePickerFrom.getValue() != null) || (birthDatePickerTo.getValue() != null)) {
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (birthDatePickerFrom.getValue() != null) {
                    Date fromDate = dateFormat.parse(birthDatePickerFrom.getValue().toString());
                    filters.put("birthDateFrom", fromDate);
                }
                if (birthDatePickerTo.getValue() != null) {
                    Date toDate = dateFormat.parse(birthDatePickerTo.getValue().toString());
                    filters.put("birthDateTo", toDate);
                }
            } catch (ParseException e) {
                ControllerManager.getInstance().notifyError("Invalid dates (Required format: yyyy-MM-dd");
            }
        }

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

}
