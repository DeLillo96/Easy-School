package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import Shared.RelationService;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.json.simple.JSONObject;

public class Places extends AbstractRowModel {
    private TextField name = new TextField();
    private TextField address = new TextField();
    private TextField cost = new TextField();
    private CheckBox select = new CheckBox();

    private DayTrips trip;

    public Places(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public Places(AbstractTableController tableController, JSONObject data) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getPlaceService(), tableController, data);

        select.setTooltip(new Tooltip("Add/remove visited place"));

        refreshModel();
        events();
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();

        Button buses = new Button();
        defineImageButton(buses, "Client/Resources/Images/bus.png");
        buses.setOnAction(actionEvent -> buses());
        buses.setTooltip(new Tooltip("Show buses"));

        if (data.size() == 0) {
            buses.setVisible(false);
        }

        getButtons().getChildren().addAll(buses);
    }

    public void buses() {
        ControllerManager.getInstance().renderAddBuses(this);
    }

    public void events() {
        name.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("name", newText);
        });
        address.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("address", newText);
        });
        cost.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("cost", newText);
        });
        select.setOnAction(event -> needToSave());
    }

    @Override
    public void save() {
        try {
            JSONObject result = service.save(getData());
            if ((boolean) result.get("success")) {
                setData((JSONObject) ((JSONObject) result.get("data")).get(0));
                RelationService tripPlaceService = RemoteManager.getInstance().getRemoteServicesManager().getTripPlaceService();
                result = select.isSelected() ?
                        tripPlaceService.assign(trip.getId(), getId()) :
                        tripPlaceService.deAssign(trip.getId(), getId());
                refreshModel();

                save.getStyleClass().remove("red-button");
                enableButtons();
            }
            notifyResult(result);
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    @Override
    protected void refreshModel() {
        setName((String) data.get("name"));
        setAddress((String) data.get("address"));
        setCost((String) data.get("cost"));
    }

    public Integer getId() {
        return Integer.parseInt((String) data.get("id"));
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public String getStringName() {
        return name.getText();
    }

    public TextField getAddress() {
        return address;
    }

    public void setAddress(TextField address) {
        this.address = address;
    }

    public void setAddress(String address) {
        this.address.setText(address);
    }

    public String getStringAddress() {
        return address.getText();
    }

    public TextField getCost() {
        return cost;
    }

    public void setCost(TextField cost) {
        this.cost = cost;
    }

    public void setCost(String cost) {
        this.cost.setText(cost);
    }

    public String getStringCost() {
        return cost.getText();
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    public DayTrips getTrip() {
        return trip;
    }

    public void setTrip(DayTrips trip) {
        this.trip = trip;
    }
}
