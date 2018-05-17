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
    private TextField telephone = new TextField();

    public Staff(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public Staff(AbstractTableController tableController, JSONObject data) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getAdultService(), tableController, data);

        setName((String) data.get("name"));
        setSurname((String) data.get("surname"));
        setFiscalCode((String) data.get("fiscalCode"));
        setBirthDate((CharSequence) data.get("birthDate"));

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
        telephone.textProperty().addListener((obs, oldText, newText) -> {
            needToSave();
            data.put("telephone", newText);
        });
        birthDate.setOnAction(event -> {
            needToSave();
            data.put("birthDate", birthDate.getValue().toString());
        });
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
}
