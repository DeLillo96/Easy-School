package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.json.simple.JSONObject;

import java.time.LocalDate;

public class Children extends AbstractRowModel {
    private TextField name = new TextField();
    private TextField surname = new TextField();
    private TextField fiscalCode = new TextField();
    private DatePicker birthDate = new DatePicker();
    private Button parents;
    private Button disorder;

    public Children(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public Children(AbstractTableController tableController, JSONObject data) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getChildrenService(), tableController, data);

        refreshModel();
        events();
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();

        parents = new Button();
        defineImageButton(parents, "Client/Resources/Images/parents.png");
        parents.setOnAction(actionEvent -> parents());
        parents.setTooltip(new Tooltip("Show parents"));

        disorder = new Button();
        defineImageButton(disorder, "Client/Resources/Images/eating.png");
        disorder.setOnAction(actionEvent -> disorder());
        disorder.setTooltip(new Tooltip("Set eating disorders"));

        if (data.size() == 0) {
            parents.setVisible(false);
            disorder.setVisible(false);
        }
        getButtons().getChildren().addAll(parents, disorder);
    }

    /**
     * Method used to set listeners and related events to trigger
     */
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
        birthDate.setOnAction(event -> {
            needToSave();
            data.put("birthDate", birthDate.getValue().toString());
        });
    }

    @Override
    protected void refreshModel() {
        setName((String) data.get("name"));
        setSurname((String) data.get("surname"));
        setFiscalCode((String) data.get("fiscalCode"));
        setBirthDate((CharSequence) data.get("birthDate"));
    }

    public void parents() {
        ControllerManager.getInstance().renderAddAdults(this);
    }

    public void disorder() {
        ControllerManager.getInstance().renderAddEatingDisorders(this);
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
        if (name != null) this.name.setText(name);
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
        if (surname != null) this.surname.setText(surname);
    }

    public String getStringSurname() {
        return surname.getText();
    }

    public TextField getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(TextField fiscalCode) {
        if (fiscalCode != null) this.fiscalCode = fiscalCode;
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
}
