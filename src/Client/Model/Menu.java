package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import Shared.DishService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Menu extends AbstractRowModel {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private ChoiceBox<String> first = new ChoiceBox<>();
    private ChoiceBox<String> second = new ChoiceBox<>();
    private ChoiceBox<String> side = new ChoiceBox<>();
    private ChoiceBox<String> sweet = new ChoiceBox<>();

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

    public void prepareChoiceBoxes() {
        fillChoiceBox(first, "first");
        fillChoiceBox(second, "second");
        fillChoiceBox(side, "side");
        fillChoiceBox(sweet, "sweet");
    }

    private void fillChoiceBox(ChoiceBox choiceBox, String parameter) {
        choiceBox.getStyleClass().add("choice-box-table");
        try {
            DishService service = RemoteManager.getInstance().getRemoteServicesManager().getDishService();

            JSONObject result = service.getDishesByCategoryName(parameter);

            ArrayList<String> items = new ArrayList<>();
            JSONObject data = (JSONObject) result.get("data");

            for (int i = 0; i < data.size(); i++) {
                JSONObject dish = (JSONObject) data.get(i);
                items.add((String) dish.get("name"));
            }

            choiceBox.setItems(FXCollections.observableArrayList(items));
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

    public ChoiceBox<String> getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first.setValue(first);
    }

    public ChoiceBox<String> getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second.setValue(second);
    }

    public ChoiceBox<String> getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side.setValue(side);
    }

    public ChoiceBox<String> getSweet() {
        return sweet;
    }

    public void setSweet(String sweet) {
        this.sweet.setValue(sweet);
    }

    @Override
    protected JSONObject makeRequest() {
        return null;
    }
}