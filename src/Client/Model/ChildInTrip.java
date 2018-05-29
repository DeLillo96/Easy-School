package Client.Model;

import Client.Controller.AbstractTableController;
import Client.ControllerManager;
import Client.Remote.RemoteManager;
import Shared.TernaryRelationService;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

public class ChildInTrip extends AbstractRowModel {
    private Text name = new Text();
    private Text surname = new Text();
    private Text fiscalCode = new Text();
    private Text birthDate = new Text();
    private CheckBox select = new CheckBox();
    private Button childInVehicle;

    private Trip trip;

    public ChildInTrip(AbstractTableController tableController) throws Exception {
        this(tableController, new JSONObject());
    }

    public ChildInTrip(AbstractTableController tableController, JSONObject data) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getChildrenService(), tableController, data);

        setName((String) data.get("name"));
        setSurname((String) data.get("surname"));
        setFiscalCode((String) data.get("fiscalCode"));
        setBirthDate((String) data.get("birthDate"));

        events();
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();

        childInVehicle = new Button();
        defineImageButton(childInVehicle, "Client/Resources/Images/bus.png");
        childInVehicle.setOnAction(actionEvent -> childInVehiclePopup());
        childInVehicle.setTooltip(new Tooltip("Show childInVehicle"));
        childInVehicle.setVisible(false);

        getButtons().getChildren().clear();
        getButtons().getChildren().add(childInVehicle);
    }

    private void childInVehiclePopup() {
        ControllerManager.getInstance().renderChildInVehicles(trip, this);
    }

    public void events() {
        select.selectedProperty().addListener( (o, oldCheck, newCheck) -> childInVehicle.setVisible(newCheck));
    }

    public int getId() {
        return Integer.parseInt((String) data.get("id"));
    }

    public Text getName() {
        return name;
    }

    public void setName(Text name) {
        this.name = name;
    }

    public void setName(String name) {
        if (name != null) this.name.setText(name);
    }

    public String getStringName() {
        return name.getText();
    }

    public Text getSurname() {
        return surname;
    }

    public void setSurname(Text surname) {
        this.surname = surname;
    }

    public void setSurname(String surname) {
        if (surname != null) this.surname.setText(surname);
    }

    public String getStringSurname() {
        return surname.getText();
    }

    public Text getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(Text fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode.setText(fiscalCode);
    }

    public String getStringFiscalCode() {
        return fiscalCode.getText();
    }

    public Text getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate.setText(birthDate);
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    @Override
    protected void refreshModel() { }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public void setRowStyleClass(String styleClass) {
        name.getStyleClass().add(styleClass);
        surname.getStyleClass().add(styleClass);
        fiscalCode.getStyleClass().add(styleClass);
    }

    public void removeRowStyleClass(String styleClass) {
        name.getStyleClass().remove(styleClass);
        surname.getStyleClass().remove(styleClass);
        fiscalCode.getStyleClass().remove(styleClass);
    }
}
