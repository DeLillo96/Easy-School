package Client.Controller;

import Client.Model.Children;
import Client.Remote.RemoteManager;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChildrenController extends AbstractTableController {
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

    public ChildrenController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getChildrenService());
    }

    @FXML
    public void initialize() {
        filter();
    }

    @FXML
    public void add() throws Exception {
        addIntoTable(new Children(this));
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("name", nameTextField.getText());
        filters.put("surname", surnameTextField.getText());
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
        } catch (ParseException ignored) {
        }
        filters.put("fiscalCode", fiscalCodeTextField.getText());

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Children> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject child = (JSONObject) data.get(i);

            list.add(new Children(this, child));
        }

        return list;
    }

}
