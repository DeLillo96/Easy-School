package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Client.Model.Adults;
import Client.Model.Children;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdultsController extends AbstractTableController {
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
    @FXML
    private TextField telephoneTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Button addButton;
    @FXML
    private Parent parent;

    @FXML
    private TableView<Adults> adultTableView;

    private Children child;

    public AdultsController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getAdultService());
    }

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
            ArrayList<Adults> list = search(takeFilters());

            AssignService parentService = RemoteManager.getInstance().getRemoteServicesManager().getParentService();
            JSONObject result = parentService.rightRead(child.getId());

            ArrayList<Adults> parent = parseIntoRows((JSONObject) result.get("data"));
            for (Adults adult : list) {
                parent.forEach(o -> {
                    if (o.getId().equals(adult.getId())) {
                        adult.getSelect().setSelected(true);
                    }
                });
            }

            ObservableList<Adults> items = FXCollections.observableArrayList(list);
            adultTableView.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    @Override
    public void delete(AbstractRowModel abstractRowModel) {
        adultTableView.getItems().remove(abstractRowModel);
    }

    @FXML
    public void add() throws Exception {
        Adults adult = new Adults(this);
        adult.setChild(child);
        adultTableView.getItems().add(adult);
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("name", nameTextField.getText());
        filters.put("surname", surnameTextField.getText());
        filters.put("fiscalCode", fiscalCodeTextField.getText());
        filters.put("telephone", telephoneTextField.getText());
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
                e.printStackTrace();
            }
        }

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Adults> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject adult = (JSONObject) data.get(i);

            Adults item = new Adults(this, adult);
            item.setChild(child);
            list.add(item);
        }
        return list;
    }

    public void remove() {
        ControllerManager.getInstance().removePopup();
    }
}
