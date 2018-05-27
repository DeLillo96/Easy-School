package Client.Controller;

import Client.Model.Trip;
import Client.Remote.RemoteManager;
import javafx.fxml.FXML;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class TripController extends AbstractTableController {

    private Integer calendarId;

    public TripController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getDailyTripService());
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();
        filters.put("day", calendarId);
        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Trip> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject trip = (JSONObject) data.get(i);

            Trip item = new Trip(this, trip);
            item.refreshModel();

            list.add(item);
        }
        return list;
    }

    @FXML
    public void add() throws Exception {
        Trip item = new Trip(this);
        item.setCalendarId(calendarId);
        addIntoTable(item);
    }

    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }
}
