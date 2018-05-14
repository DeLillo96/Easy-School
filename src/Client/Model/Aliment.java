package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import Shared.AssignService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

public class Aliment extends AbstractRowModel {
    private final SimpleIntegerProperty dishId = new SimpleIntegerProperty(0);
    private TextField name = new TextField();

    private CheckBox select = new CheckBox();

    public Aliment(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public Aliment(AbstractTableController tableController, JSONObject data) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getAlimentService(), tableController, data);
        setName((String) data.get("name"));
        events();
    }

    public void events() {
        name.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("name", newText);
        });

        select.setOnAction(event -> needToSave());
    }

    public int getId() {
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

    public int getDishId() {
        return dishId.get();
    }

    public void setDishId(int dishId) {
        this.dishId.set(dishId);
    }

    public SimpleIntegerProperty dishIdProperty() {
        return dishId;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    @Override
    public void save() {
        try {
            JSONObject result = service.save(getData());
            if ((boolean) result.get("success")) {
                setData((JSONObject) ((JSONObject) result.get("data")).get(0));

                AssignService recipesService = RemoteManager.getInstance().getRemoteServicesManager().getRecipesService();
                result = select.isSelected() ?
                        recipesService.assign(getDishId(), getId()) :
                        recipesService.deAssign(getDishId(), getId());

                save.getStyleClass().remove("red-button");
            }
            notifyResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError("500 Server Error");
        }
    }
}
