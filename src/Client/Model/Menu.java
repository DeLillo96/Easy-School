package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import javafx.scene.control.Button;
import org.json.simple.JSONObject;

public class Menu extends AbstractRowModel {
    private Button first = new Button();
    private Button second = new Button();
    private Button side = new Button();
    private Button sweet = new Button();

    public Menu(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public Menu(AbstractTableController tableController, JSONObject menu) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getMenuService(), tableController, menu);
        refreshButtonsRow();
    }

    protected void refreshButtonsRow() {
        initializeButton(first, "first");
        initializeButton(second, "second");
        initializeButton(side, "side");
        initializeButton(sweet, "sweet");
    }

    private void initializeButton(Button button, String dishCategory) {
        String name = (String) ((JSONObject) data.get(dishCategory)).get("name");
        JSONObject category = (JSONObject) ((JSONObject) data.get(dishCategory)).get("dishCategory");

        button.setText(name);
        button.setOnAction(e -> openDishPopup(name, category));
        button.getStyleClass().add("button-table");
    }

    protected void openDishPopup(String dishName, JSONObject categoryName) {
        ControllerManager.getInstance().renderDishes(this, dishName, categoryName);
    }

    public void setDishData(Integer dishId, String dishName, String dishCategory) {
        JSONObject dish = (JSONObject) data.get(dishCategory);
        dish.put("id", dishId);
        dish.put("name", dishName);
        refreshButtonsRow();
        needToSave();
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
}