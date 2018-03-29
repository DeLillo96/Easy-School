package Client.Model;

import Client.Remote.RemoteManager;
import Shared.BaseService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Children {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private TextField name = new TextField();
    private TextField surname = new TextField();
    private TextField fiscalCode = new TextField();
    private DatePicker birthDate = new DatePicker();
    private static BaseService service;

    public Children() throws Exception {
        service = RemoteManager.getInstance().getRemoteServicesManager().getChildrenService();
    }

    public Children(Integer id, String name, String surname, String fiscalCode, Date birthDate) throws Exception {
        setId(id);
        setName(name);
        setSurname(surname);
        setFiscalCode(fiscalCode);
        //setBirthDate(birthDate); //todo make parse to Date into DatePicker
        service = RemoteManager.getInstance().getRemoteServicesManager().getChildrenService();
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

    public void setName(TextField name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name.setText(name);
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

    public TextField getFiscalCode() {
        return fiscalCode;
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

    public void setBirthDate(DatePicker birthDate) {
        this.birthDate = birthDate;
    }

    public ArrayList<Children> read() throws Exception {
        ArrayList<Children> list = new ArrayList<>();
        JSONObject response = service.readAll();

        if((boolean) response.get("success")) {
            JSONObject data = (JSONObject) response.get("data");

            for (int i = 0; i < data.size(); i++) {
                JSONObject child = (JSONObject) data.get(i);

                Integer id = Integer.parseInt((String) child.get("id"));
                String name = (String) child.get("name");
                String surname = (String) child.get("surname");
                String fiscalCode = (String) child.get("fiscalCode");

                list.add(new Children(id, name, surname, fiscalCode, new Date()));
            }
            return list;
        } else throw new Exception("Read from server error");
    }
}
