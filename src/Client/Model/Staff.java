package Client.Model;

import Client.Controller.AbstractTableController;
import Client.Remote.RemoteManager;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.time.LocalDate;

public class Staff extends AbstractRowModel {
    private TextField name = new TextField();
    private TextField surname = new TextField();
    private TextField fiscalCode = new TextField();
    private DatePicker birthDate = new DatePicker();
    private TextField mansion = new TextField();

    public Staff(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public Staff(AbstractTableController tableController, JSONObject data) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getStaffService(), tableController, data);

        refreshModel();
        events();
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();
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
        mansion.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("mansion", newText);
        });
        birthDate.setOnAction(event -> {
            needToSave();
            data.put("birthDate", birthDate.getValue().toString());
        });
    }

    @Override
    protected void refreshModel() {
        getMansion().setMaxHeight(40);
        setName((String) data.get("name"));
        setSurname((String) data.get("surname"));
        setFiscalCode((String) data.get("fiscalCode"));
        setBirthDate((CharSequence) data.get("birthDate"));
        setMansion((String) data.get("mansion"));
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

    public TextField getMansion() {
        return mansion;
    }

    public void setMansion(TextField mansion) {
        this.mansion = mansion;
    }

    public void setMansion(String mansion) {
        this.mansion.setText(mansion);
    }

    public String getStringMansion() {
        return mansion.getText();
    }
}
