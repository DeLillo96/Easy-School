package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

public class DailyTrips extends AbstractRowModel {
    private TextField code = new TextField();
    private Button child;

    public DailyTrips(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public DailyTrips(AbstractTableController tableController, JSONObject dailyTrip) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getDailyTripService(), tableController, dailyTrip);

        events();
        refreshModel();
    }

    /**
     * Method used to set listeners and related events to trigger
     */
    public void events() {
        code.textProperty().addListener((obj, oldText, newText) -> {
            needToSave();
            data.put("code", newText);
            child.setVisible(getId() != null);
        });
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();

        child = new Button();
        defineImageButton(child, "Client/Resources/Images/add.png");
        child.setOnAction(actionEvent -> openChildInTripPopup());

        child.setVisible(getId() != null);
        getButtons().getChildren().addAll(child);
    }

    private void openChildInTripPopup() {
        ControllerManager.getInstance().renderChildInTrip(this);
    }

    @Override
    public void refreshModel() {
        setCode((String) data.get("code"));

        save.getStyleClass().remove("red-button");
    }

    public Integer getId() {
        return data.get("id") != null ? Integer.parseInt((String) data.get("id")) : null;
    }

    public TextField getCode() {
        return code;
    }

    public void setCode(TextField code) {
        this.code = code;
    }

    public void setCode(String code) {
        this.code.setText(code);
    }

    public Integer getCalendarId() {
        return Integer.parseInt((String) ((JSONObject) data.get("day")).get("id"));
    }

    /**
     * Method used to link this dailyTrip object to a specific Calendar entity
     * @param calendarId (Id of the specific Calendar entity to link to)
     */
    public void setCalendarId(Integer calendarId) {
        JSONObject day = (JSONObject) data.get("day");
        if(day == null) {
            day = new JSONObject();
            data.put("day", day);
        }
        day.put("id", calendarId);
    }
}
