package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Shared.BaseService;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.json.simple.JSONObject;

public abstract class AbstractRowModel {
    protected JSONObject data;

    protected Button save = new Button();
    protected Button delete = new Button();
    protected HBox buttons = new HBox(save, delete);

    protected BaseService service;
    protected AbstractTableController controller;

    public AbstractRowModel(BaseService baseService, AbstractTableController tableController, JSONObject data) {
        service = baseService;
        controller = tableController;
        this.data = data;
        buttons.getStyleClass().add("row-HBox");
        initializeButtons();
    }

    public HBox getButtons() {
        return buttons;
    }

    public void setButtons(HBox buttons) {
        this.buttons = buttons;
    }

    protected void initializeButtons() {
        defineImageButton(save, "Client/Resources/Images/save.png");
        save.setOnAction(actionEvent -> save());
        save.setTooltip(new Tooltip("Save"));

        defineImageButton(delete, "Client/Resources/Images/delete.png");
        delete.setOnAction(actionEvent -> delete());
    }

    protected void defineImageButton(Button button, String urlImage) {
        ObservableList<String> classes = button.getStyleClass();
        classes.add("row-button");
        classes.add("radius-15");
        ImageView imageView = new ImageView(urlImage);

        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        button.setGraphic(imageView);
    }

    public void save() {
        try {
            JSONObject result = service.save(getData());
            if ((boolean) result.get("success")) {
                data = (JSONObject) ((JSONObject) result.get("data")).get(0);
                refreshModel();
                save.getStyleClass().remove("red-button");
                enableButtons();
            }
            notifyResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError("500 Server Error");
        }
    }

    public void delete() {
        try {
            JSONObject result = service.delete(getData());
            if ((boolean) result.get("success")) controller.delete(this);
            notifyResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void notifyResult(JSONObject result) {
        if ((boolean) result.get("success")) {
            ControllerManager.getInstance().notifySuccess(result.get("messages").toString());
        } else {
            ControllerManager.getInstance().notifyError(result.get("messages").toString());
        }
    }

    protected void needToSave() {
        ObservableList styleClasses = save.getStyleClass();
        if (!styleClasses.contains("red-button")) styleClasses.add("red-button");
        if (!controller.isNewRowFlag()) controller.setNewRowFlag(true);
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    protected void enableButtons() {
        for (Node button : buttons.getChildren()) {
            button.setVisible(true);
        }
    }

    protected abstract void refreshModel();
}
