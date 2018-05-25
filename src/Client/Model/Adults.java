package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import Shared.RelationService;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.Date;

public class Adults extends AbstractRowModel {
    private TextField name = new TextField();
    private TextField surname = new TextField();
    private TextField fiscalCode = new TextField();
    private DatePicker birthDate = new DatePicker();
    private TextField telephone = new TextField();
    private CheckBox select = new CheckBox();
    private Children child;

    public Adults(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public Adults(AbstractTableController tableController, JSONObject data) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getAdultService(), tableController, data);

        select.setTooltip(new Tooltip("Add/remove parent"));

        refreshModel();
        events();
    }

    public void events() {
        name.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("name", newText);
        });
        surname.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("surname", newText);
        });
        fiscalCode.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("fiscalCode", newText);
        });
        telephone.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("telephone", newText);
        });
        birthDate.setOnAction(event -> {
            needToSave();
            data.put("birthDate", birthDate.getValue().toString());
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
                RelationService parentService = RemoteManager.getInstance().getRemoteServicesManager().getParentService();
                result = select.isSelected() ?
                        parentService.assign(child.getId(), getId()) :
                        parentService.deAssign(child.getId(), getId());
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
        setSurname((String) data.get("surname"));
        setFiscalCode((String) data.get("fiscalCode"));
        setTelephone((String) data.get("telephone"));
        setBirthDate((CharSequence) data.get("birthDate"));
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

    public TextField getSurname() {
        return surname;
    }

    public void setSurname(TextField surname) {
        this.surname = surname;
    }

    public void setSurname(String surname) {
        this.surname.setText(surname);
    }

    public String getStringSurname() {
        return surname.getText();
    }

    public TextField getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(TextField fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode.setText(fiscalCode);
    }

    public String getStringFiscalCode() {
        return fiscalCode.getText();
    }

    public DatePicker getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(CharSequence birthDate) {
        if (birthDate != null) this.birthDate.setValue(LocalDate.parse(birthDate));
    }

    public Date getDateOnDatePicker() {
        //return birthDate.getValue();
        return new Date();
    }

    public TextField getTelephone() {
        return telephone;
    }

    public void setTelephone(TextField telephone) {
        this.telephone = telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone.setText(telephone);
    }

    public String getStringTelephone() {
        return telephone.getText();
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    public Children getChild() {
        return child;
    }

    public void setChild(Children child) {
        this.child = child;
    }
}
