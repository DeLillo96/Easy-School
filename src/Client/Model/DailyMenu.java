package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import Shared.AssignService;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

public class DailyMenu extends AbstractRowModel {
    private Text first = new Text();
    private Text second = new Text();
    private Text side = new Text();
    private Text sweet = new Text();
    private CheckBox select = new CheckBox();
    private Integer calendarId;

    public DailyMenu(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public DailyMenu(AbstractTableController tableController, JSONObject menu) throws Exception {

        super(RemoteManager.getInstance().getRemoteServicesManager().getMenuService(), tableController, menu);

        if (data.size() > 0) {
            first.setText((String) ((JSONObject) data.get("first")).get("name"));
            second.setText((String) ((JSONObject) data.get("second")).get("name"));
            side.setText((String) ((JSONObject) data.get("side")).get("name"));
            sweet.setText((String) ((JSONObject) data.get("sweet")).get("name"));
        }

        events();
        buttons.getChildren().remove(delete);
    }

    public void events() {
        select.setOnAction(event -> needToSave());
    }

    @Override
    public void save() {
        try {
            JSONObject result;
            AssignService dailyMenuService = RemoteManager.getInstance().getRemoteServicesManager().getDailyMenuService();
            result = select.isSelected() ?
                    dailyMenuService.assign(getCalendarId(), getId()) :
                    dailyMenuService.deAssign(getCalendarId(), getId());
            refreshModel();
            save.getStyleClass().remove("red-button");
            notifyResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError("500 Server Error");
        }
    }

    @Override
    protected void refreshModel() {

    }

    public Integer getId() {
        return Integer.parseInt((String) data.get("id"));
    }

    public Text getFirst() {
        return first;
    }

    public String getStringFirst() {
        return first.getText();
    }

    public void setFirst(Text first) {
        this.first = first;
    }

    public void setFirst(String first) {
        if (first != null) this.first.setText(first);
    }

    public Text getSecond() {
        return second;
    }

    public String getStringSecond() {
        return second.getText();
    }

    public void setSecond(Text second) {
        this.second = second;
    }

    public void setSecond(String second) {
        if (second != null) this.second.setText(second);
    }

    public Text getSide() {
        return side;
    }

    public String getStringSide() {
        return side.getText();
    }

    public void setSide(Text side) {
        this.side = side;
    }

    public void setSide(String side) {
        if (side != null) this.side.setText(side);
    }

    public Text getSweet() {
        return sweet;
    }

    public String getStringSweet() {
        return sweet.getText();
    }

    public void setSweet(Text sweet) {
        this.sweet = sweet;
    }

    public void setSweet(String sweet) {
        if (sweet != null) this.sweet.setText(sweet);
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }
}