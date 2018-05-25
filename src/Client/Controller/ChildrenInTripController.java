package Client.Controller;

import Client.ControllerManager;
import Client.Model.ChildInTrip;
import Client.Remote.RemoteManager;
import Shared.RelationService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChildrenInTripController extends AbstractTableController {
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

    private Integer tripId;

    public ChildrenInTripController() throws Exception {
        super(null);
    }

    @FXML
    public void saveAll() {
        try {
            ArrayList<Integer> childIds = new ArrayList<>();
            tableView.getItems().forEach(item -> childIds.add(Integer.parseInt((String) item.getData().get("id"))));

            RelationService service = RemoteManager.getInstance().getRemoteServicesManager().getChildInTripService();
            notifyResult(service.assign(childIds, getDailyTripId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ArrayList search(JSONObject filters) throws Exception {
        RelationService service = RemoteManager.getInstance().getRemoteServicesManager().getChildInTripService();
        JSONObject response = service.rightRead(tripId);

        if ((boolean) response.get("success")) {

            JSONObject data = (JSONObject) response.get("data");
            return parseIntoRows(data);

        } else throw new Exception("Read from server error");
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
        ArrayList<ChildInTrip> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject child = (JSONObject) data.get(i);

            ChildInTrip item = new ChildInTrip(this, (JSONObject) child.get(0));
            item.getSelect().setSelected(Boolean.valueOf((String) child.get(1)));
            list.add(item);
        }

        return list;
    }

    @FXML
    public void remove() {
        ControllerManager.getInstance().removePopup();
    }

    public Integer getDailyTripId() {
        return tripId;
    }

    public void setDailyTripId(Integer dailyTripId) {
        this.tripId = dailyTripId;
    }
}
