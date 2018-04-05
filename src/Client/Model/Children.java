package Client.Model;

import Client.ControllerManager;
import Client.Remote.RemoteManager;
import Shared.BaseService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Date;

public class Children {
    private Button save = new Button();
    private Button parents = new Button();
    private Button disorder = new Button();
    private Button delete = new Button();
    private HBox buttons = new HBox(save, parents, disorder, delete);

    private final SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private TextField name = new TextField();
    private TextField surname = new TextField();
    private TextField fiscalCode = new TextField();
    private DatePicker birthDate = new DatePicker();

    private static BaseService service;

    public Children() throws Exception {
        this(0, "", "", "", new Date());
    }

    public Children(Integer id, String name, String surname, String fiscalCode, Date birthDate) throws Exception {
        setId(id);
        setName(name);
        setSurname(surname);
        setFiscalCode(fiscalCode);
        //setBirthDate(birthDate); //todo make parse to Date into DatePicker

        defineImageButton(save, "Client/Resources/Images/save.png");
        save.setOnAction(this::save);
        defineImageButton(parents, "Client/Resources/Images/parents.png");
        parents.setOnAction(this::parents);
        defineImageButton(disorder, "Client/Resources/Images/eating.png");
        disorder.setOnAction(this::disorder);
        defineImageButton(delete, "Client/Resources/Images/delete.png");
        delete.setOnAction(this::delete);
        buttons.setAlignment(Pos.CENTER);

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

    public HBox getButtons() {
        return buttons;
    }

    public void setButtons(HBox buttons) {
        this.buttons = buttons;
    }

    public void defineImageButton(Button button, String urlImage) {
        ObservableList<String> classes = button.getStyleClass();
        classes.add("row-button");
        classes.add("radius-15");
        ImageView imageView = new ImageView( urlImage );

        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        button.setGraphic(imageView);
    }

    private void save(ActionEvent actionEvent) {
        JSONObject request = new JSONObject();

        if(getId() != 0) request.put("id", getId());
        request.put("name", getStringName());
        request.put("surname", getStringSurname());
        request.put("birthDate", "2018-04-04");
        request.put("fiscalCode", getStringFiscalCode());

        try {
            JSONObject response = service.save(request);
            if(!(boolean) response.get("success")) {
                String errorMessage = response.get("messages").toString();
                throw new Exception(errorMessage);
            }
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError("500 Server Error");
        }
    }

    private void parents(ActionEvent actionEvent) {
        //todo open popup of parents
    }

    private void disorder(ActionEvent actionEvent) {
        //todo open popup of disorder
    }

    private void delete(ActionEvent actionEvent) {
        try {
            if(getId() != 0) {
                JSONObject request = new JSONObject();

                request.put("id", getId());
                request.put("name", getStringName());
                request.put("surname", getStringSurname());
                request.put("birthDate", "2018-04-04");
                request.put("fiscalCode", getStringFiscalCode());

                JSONObject response = service.delete(request);
                if(!(boolean) response.get("success")) {
                    String errorMessage = response.get("messages").toString();
                    throw new Exception(errorMessage);
                }
            }
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError("500 Server Error");
        }
        //todo refresh table
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
