package Client.Controller;

import Client.ControllerManager;
import Client.Model.Buses;
import Client.Model.ChildInTrip;
import Client.Model.ChildrenInVehicle;
import Client.Model.Trip;
import Client.Remote.RemoteManager;
import Shared.TernaryRelationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ChildrenInVehicleController extends AbstractTableController {
    @FXML
    private Text tripName;
    @FXML
    private Text childName;
    @FXML
    private TextField codeTextField;
    @FXML
    private TextField companyNameTextField;

    private Trip trip;

    private ChildInTrip childInTrip;

    public ChildrenInVehicleController() throws Exception {
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

    public ChildInTrip getChildInTrip() {
        return childInTrip;
    }

    public void setChildInTrip(ChildInTrip childInTrip) {
        this.childInTrip = childInTrip;
        childName.setText(childInTrip.getStringName() + " " + childInTrip.getStringSurname()+ " " + childInTrip.getStringFiscalCode());
    }

    @Override
    protected ArrayList search(JSONObject filters) throws Exception {
        TernaryRelationService service = RemoteManager.getInstance().getRemoteServicesManager().getChildInVehicleService();
        JSONObject response = service.leftRead(trip.getId(), childInTrip.getId());

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

    @FXML
    public void saveAll() {
        try {
            ArrayList<Integer> vehicleIds = new ArrayList<>();
            for (int i = 0; i < tableView.getItems().size(); i++) {
                ChildrenInVehicle item = (ChildrenInVehicle) tableView.getItems().get(i);
                if( item.getSelect().isSelected()) {
                    vehicleIds.add(item.getId());
                }
            }
            TernaryRelationService service = RemoteManager.getInstance().getRemoteServicesManager().getChildInVehicleService();
            notifyResult(service.assign(trip.getId(), childInTrip.getId(), vehicleIds));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected JSONObject takeFilters() {
        return new JSONObject();
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<ChildrenInVehicle> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject bus = (JSONObject) data.get(i);
            ChildrenInVehicle item = new ChildrenInVehicle(this, (JSONObject) bus.get(0));
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
