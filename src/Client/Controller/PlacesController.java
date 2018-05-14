package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Client.Model.DayTrips;
import Client.Model.Places;
import Client.Remote.RemoteManager;
import Shared.AssignService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
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
    @FXML
    private Button searchButton;
    @FXML
    private Button addButton;

    @FXML
    private TableView<Places> placesTableView;

    private DayTrips trip;

    public PlacesController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getPlaceService());
    }

    public void setTrip(DayTrips trip) {
        this.trip = trip;
        tripName.setText(trip.getStringName());
        filter();
    }

    @FXML
    public void initialize() {
    }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<Places> list = search();

            AssignService parentService = RemoteManager.getInstance().getRemoteServicesManager().getTripPlaceService();
            JSONObject result = parentService.rightRead(trip.getId());

            ArrayList<Places> visitedPlaces = parseIntoRows((JSONObject) result.get("data"));
            for (Places visitedPlace : list) {
                visitedPlaces.forEach(o -> {
                    if (o.getId().equals(visitedPlace.getId())) {
                        visitedPlace.getSelect().setSelected(true);
                    }
                });
            }

            ObservableList<Places> items = FXCollections.observableArrayList(list);
            placesTableView.setItems(items);

        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    @Override
    public void delete(AbstractRowModel abstractRowModel) {
        placesTableView.getItems().remove(abstractRowModel);
    }

    @FXML
    public void add() throws Exception {
        Places place = new Places(this);
        place.setTrip(trip);
        placesTableView.getItems().add(place);
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

    public void remove() {
        ControllerManager.getInstance().removePopup();
    }
}
