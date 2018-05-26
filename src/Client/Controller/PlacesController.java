package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Client.Model.DayTrips;
import Client.Model.Places;
import Client.Remote.RemoteManager;
import Shared.RelationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.util.ArrayList;

//import com.sun.javafx.scene.control.DoubleField;

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

    private DayTrips trip;

    public PlacesController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getPlaceService());
    }

    /**
     * Set a Trip element to the controller, used for assignations
     * @param trip
     */
    public void setTrip(DayTrips trip) {
        this.trip = trip;
        tripName.setText(trip.getStringName());
        filter();
    }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<Places> list = search(takeFilters());

            RelationService parentService = RemoteManager.getInstance().getRemoteServicesManager().getTripPlaceService();
            JSONObject result = parentService.rightRead(trip.getId());

            ArrayList<Places> visitedPlaces = parseIntoRows((JSONObject) result.get("data"));
            for (Places visitedPlace : list) {
                visitedPlaces.forEach(o -> {
                    if (o.getId().equals(visitedPlace.getId())) {
                        visitedPlace.getSelect().setSelected(true);
                    }
                });
            }

            ObservableList<AbstractRowModel> items = FXCollections.observableArrayList(list);
            tableView.setItems(items);

        } catch (Exception e) {
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
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
            JSONObject places = (JSONObject) data.get(i);

            Places item = new Places(this, places);
            item.setTrip(trip);
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
