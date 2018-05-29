package Client.Model;

import Client.Controller.AbstractTableController;
import Client.Controller.DishController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import Shared.BaseService;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.json.simple.JSONObject;

public class Dish extends AbstractRowModel {
    protected DishController controller;

    private TextField name = new TextField();
    private ChoiceBox type = new ChoiceBox();

    public Dish(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public Dish(AbstractTableController tableController, JSONObject data) throws Exception {
        super(null, tableController, data);

        controller = (DishController) tableController;
        type.setItems(FXCollections.observableArrayList("first", "second", "side", "sweet"));
        getType().setPrefSize(380, 40);
        getType().setMinSize(380, 40);
        getType().setMaxSize(380, 40);
        refreshModel();
        events();
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();

        Button recipes = new Button();
        defineImageButton(recipes, "Client/Resources/Images/recipes.png");
        recipes.setOnAction(actionEvent -> openRecipesPopup());
        recipes.setTooltip(new Tooltip("Show ingredients"));

        if (data.size() == 0) {
            recipes.setVisible(false);
        }

        getButtons().getChildren().addAll(recipes);
    }

    @Override
    protected void refreshModel() {
        setName((String) data.get("name"));
        save.getStyleClass().remove("red-button");
    }

    public void save() {
        if(!updateService()) return;
        super.save();
    }

    /**
     * Method used to set listeners and related events to trigger
     */
    public void events() {
        name.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("name", newText);
        });
    }

    /**
     * Method which opens a new recipe popup referred to current dish
     */
    private void openRecipesPopup() {
        ControllerManager.getInstance().renderRecipes(this);
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

    public ChoiceBox getType() {
        return type;
    }

    public void setType(ChoiceBox type) {
        this.type = type;
    }

    public boolean updateService() {
        try{
            switch ((String) type.getValue()) {
                case "first":
                    service = RemoteManager.getInstance().getRemoteServicesManager().getFirstDishService();
                    break;
                case "second":
                    service = RemoteManager.getInstance().getRemoteServicesManager().getSecondDishService();
                    break;
                case "side":
                    service = RemoteManager.getInstance().getRemoteServicesManager().getSideDishService();
                    break;
                case "sweet":
                    service = RemoteManager.getInstance().getRemoteServicesManager().getSweetDishService();
                    break;
                default: throw new Exception("No type is selected");
            }
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError(e.getMessage());
            return false;
        }
        return true;
    }
}
