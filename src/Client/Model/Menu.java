package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import Shared.DishService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import org.json.simple.JSONObject;
import java.util.ArrayList;

public class Menu extends AbstractRowModel {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private Button first = new Button();
    private Button second = new Button();
    private Button side = new Button();
    private Button sweet = new Button();

    public Menu(AbstractTableController tableController) throws Exception {
        this(tableController, 0, "", "", "", "");
    }

    public Menu(AbstractTableController tableController, Integer id, String first, String second, String side, String sweet) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getMenuService(), tableController);

        setId(id);
        setFirst(first);
        setSecond(second);
        setSide(side);
        setSweet(sweet);
    }

    public void prepareButtones() {
        fillButton(first, "first");
        fillButton(second, "second");
        fillButton(side, "side");
        fillButton(sweet, "sweet");
    }

    private void fillButton(Button button, String parameter) {
        try {
            DishService service = RemoteManager.getInstance().getRemoteServicesManager().getDishService();

            JSONObject result = service.getDishesByCategoryName(parameter);

            ArrayList<String> items = new ArrayList<>();
            JSONObject data = (JSONObject) result.get("data");

            for (int i = 0; i < data.size(); i++) {
                JSONObject dish = (JSONObject) data.get(i);
                items.add((String) dish.get("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Button getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first.setText(first);
    }

    public Button getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second.setText(second);
    }

    public Button getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side.setText(side);
    }

    public Button getSweet() {
        return sweet;
    }

    public void setSweet(String sweet) {
        this.sweet.setText(sweet);
    }

    @Override
    protected JSONObject makeRequest() {
        return null;
    }
}