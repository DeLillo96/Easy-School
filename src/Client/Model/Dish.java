package Client.Model;

import Client.Controller.AbstractTableController;
import Client.Controller.DishController;
import Client.ControllerManager;
import Shared.BaseService;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.json.simple.JSONObject;

public class Dish extends AbstractRowModel {
    protected DishController controller;

    private TextField name = new TextField();

    @Deprecated
    public Dish(AbstractTableController tableController) throws Exception {
        this(null, tableController, new JSONObject());
    }

    public Dish(BaseService service, AbstractTableController tableController) throws Exception {
        this(service, tableController, new JSONObject());
    }

    public Dish(BaseService service, AbstractTableController tableController, JSONObject data) throws Exception {
        super(service, tableController, data);

        controller = (DishController) tableController;
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

    public void setName(TextField name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public String getStringName() {
        return name.getText();
    }

    public void setCategory(JSONObject jsonCategory) {
        data.put("dishCategory", jsonCategory);
    }
}
