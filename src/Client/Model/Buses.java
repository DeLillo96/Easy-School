package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import Shared.RelationService;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.json.simple.JSONObject;

public class Buses extends AbstractRowModel {
    private TextField code = new TextField();
    private TextField companyName = new TextField();
    private CheckBox select = new CheckBox();

    private Trip trip;

    public Buses(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public Buses(AbstractTableController tableController, JSONObject data) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getBusService(), tableController, data);

        select.setTooltip(new Tooltip("Add/remove bus"));

        refreshModel();
        events();
    }

    public void events() {
        companyName.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("companyName", newText);
        });
        code.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("code", newText);
        });
        select.setOnAction(event -> needToSave());
    }

    @Override
    public void save() {
        try {
            JSONObject result = service.save(getData());
            if ((boolean) result.get("success")) {
                setData((JSONObject) ((JSONObject) result.get("data")).get(0));
                RelationService busTripService = RemoteManager.getInstance().getRemoteServicesManager().getBusTripService();
                result = select.isSelected() ?
                        busTripService.assign(trip.getId(), getId()) :
                        busTripService.deAssign(trip.getId(), getId());
                        busTripService.deAssign(trip.getId(), getId());
            }
            save.getStyleClass().remove("red-button");
            notifyResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    @Override
    protected void refreshModel() {
        setCode((String) data.get("code"));
        setCompanyName((String) data.get("companyName"));
    }

    public Integer getId() {
        return Integer.parseInt((String) data.get("id"));
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

    public String getStringCode() {
        return code.getText();
    }

    public TextField getCompanyName() {
        return companyName;
    }

    public void setCompanyName(TextField companyName) {
        this.companyName = companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.setText(companyName);
    }

    public String getStringCompanyName() {
        return companyName.getText();
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
