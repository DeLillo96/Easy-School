package Client.Model;

import Client.Controller.AbstractTableController;
import Client.Remote.RemoteManager;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

public class DailyDish extends AbstractRowModel {
    private Text name = new Text();
    private Text type = new Text();
    private CheckBox select = new CheckBox();
    private Integer calendarId;

    public DailyDish(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public DailyDish(AbstractTableController tableController, JSONObject dish) throws Exception {

        super(RemoteManager.getInstance().getRemoteServicesManager().getDishService(), tableController, dish);

        select.setTooltip(new Tooltip("Add/remove daily dish"));

        refreshModel();
    }

    @Override
    protected void refreshModel() {
        if (data.size() > 0) {
            name.setText((String) data.get("name"));
            type.setText((String) data.get("type"));
        }
    }

    public Integer getId() {
        return Integer.parseInt((String) data.get("id"));
    }

    public Text getName() {
        return name;
    }

    public void setName(Text name) {
        this.name = name;
    }

    public Text getType() {
        return type;
    }

    public void setType(Text type) {
        this.type = type;
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