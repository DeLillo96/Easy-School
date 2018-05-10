package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;
import java.util.Date;

public class Buses extends AbstractRowModel {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private TextField licensePlate = new TextField();
    private TextField companyName = new TextField();
    private CheckBox select = new CheckBox();
    private int tripId;

    public Buses(AbstractTableController tableController, int tripId) throws Exception {
        this(tableController, 0, "", "", new CheckBox(), tripId);
    }

    public Buses(AbstractTableController tableController, Integer id, String licensePlate, String companyName, CheckBox checkBox, int tripId) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getBusService(), tableController);

        setId(id);
        setLicensePlate(licensePlate);
        setCompanyName(companyName);
        setTripId(tripId);
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();
    }

    @Override
    public void save() {
        try {
            JSONObject result = RemoteManager.getInstance().getRemoteServicesManager().getBusService().save( makeRequest() );
            if((boolean)result.get("success"))
            {
                JSONObject check = new JSONObject();
                check.put("tripId", tripId);
                check.put("busId", this.getId());
                check.put("check", getSelect().isSelected());
                result = RemoteManager.getInstance().getRemoteServicesManager().getBusService().setUsedBusesFromJSON(check);
            }
            save.getStyleClass().remove("red-button");
            notifyResult(result);
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError("500 Server Error");
            e.printStackTrace();
        }
    }

    @Override
    protected JSONObject makeRequest() {
        JSONObject request = new JSONObject();

        if(getId() != 0) request.put("id", getId());
        request.put("licensePlate", getStringLicensePlate());
        request.put("companyName", getStringCompanyName());

        return request;
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

    public TextField getLicensePlate() {
        return licensePlate;
    }

    public String getStringLicensePlate() {
        return licensePlate.getText();
    }

    public void setLicensePlate(TextField licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate.setText(licensePlate);
    }

    public TextField getCompanyName() {
        return companyName;
    }

    public String getStringCompanyName() {
        return companyName.getText();
    }

    public void setCompanyName(TextField companyName) {
        this.companyName = companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.setText(companyName);
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
}
