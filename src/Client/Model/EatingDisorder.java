package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import org.json.simple.JSONObject;
import java.util.Date;

public class EatingDisorder extends AbstractRowModel {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private TextField name = new TextField();
    private ChoiceBox<String> type = new ChoiceBox<>();
    private int childId;

    public EatingDisorder(AbstractTableController tableController, int childId) throws Exception {
        this(tableController, 0, "", childId);
    }

    public EatingDisorder(AbstractTableController tableController, Integer id, String name, int childId) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getAlimentService(), tableController);

        setId(id);
        setName(name);
        getType().getItems().addAll(null, "Allergy", "Intolerance");
        getType().setPrefSize(380,40);
        getType().setMinSize(380, 40);
        getType().setMaxSize(380,40);
        setChildId(childId);
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();
    }

    @Override
    public void save() {
        try {
            JSONObject result = RemoteManager.getInstance().getRemoteServicesManager().getAlimentService().save( makeRequest() );
            if((boolean)result.get("success"))
            {
                JSONObject check = new JSONObject();
                check.put("childId", childId);
                check.put("alimentId", this.getId());
                check.put("type", getType().getValue());
                result = RemoteManager.getInstance().getRemoteServicesManager().getEatingDisorderService().setDisorderFromJSON(check);
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

    public ChoiceBox<String> getType() {
        return type;
    }

    public void setType(ChoiceBox<String> type) {
        this.type = type;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }
}
