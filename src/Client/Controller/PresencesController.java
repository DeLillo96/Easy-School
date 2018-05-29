package Client.Controller;

import Client.ControllerManager;
import Client.Model.Presence;
import Client.Model.Children;
import Client.Model.Trip;
import Client.Remote.RemoteManager;
import Shared.RelationService;
import Shared.TernaryRelationService;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class PresencesController extends AbstractTableController {
    @FXML
    private Text tripName;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField fiscalCodeTextField;
    @FXML
    private ChoiceBox vehicles;

    private ArrayList<Integer> vehiclesIds = new ArrayList<>();
    private Trip trip;

    public PresencesController() throws Exception {
        super(null);
    }

    public void fillVehicleBox() {
        try {
            TernaryRelationService service = RemoteManager.getInstance().getRemoteServicesManager().getChildInVehicleService();
            JSONObject response = service.leftRead(trip.getId(), 0);
            JSONObject data = (JSONObject) response.get("data");
            vehicles.getItems().clear();
            vehiclesIds.clear();
            for (int i = 0; i < data.size(); i++) {
                JSONObject item = (JSONObject) data.get(i);
                JSONObject vehicle = (JSONObject) item.get(0);

                vehicles.getItems().add(vehicle.get("code") + " - " + vehicle.get("companyName"));
                vehiclesIds.add(Integer.parseInt((String) vehicle.get("id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        RelationService service = RemoteManager.getInstance().getRemoteServicesManager().getChildInTripService();
        JSONObject response = service.rightRead(trip.getId());

        if ((boolean) response.get("success")) {

            JSONObject data = (JSONObject) response.get("data");
            return parseIntoRows(data);

        } else throw new Exception("Read from server error");
    }

    @FXML
    public void checkPresence() {
        try {
            TernaryRelationService service = RemoteManager.getInstance().getRemoteServicesManager().getChildInVehicleService();
            JSONObject response = service.centerRead(trip.getId(), vehiclesIds.get(vehicles.getSelectionModel().getSelectedIndex()));
            JSONObject data = (JSONObject) response.get("data");
            for (int j = 0; j < tableView.getItems().size(); j++) {
                Presence child = (Presence) tableView.getItems().get(j);
                child.removeStatus();
                child.setStatusMessage("");
                for (int i = 0; i < data.size(); i++) {
                    Integer childId = Integer.parseInt((String) (((JSONObject) data.get(i)).get("id")));
                    if (child.getSelect().isSelected()) {
                        if(childId.equals(child.getId())) {
                            child.setStatusGreen();
                            child.setStatusMessage("Correct");
                            break;
                        } else {
                            child.setStatusRed();
                            child.setStatusMessage("This child is in the wrong vehicle");
                        }
                    } else {
                        if(childId.equals(child.getId())) {
                            child.setStatusRed();
                            child.setStatusMessage("Missed child");
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError("error in check appeal from selected vehicle");
        }
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
        filters.put("fiscalCode", fiscalCodeTextField.getText());

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Presence> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject child = (JSONObject) data.get(i);

            if(Boolean.valueOf((String) child.get(1))) {
                list.add(new Presence(this, (JSONObject) child.get(0)));
            }
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
