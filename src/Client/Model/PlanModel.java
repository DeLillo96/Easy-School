package Client.Model;

import Client.Controller.AbstractTableController;
import Client.Remote.RemoteManager;
import Shared.PlaceToPlaceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class PlanModel extends AbstractRowModel {
    private Text address = new Text();
    private Text name = new Text();
    private ChoiceBox bus = new ChoiceBox();
    private Button up = new Button();
    private Button down = new Button();

    private Trip trip;
    private JSONObject buses = new JSONObject();

    public PlanModel(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public PlanModel(AbstractTableController tableController, JSONObject data) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getBusService(), tableController, data);

        bus.setTooltip(new Tooltip("set vehicle to"));

        buttons.getChildren().removeAll(save, delete);
        refreshModel();
        events();
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();

        up = new Button();
        defineImageButton(up, "Client/Resources/Images/up.png");
        up.setOnAction(actionEvent -> up());
        up.setTooltip(new Tooltip());
        
        down = new Button();
        defineImageButton(down, "Client/Resources/Images/down.png");
        down.setOnAction(actionEvent -> down());
        down.setTooltip(new Tooltip());

        buttons.getChildren().addAll(up, down);
    }

    private void up() {
        ObservableList<AbstractRowModel> items = controller.getTableView().getItems();
        int i = items.indexOf(this);
        if(i > 0) {
            PlanModel switched = (PlanModel) items.get(i - 1);
            items.set(i - 1, this);
            items.set(i, switched);
            int j = i > 1 ? i - 2 : i - 1;
            int jMax = items.size() == i + 1 ? i : i + 1;
            for (; j < jMax ; j++) {
                items.get(j).refreshModel();
            }
        }
    }

    private void down() {
        ObservableList<AbstractRowModel> items = controller.getTableView().getItems();
        int i = items.indexOf(this);
        if(i + 1 < items.size()) {
            PlanModel switched = (PlanModel) items.get(i + 1);
            items.set(i + 1, this);
            items.set(i, switched);
            int j = i - 1 >= 0 ? i - 1 : i;
            int jMax = items.size() == i + 2 ? i + 1 : i + 2;
            for (; j < jMax ; j++) {
                items.get(j).refreshModel();
            }
        }
    }

    public void events() {
        bus.setOnShowing(o -> fillFromToChoiceBox(bus, buses, +1));
    }

    protected void fillFromToChoiceBox(ChoiceBox choiceBox, JSONObject toFill, Integer toIndex) {
        try {
            ObservableList<AbstractRowModel> rows = controller.getTableView().getItems();
            int i = rows.indexOf(this);
            if(i + toIndex < rows.size()) {
                PlaceToPlaceService service = RemoteManager.getInstance().getRemoteServicesManager().getPlaceToPlaceService();
                JSONObject response = service.getBusesFromToPlaces(getId(), ((PlanModel) rows.get(i + toIndex)).getId());
                JSONObject data = (JSONObject) response.get("data");

                ArrayList items = new ArrayList();
                toFill.clear();
                for (i = 0; i < data.size(); i++) {
                    JSONObject bus = (JSONObject) data.get(0);
                    toFill.put(i, bus);
                    items.add(bus.get("licensePlate") + " - " + bus.get("companyName"));
                }
                if(data.size() == 0) items.add("Rent a bus");

                choiceBox.setItems(FXCollections.observableArrayList(items));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void refreshModel() {
        setAddress((String) data.get("address"));
        setName((String) data.get("name"));
        getBus().setValue("");
    }

    public Integer getId() {
        return Integer.parseInt((String) data.get("id"));
    }

    public Text getAddress() {
        return address;
    }

    public void setAddress(Text address) {
        this.address = address;
    }

    public void setAddress(String address) {
        this.address.setText(address);
    }

    public String getStringAddress() {
        return address.getText();
    }

    public Text getName() {
        return name;
    }

    public void setName(Text name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public String getStringName() {
        return name.getText();
    }

    public ChoiceBox getBus() {
        return bus;
    }

    public void setBus(ChoiceBox bus) {
        this.bus = bus;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Integer getSelectedBusId() {
        JSONObject selectedBus = (JSONObject) buses.get(bus.getSelectionModel().getSelectedIndex());
        return Integer.parseInt((String) selectedBus.get("id"));
    }
}
