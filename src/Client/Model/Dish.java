package Client.Model;

import Client.Controller.AbstractTableController;
import Client.Controller.DishController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import Shared.BaseService;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

public class Dish extends AbstractRowModel {
    protected DishController controller;

    private TextField name = new TextField();

    public Dish(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public Dish(AbstractTableController tableController, JSONObject data) throws Exception {
        super((BaseService) RemoteManager.getInstance().getRemoteServicesManager().getDishService(), tableController, data);

        controller = (DishController) tableController;
        setName((String) data.get("name"));
        events();
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();

        Button set = new Button();
        defineImageButton(set, "Client/Resources/Images/set.png");
        set.setOnAction(actionEvent -> controller.setMenuDish(getId(), getStringName()));

        Button recipes = new Button();
        defineImageButton(recipes, "Client/Resources/Images/recipes.png");
        recipes.setOnAction(actionEvent -> openRecipesPopup());

        getButtons().getChildren().addAll(set, recipes);
    }

    public void events() {
        name.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("name", newText);
        });
    }

    private void openRecipesPopup() {
        ControllerManager.getInstance().renderRecipes(this);
    }

    public int getId() {
        return Integer.parseInt((String) data.get("id"));
    }

    public TextField getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public String getStringName() {
        return name.getText();
    }

    public void setCategory(JSONObject jsonCategory) {
        data.put("dishCategory", jsonCategory);
    }
}
