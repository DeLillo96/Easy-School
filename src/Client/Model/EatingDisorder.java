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

public class EatingDisorder extends AbstractRowModel {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private TextField name = new TextField();
    private TextField type = new TextField();
    private CheckBox select = new CheckBox();

    public EatingDisorder(AbstractTableController tableController) throws Exception {
        this(tableController, 0, "", "", new CheckBox());
    }

    public EatingDisorder(AbstractTableController tableController, Integer id, String name, String type, CheckBox checkBox) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getAlimentService(), tableController);

        setId(id);
        setName(name);
        setType(type);
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();
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

    public TextField getType() {
        return type;
    }

    public String getStringType() { return type.getText(); }

    public void setType(TextField type) {
        this.type = type;
    }

    public void setType(String type) { this.type.setText(type);}

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }
}
