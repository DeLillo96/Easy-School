package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.json.simple.JSONObject;

public class Trip extends AbstractRowModel {
    private TextField code = new TextField();
    private Button child;
    private Button place;
    private Button planning;

    public Trip(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public Trip(AbstractTableController tableController, JSONObject dailyTrip) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getDailyTripService(), tableController, dailyTrip);

        events();
        refreshModel();
    }

    public void events() {
        code.textProperty().addListener((obj, oldText, newText) -> {
            needToSave();
            data.put("code", newText);
            child.setVisible(getId() != null);
            place.setVisible(getId() != null);
        });
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();

        child = new Button();
        defineImageButton(child, "Client/Resources/Images/add.png");
        child.setOnAction(actionEvent -> openChildInTripPopup());
        child.setVisible(getId() != null);
        child.setTooltip(new Tooltip());

        place = new Button();
        defineImageButton(place, "Client/Resources/Images/addplace.png");
        place.setOnAction(actionEvent -> openPlaceInTripPopup());
        place.setVisible(getId() != null);
        place.setTooltip(new Tooltip());
        
        planning = new Button();
        defineImageButton(planning, "Client/Resources/Images/addbus.png");
        planning.setOnAction(actionEvent -> openBusPopup());
        planning.setVisible(false);
        planning.setTooltip(new Tooltip());

        getButtons().getChildren().addAll(child, place, planning);
    }

    private void openBusPopup() {
        ControllerManager.getInstance().renderAddBuses(this);
    }

    private void openPlaceInTripPopup() {
        ControllerManager.getInstance().renderAddPlaces(this);
    }

    private void openChildInTripPopup() {
        ControllerManager.getInstance().renderChildInTrip(this);
    }

    @Override
    public void refreshModel() {
        setCode((String) data.get("code"));

        try {
            Integer childCount = RemoteManager.getInstance().getRemoteServicesManager().getChildInTripService().leftCount(getId());
            Integer placeCount = RemoteManager.getInstance().getRemoteServicesManager().getTripPlaceService().rightCount(getId());

            warningButton(childCount > 0, child, "child");
            warningButton(placeCount > 0, place, "place");

            planning.setVisible(childCount > 0 && placeCount > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        save.getStyleClass().remove("red-button");
    }

    protected void warningButton(Boolean isWarning, Button btn, String tooltipMessage ){
        Tooltip tooltip = btn.getTooltip();
        if(isWarning) {
            btn.getStyleClass().remove("orange-button");
            tooltip.getStyleClass().remove("orange-button");

            tooltip.setText("Change selected " + tooltipMessage);
        } else {
            btn.getStyleClass().add("orange-button");
            tooltip.getStyleClass().add("orange-button");

            tooltip.setText("Need to set " + tooltipMessage);

        }
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

    public void setCalendarId(Integer calendarId) {
        JSONObject day = (JSONObject) data.get("day");
        if(day == null) {
            day = new JSONObject();
            data.put("day", day);
        }
        day.put("id", calendarId);
    }
}
