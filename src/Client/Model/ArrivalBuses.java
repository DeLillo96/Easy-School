package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

public class ArrivalBuses extends AbstractRowModel {
    private Text licensePlate = new Text();
    private Text companyName = new Text();
    private CheckBox select = new CheckBox();

    private Places place;

    public ArrivalBuses(AbstractTableController tableController, int placeId) throws Exception {
        this(tableController, new JSONObject());
    }

    public ArrivalBuses(AbstractTableController tableController, JSONObject data) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getBusService(), tableController, data);

        setLicensePlate(licensePlate);
        setCompanyName(companyName);
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
            check.put("placeId", place.getId());
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

    public int getId() {
        return Integer.parseInt((String) data.get("id"));
    }

    public Text getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(Text licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate.setText(licensePlate);
    }

    public String getStringLicensePlate() {
        return licensePlate.getText();
    }

    public Text getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Text companyName) {
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

    public Places getPlace() {
        return place;
    }

    public void setPlace(Places place) {
        this.place = place;
    }
}
