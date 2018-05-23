package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Client.Model.Buses;
import Client.Model.Places;
import Client.Remote.RemoteManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class BusesController extends AbstractTableController {
    /* FILTERS */
    @FXML
    private Text placeName;
    @FXML
    private TextField licensePlateTextField;
    @FXML
    private TextField companyNameTextField;

    private Places place;

    public BusesController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getBusService());
    }

    public void setPlace(Places place) {
        this.place = place;
        placeName.setText(place.getStringName());
    }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<Buses> allBuses = search(takeFilters());
            JSONObject response = RemoteManager.getInstance().getRemoteServicesManager().getBusArrivalPlaceService().leftRead(place.getId());
            ArrayList<Buses> arrivalBuses = parseIntoRows((JSONObject) response.get("data"));

            response = RemoteManager.getInstance().getRemoteServicesManager().getBusStartingPlaceService().leftRead(place.getId());
            ArrayList<Buses> startingBuses = parseIntoRows((JSONObject) response.get("data"));

            for (Buses bus : allBuses) {
                arrivalBuses.forEach(o -> {
                    if (o.getId().equals(bus.getId())) {
                        bus.getArrivalSelect().setSelected(true);
                    }
                });
                startingBuses.forEach(o -> {
                    if (o.getId().equals(bus.getId())) {
                        bus.getStartSelect().setSelected(true);
                    }
                });
            }

            ObservableList<Buses> items = FXCollections.observableArrayList(allBuses);
            tableView.setItems(items);

        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    @FXML
    public void add() throws Exception {
        Buses bus = new Buses(this);
        bus.setPlace(place);
        addIntoTable(bus);
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("licensePlate", licensePlateTextField.getText());
        filters.put("companyName", companyNameTextField.getText());

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Buses> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject bus = (JSONObject) data.get(i);
            Buses item = new Buses(this, bus);
            item.setPlace(place);
            list.add(item);
        }
        return list;
    }

    public void remove() {
        ControllerManager.getInstance().removePopup();
    }

}
