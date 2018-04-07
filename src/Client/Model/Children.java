package Client.Model;

import Client.Controller.AbstractTableController;
import Client.Remote.RemoteManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;
import java.util.Date;

public class Children extends AbstractRowModel {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private TextField name = new TextField();
    private TextField surname = new TextField();
    private TextField fiscalCode = new TextField();
    private DatePicker birthDate = new DatePicker();

    public Children(AbstractTableController tableController) throws Exception {
        this(tableController, 0, "", "", "", new Date());
    }

    public Children(AbstractTableController tableController, Integer id, String name, String surname, String fiscalCode, Date birthDate) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getChildrenService(), tableController);

        setId(id);
        setName(name);
        setSurname(surname);
        setFiscalCode(fiscalCode);
        //todo make parse to Date into DatePicker
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();

        Button parents = new Button();
        defineImageButton(parents, "Client/Resources/Images/parents.png");
        parents.setOnAction(actionEvent -> parents());

        Button disorder = new Button();
        defineImageButton(disorder, "Client/Resources/Images/eating.png");
        disorder.setOnAction(actionEvent -> disorder());

        getButtons().getChildren().addAll(parents, disorder);
    }

    public void parents() {
        //todo open popup of parents
    }

    public void disorder() {
        //todo open popup of disorder
    }

    @Override
    protected JSONObject makeRequest() {
        JSONObject request = new JSONObject();

        if(getId() != 0) request.put("id", getId());
        request.put("name", getStringName());
        request.put("surname", getStringSurname());
        request.put("birthDate", "2018-04-04");
        request.put("fiscalCode", getStringFiscalCode());

        return request;
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public TextField getName() {
        return name;
    }

    public String getStringName() {
        return name.getText();
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public TextField getSurname() {
        return surname;
    }

    public String getStringSurname() {
        return surname.getText();
    }

    public void setSurname(TextField surname) {
        this.surname = surname;
    }

    public void setSurname(String surname) {
        this.surname.setText(surname);
    }

    public TextField getFiscalCode() {
        return fiscalCode;
    }

    public String getStringFiscalCode() {
        return fiscalCode.getText();
    }

    public void setFiscalCode(TextField fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode.setText(fiscalCode);
    }

    public DatePicker getBirthDate() {
        return birthDate;
    }

    public Date getDateOnDatePicker() {
        //return birthDate.getValue();
        return new Date();
    }

    public void setBirthDate(DatePicker birthDate) {
        this.birthDate = birthDate;
    }
}
