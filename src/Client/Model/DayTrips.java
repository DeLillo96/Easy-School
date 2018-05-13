package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;
import java.util.Date;

public class DayTrips extends AbstractRowModel {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private TextField name = new TextField();

    public DayTrips(AbstractTableController tableController) throws Exception {
        this(tableController, 0, "");
    }

    public DayTrips(AbstractTableController tableController, Integer id, String name) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getDayTripService(), tableController);

        setId(id);
        setName(name);
        //todo make parse to Date into DatePicker

        events();
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();

        Button buses = new Button();
        defineImageButton(buses, "Client/Resources/Images/parents.png");
        buses.setOnAction(actionEvent -> buses());

        Button places = new Button();
        defineImageButton(places, "Client/Resources/Images/eating.png");
        places.setOnAction(actionEvent -> places());

        getButtons().getChildren().addAll(buses, places);
    }

    public void events() {
        name.textProperty().addListener((obs, oldText, newText) -> needToSave());
    }

    public void buses() {
        try {
            ControllerManager.getInstance().renderAddBuses(this);
        } catch(Exception e) { e.printStackTrace(); }
    }

    public void places() {
        try {
            ControllerManager.getInstance().renderAddPlaces(this);
        } catch(Exception e) { e.printStackTrace(); }
    }

    @Override
    protected JSONObject makeRequest() {
        JSONObject request = new JSONObject();

        if(getId() != 0) request.put("id", getId());
        request.put("name", getStringName());

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

}
