package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import Shared.RelationService;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.json.simple.JSONObject;

public class Suppliers extends AbstractRowModel {
    private TextField name = new TextField();
    private TextField iva = new TextField();
    private CheckBox select = new CheckBox();
    private Aliment aliment;

    public Suppliers(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public Suppliers(AbstractTableController tableController, JSONObject data) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getSupplierService(), tableController, data);

        select.setTooltip(new Tooltip("Add/remove supplier"));

        refreshModel();
        events();
    }

    /**
     * Method used to set listeners and related events to trigger
     */
    public void events() {
        name.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("name", newText);
        });
        iva.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("iva", newText);
        });
        select.setOnAction(event -> needToSave());
    }

    @Override
    public void save() {
        try {
            JSONObject result = service.save(getData());
            if ((boolean) result.get("success")) {
                setData((JSONObject) ((JSONObject) result.get("data")).get(0));
                refreshModel();
                save.getStyleClass().remove("red-button");
                RelationService supplyService = RemoteManager.getInstance().getRemoteServicesManager().getSupplyingService();
                result = select.isSelected() ?
                        supplyService.assign(aliment.getId(), getId()) :
                        supplyService.deAssign(aliment.getId(), getId());
                enableButtons();
            }
            notifyResult(result);
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    @Override
    protected void refreshModel() {
        setName((String) data.get("name"));
        setIva((String) data.get("iva"));
    }

    public Integer getId() {
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

    public TextField getIva() {
        return iva;
    }

    public void setIva(TextField iva) {
        this.iva = iva;
    }

    public void setIva(String iva) {
        this.iva.setText(iva);
    }

    public String getStringIva() {
        return iva.getText();
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    public Aliment getAliment() {
        return aliment;
    }

    public void setAliment(Aliment aliment) {
        this.aliment = aliment;
    }
}
