package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;
import java.util.Date;

public class ArrivalBuses extends AbstractRowModel {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private Text licensePlate = new Text();
    private Text companyName = new Text();
    private CheckBox select = new CheckBox();
    private int placeId;

    public ArrivalBuses(AbstractTableController tableController, int placeId) throws Exception {
        this(tableController, 0, "", "", new CheckBox(), placeId);
    }

    public ArrivalBuses(AbstractTableController tableController, Integer id, String licensePlate, String companyName, CheckBox checkBox, int placeId) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getBusService(), tableController);

        setId(id);
        setLicensePlate(licensePlate);
        setCompanyName(companyName);
        setPlaceId(placeId);
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();
        delete.setVisible(false);
    }

    @Override
    public void save() {
        try {
            JSONObject result;
            JSONObject check = new JSONObject();
            check.put("placeId", placeId);
            check.put("busId", this.getId());
            check.put("check", getSelect().isSelected());
            result = RemoteManager.getInstance().getRemoteServicesManager().getBusService().setArrivalBusesFromJSON(check);
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

    public Text getLicensePlate() {
        return licensePlate;
    }

    public String getStringLicensePlate() {
        return licensePlate.getText();
    }

    public void setLicensePlate(Text licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate.setText(licensePlate);
    }

    public Text getCompanyName() {
        return companyName;
    }

    public String getStringCompanyName() {
        return companyName.getText();
    }

    public void setCompanyName(Text companyName) {
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

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }
}
