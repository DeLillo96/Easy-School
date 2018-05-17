package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import javafx.scene.control.Button;
import org.json.simple.JSONObject;

public class Menu extends AbstractRowModel {
    private Button first;
    private Button second;
    private Button side;
    private Button sweet;

    public Menu(AbstractTableController tableController) throws Exception {
        this(tableController, null);
    }

    public Menu(AbstractTableController tableController, JSONObject menu) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getMenuService(), tableController, menu);
        //refreshButtonsRow();
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();

        first = new Button();
        first.getStyleClass().add("button-table");
        first.setOnAction(e -> ControllerManager.getInstance().renderFirstDishes(this, getFirstString()));

        second = new Button();
        second.getStyleClass().add("button-table");
        second.setOnAction(e -> ControllerManager.getInstance().renderSecondDishes(this, getSecondString()));

        side = new Button();
        side.getStyleClass().add("button-table");
        side.setOnAction(e -> ControllerManager.getInstance().renderSideDishes(this, getSideString()));

        sweet = new Button();
        sweet.getStyleClass().add("button-table");
        sweet.setOnAction(e -> ControllerManager.getInstance().renderSideDishes(this, getSideString()));
    }

    private void refreshButtonsRow() {
        /*
        JSONObject dish = (JSONObject) data.get(dishCategory);
        String name = "";
        JSONObject category = null;
        if(dish != null) {
            name = (String) dish.get("name");
            button.setText(name);

            category = (JSONObject) dish.get("dishCategory");
        }

        String finalName = name;
        JSONObject finalCategory = category;
        button.getStyleClass().add("button-table");
        */
        //todo refresh buttons
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

    public String getFirstString() {
        return first.getText();
    }

    public void setFirst(String first) {
        this.first.setText(first);
    }

    public Button getSecond() {
        return second;
    }

    public String getSecondString() {
        return second.getText();
    }

    public void setSecond(String second) {
        this.second.setText(second);
    }

    public Button getSide() {
        return side;
    }

    public String getSideString() {
        return side.getText();
    }

    public void setSide(String side) {
        this.side.setText(side);
    }

    public Button getSweet() {
        return sweet;
    }

    public String getSweetString() {
        return sweet.getText();
    }

    public void setSweet(String sweet) {
        this.sweet.setText(sweet);
    }
}