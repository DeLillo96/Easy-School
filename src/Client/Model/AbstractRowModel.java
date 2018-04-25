package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Shared.BaseService;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.json.simple.JSONObject;

public abstract class AbstractRowModel {
    protected Button save = new Button();
    protected Button delete = new Button();
    protected HBox buttons = new HBox(save, delete);

    protected static BaseService service;
    protected static AbstractTableController controller;

    public AbstractRowModel(BaseService baseService, AbstractTableController tableController) {
        service = baseService;
        controller = tableController;

        buttons.getStyleClass().add("row-HBox");
        initializeButtons();
    }

    public HBox getButtons() {
        return buttons;
    }

    public void setButtons(HBox buttons) {
        this. buttons = buttons;
    }

    protected void initializeButtons() {
        defineImageButton(save, "Client/Resources/Images/save.png");
        save.setOnAction(actionEvent -> save());

        defineImageButton(delete, "Client/Resources/Images/delete.png");
        delete.setOnAction(actionEvent -> delete());
    }

    protected void defineImageButton(Button button, String urlImage) {
        ObservableList<String> classes = button.getStyleClass();
        classes.add("row-button");
        classes.add("radius-15");
        ImageView imageView = new ImageView( urlImage );

        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        button.setGraphic(imageView);
    }

    public void save() {
        try {
            JSONObject result = service.save( makeRequest() );
            save.getStyleClass().remove("red-button");
            notifyResult(result);
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError("500 Server Error");
        }
    }

    public void delete() {
        try {
            JSONObject result = service.delete( makeRequest() );
            notifyResult(result);
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError("500 Server Error");
        }
    }

    protected void notifyResult(JSONObject result) throws Exception {
        if((boolean) result.get("success")) {
            controller.filter(); //TODO remove filter & do single update.
            ControllerManager.getInstance().notifySuccess(result.get("messages").toString());
        } else {
            String errorMessage = result.get("messages").toString();
            throw new Exception(errorMessage);
        }
    }

    protected void needToSave() {
        save.getStyleClass().add("red-button");
    }

    protected abstract JSONObject makeRequest();
}
