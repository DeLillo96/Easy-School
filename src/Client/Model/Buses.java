package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import Shared.AssignService;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

public class Buses extends AbstractRowModel {
    private TextField licensePlate = new TextField();
    private TextField companyName = new TextField();
    private CheckBox startSelect = new CheckBox();
    private CheckBox arrivalSelect = new CheckBox();
    private Places place;

    public Buses(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public Buses(AbstractTableController tableController, JSONObject data) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getBusService(), tableController, data);

        setLicensePlate((String) data.get("licensePlate"));
        setCompanyName((String) data.get("companyName"));
        events();
    }

    public void events() {
        companyName.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("companyName", newText);
        });
        licensePlate.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("licensePlate", newText);
        });
        arrivalSelect.setOnAction(event -> needToSave());
        startSelect.setOnAction(event -> needToSave());
    }

    @Override
    public void save() {
        try {
            JSONObject result = service.save(data);
            if ((boolean) result.get("success")) {
                setData((JSONObject) ((JSONObject) result.get("data")).get(0));
                AssignService busArrivalPlaceService = RemoteManager.getInstance().getRemoteServicesManager().getBusArrivalPlaceService();
                result = arrivalSelect.isSelected() ?
                        busArrivalPlaceService.assign(getId(), place.getId()) :
                        busArrivalPlaceService.deAssign(getId(), place.getId());

                AssignService busStartingPlaceService = RemoteManager.getInstance().getRemoteServicesManager().getBusStartingPlaceService();
                result = startSelect.isSelected() ?
                        busStartingPlaceService.assign(getId(), place.getId()) :
                        busStartingPlaceService.deAssign(getId(), place.getId());

            }
            save.getStyleClass().remove("red-button");
            notifyResult(result);
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError("500 Server Error");
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return Integer.parseInt((String) data.get("id"));
    }

    public TextField getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(TextField licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate.setText(licensePlate);
    }

    public String getStringLicensePlate() {
        return licensePlate.getText();
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

    public CheckBox getStartSelect() {
        return startSelect;
    }

    public void setStartSelect(CheckBox startSelect) {
        this.startSelect = startSelect;
    }

    public CheckBox getArrivalSelect() {
        return arrivalSelect;
    }

    public void setArrivalSelect(CheckBox arrivalSelect) {
        this.arrivalSelect = arrivalSelect;
    }

    public Places getPlace() {
        return place;
    }

    public void setPlace(Places place) {
        this.place = place;
    }
}
