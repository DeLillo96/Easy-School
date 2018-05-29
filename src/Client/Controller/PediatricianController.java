package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Client.Model.Pediatrician;
import Client.Model.Children;
import Client.Remote.RemoteManager;
import Shared.RelationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PediatricianController extends AbstractTableController {
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
    private DatePicker birthDatePickerFrom;
    @FXML
    private DatePicker birthDatePickerTo;
    @FXML
    private TextField fiscalCodeTextField;

    private Children child;

    public PediatricianController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getPediatricianService());
    }

    /**
     * Set a Child element to the controller, used for assignations
     * @param child
     */
    public void setChild(Children child) {
        this.child = child;
        childName.setText(child.getStringName());
        childSurname.setText(child.getStringSurname());
        childFiscalCode.setText(child.getStringFiscalCode());
    }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<Pediatrician> list = search(takeFilters());

            RelationService followService = RemoteManager.getInstance().getRemoteServicesManager().getChildPediatricianService();
            JSONObject result = followService.rightRead(child.getId());

            ArrayList<Pediatrician> follow = parseIntoRows((JSONObject) result.get("data"));
            for (Pediatrician pediatrician : list) {
                follow.forEach(o -> {
                    if (o.getId().equals(pediatrician.getId())) {
                        pediatrician.getSelect().setSelected(true);
                    }
                });
            }

            ObservableList<AbstractRowModel> items = FXCollections.observableArrayList(list);
            tableView.setItems(items);
            newRowFlag = true;
        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    /**
     * Generates a new Pediatrician model in order to add it to the tableView as a new row
     * @throws Exception
     */
    @FXML
    public void add() throws Exception {
        Pediatrician pediatrician = new Pediatrician(this);
        pediatrician.setChild(child);
        addIntoTable(pediatrician);
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("name", nameTextField.getText());
        filters.put("surname", surnameTextField.getText());
        filters.put("fiscalCode", fiscalCodeTextField.getText());
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
        ArrayList<Pediatrician> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject adult = (JSONObject) data.get(i);

            Pediatrician item = new Pediatrician(this, adult);
            item.setChild(child);
            list.add(item);
        }
        return list;
    }

    /**
     * Removes the current popup
     */
    public void remove() {
        ControllerManager.getInstance().removePopup();
    }
}
