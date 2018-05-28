package Client.Controller;

import Client.Model.CalendarDay;
import Client.Model.Dish;
import Client.Remote.RemoteManager;
import javafx.fxml.FXML;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class DishController extends AbstractTableController {
    public DishController() throws Exception {
        super(null);
    }

    @FXML
    public void initialize() {
        filter();
    }

    @Override
    protected JSONObject takeFilters() {
        return new JSONObject();
    }

    @Override
    protected ArrayList search(JSONObject filters) throws Exception {
        ArrayList result = new ArrayList();
        JSONObject response = RemoteManager.getInstance().getRemoteServicesManager().getFirstDishService().read(filters);
        if ((boolean) response.get("success")) {
            JSONObject data = (JSONObject) response.get("data");
            result.addAll(parseIntoRows(data, "first"));
        }

        response = RemoteManager.getInstance().getRemoteServicesManager().getSecondDishService().read(filters);
        if ((boolean) response.get("success")) {
            JSONObject data = (JSONObject) response.get("data");
            result.addAll(parseIntoRows(data, "second"));
        }

        response = RemoteManager.getInstance().getRemoteServicesManager().getSideDishService().read(filters);
        if ((boolean) response.get("success")) {
            JSONObject data = (JSONObject) response.get("data");
            result.addAll(parseIntoRows(data, "side"));
        }

        response = RemoteManager.getInstance().getRemoteServicesManager().getSweetDishService().read(filters);
        if ((boolean) response.get("success")) {
            JSONObject data = (JSONObject) response.get("data");
            result.addAll(parseIntoRows(data, "sweet"));
        }
        return result;
    }

    protected ArrayList<Dish> parseIntoRows(JSONObject data, String type) throws Exception {
        ArrayList<Dish> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject dish = (JSONObject) data.get(i);

            Dish item = new Dish(this, dish);
            item.getType().setValue(type);
            item.updateService();
            list.add(item);
        }

        return list;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        return null;
    }

    @FXML
    public void add() throws Exception {
        addIntoTable(new Dish(this));
    }

}
