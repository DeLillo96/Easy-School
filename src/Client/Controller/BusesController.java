package Client.Controller;

import Client.ControllerManager;
import Client.Model.Buses;
import Client.Model.Trip;
import Client.Remote.RemoteManager;
import Shared.RelationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class BusesController extends AbstractTableController {
    /* FILTERS */
    @FXML
    private Text tripName;
    @FXML
    private TextField codeTextField;
    @FXML
    private TextField companyNameTextField;

    private Trip trip;

    public BusesController() throws Exception {
        super(null);
    }

    /**
     * Set a Trip element to the controller, used for assignations
     * @param trip
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
        tripName.setText(trip.getCode().getText());
    }

    @Override
    protected ArrayList search(JSONObject filters) throws Exception {
        RelationService service = RemoteManager.getInstance().getRemoteServicesManager().getBusTripService();
        JSONObject response = service.leftRead(trip.getId());

        if ((boolean) response.get("success")) {

            JSONObject data = (JSONObject) response.get("data");
            return parseIntoRows(data);

        } else throw new Exception("Read from server error");
    }

    /**
     * Generates a new Buses model in order to add it to the tableView as a new row
     * @throws Exception
     */
    @FXML
    public void add() throws Exception {
        Buses bus = new Buses(this);
        bus.setTrip(trip);
        addIntoTable(bus);
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("code", codeTextField.getText());
        filters.put("companyName", companyNameTextField.getText());

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Buses> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject bus = (JSONObject) data.get(i);
            Buses item = new Buses(this, (JSONObject) bus.get(0));
            item.setTrip(trip);
            item.getSelect().setSelected(Boolean.valueOf((String) bus.get(1)));
            list.add(item);
        }

        return list;
    }

    /**
     * Removes the current popup
     */
    @FXML
    public void remove() {
        trip.refreshModel();
        ControllerManager.getInstance().removePopup();
    }

}
