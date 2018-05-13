package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
//import com.sun.javafx.scene.control.DoubleField;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;
import java.util.Date;

public class Places extends AbstractRowModel {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private TextField name = new TextField();
    private TextField address = new TextField();
    private TextField cost = new TextField();
    private CheckBox select = new CheckBox();
    private int tripId;

    public Places(AbstractTableController tableController, int tripId) throws Exception {
        this(tableController, 0, "", "", ""+0, new CheckBox(), tripId);
    }

    public Places(AbstractTableController tableController, Integer id, String name, String address, String cost, CheckBox checkBox, int tripId) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getPlaceService(), tableController);

        setId(id);
        setName(name);
        setAddress(address);
        setCost(cost);
        setTripId(tripId);
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();

        Button arrivalBuses = new Button();
        defineImageButton(arrivalBuses, "Client/Resources/Images/parents.png");
        arrivalBuses.setOnAction(actionEvent -> arrivalBuses());

        Button startBuses = new Button();
        defineImageButton(startBuses, "Client/Resources/Images/eating.png");
        startBuses.setOnAction(actionEvent -> startBuses());

        getButtons().getChildren().addAll(arrivalBuses, startBuses);
    }

    public void arrivalBuses() {
        try {
            ControllerManager.getInstance().renderSetArrivalBuses(this.tripId, this);
        } catch(Exception e) { e.printStackTrace(); }
    }

    public void startBuses() {
        try {
            ControllerManager.getInstance().renderSetStartBuses(this.tripId, this);
        } catch(Exception e) { e.printStackTrace(); }
    }

    @Override
    public void save() {
        try {
            JSONObject result = RemoteManager.getInstance().getRemoteServicesManager().getPlaceService().save( makeRequest() );
            if((boolean)result.get("success"))
            {
                JSONObject check = new JSONObject();
                check.put("tripId", tripId);
                check.put("placeId", this.getId());
                check.put("check", getSelect().isSelected());
                result = RemoteManager.getInstance().getRemoteServicesManager().getPlaceService().setVisitedPlaceFromJSON(check);
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
        request.put("name", getStringName());
        request.put("address", getStringAddress());
        //request.put("cost", getIntegerCost());

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

    public TextField getName() {
        return name;
    }

    public String getStringName() {
        return name.getText();
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public TextField getAddress() {
        return address;
    }

    public String getStringAddress() {
        return address.getText();
    }

    public void setAddress(TextField address) {
        this.address = address;
    }

    public void setAddress(String address) {
        this.address.setText(address);
    }

    public TextField getCost() {
        return cost;
    }

    public String getStringCost() {
        return cost.getText();
    }

    public void setCost(TextField cost) {
        this.cost = cost;
    }

    public void setCost(String cost) {
        this.cost.setText(cost);
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
