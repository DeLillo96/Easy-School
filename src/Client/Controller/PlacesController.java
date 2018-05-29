package Client.Controller;

import Client.ControllerManager;
import Client.Model.Places;
import Client.Model.Trip;
import Client.Remote.RemoteManager;
import Shared.RelationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class PlacesController extends AbstractTableController {
    /* FILTERS */
    @FXML
    private Text tripName;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField maxCostTextField;

    private Trip trip;

    public PlacesController() throws Exception {
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
        RelationService service = RemoteManager.getInstance().getRemoteServicesManager().getTripPlaceService();
        JSONObject response = service.leftRead(trip.getId());

        if ((boolean) response.get("success")) {

            JSONObject data = (JSONObject) response.get("data");
            return parseIntoRows(data);

        } else throw new Exception("Read from server error");
    }

    /**
     * Generates a new Place model in order to add it to the tableView as a new row
     * @throws Exception
     */
    @FXML
    public void add() throws Exception {
        Places place = new Places(this);
        place.setTrip(trip);
        addIntoTable(place);
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("name", nameTextField.getText());
        filters.put("address", addressTextField.getText());
        try {
            if (!maxCostTextField.getText().equals(""))
                filters.put("cost", Integer.parseInt(maxCostTextField.getText()));
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError("Insert a number in 'cost' text field");
        }

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Places> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject place = (JSONObject) data.get(i);

            Places item = new Places(this, (JSONObject) place.get(0));
            item.getSelect().setSelected(Boolean.valueOf((String) place.get(1)));
            item.setTrip(trip);
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
