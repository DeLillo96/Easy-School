package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.io.IOException;

public class Menu extends AbstractRowModel {
    private Text first = new Text();
    private Text second = new Text();
    private Text side = new Text();
    private Text sweet = new Text();

    private Button modify;

    public Menu(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public Menu(AbstractTableController tableController, JSONObject menu) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getMenuService(), tableController, menu);

        if (data.size() > 0) {
            first.setText((String) ((JSONObject) data.get("first")).get("name"));
            second.setText((String) ((JSONObject) data.get("second")).get("name"));
            side.setText((String) ((JSONObject) data.get("side")).get("name"));
            sweet.setText((String) ((JSONObject) data.get("sweet")).get("name"));
        }
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();

        modify = new Button();
        defineImageButton(modify, "Client/Resources/Images/modify.png");
        modify.setOnAction(actionEvent -> modify());

        getButtons().getChildren().add(modify);
        getButtons().getChildren().remove(save);
    }

    public void modify() {
        try {
            ControllerManager.getInstance().renderDishes(this);
        } catch (IOException e) {
            ControllerManager.getInstance().notifyError("Rendering Dishes Error.7");
        }
    }

    public void refreshRow() {
        first.setText("first baresh");
        second.setText("second baresh");
        side.setText("side baresh");
        sweet.setText("sweet baresh");
    }

    public void setDishData(Integer dishId, String dishName, String dishCategory) {
        JSONObject dish = (JSONObject) data.get(dishCategory);
        dish.put("id", dishId);
        dish.put("name", dishName);
        refreshRow();
        needToSave();
    }

    public Text getFirst() {
        return first;
    }

    public void setFirst(Text first) {
        this.first = first;
    }

    public String getFirstString() {
        return first.getText();
    }

    public Text getSecond() {
        return second;
    }

    public void setSecond(Text second) {
        this.second = second;
    }

    public String getSecondString() {
        return second.getText();
    }

    public Text getSide() {
        return side;
    }

    public void setSide(Text side) {
        this.side = side;
    }

    public String getSideString() {
        return side.getText();
    }

    public Text getSweet() {
        return sweet;
    }

    public void setSweet(Text sweet) {
        this.sweet = sweet;
    }

    public String getSweetString() {
        return sweet.getText();
    }
}