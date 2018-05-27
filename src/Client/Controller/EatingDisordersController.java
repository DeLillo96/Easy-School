package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Client.Model.Children;
import Client.Model.EatingDisorder;
import Client.Remote.RemoteManager;
import Shared.RelationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class EatingDisordersController extends AbstractTableController {
    /* FILTERS */
    @FXML
    private Text childName;
    @FXML
    private Text childSurname;
    @FXML
    private Text childFiscalCode;
    @FXML
    private TextField nameTextField;

    private Children child;

    public EatingDisordersController() throws Exception {
        super(null);
    }

    public void setChild(Children child) {
        this.child = child;
        childName.setText(child.getStringName());
        childSurname.setText(child.getStringSurname());
        childFiscalCode.setText(child.getStringFiscalCode());
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("name", nameTextField.getText());

        return filters;
    }

    @Override
    protected ArrayList search(JSONObject filters) throws Exception {
        RelationService service = RemoteManager.getInstance().getRemoteServicesManager().getEatingDisorderService();
        JSONObject response = service.rightRead(child.getId());

        if ((boolean) response.get("success")) {

            JSONObject data = (JSONObject) response.get("data");
            return parseIntoRows(data);

        } else throw new Exception("Read from server error");
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<EatingDisorder> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject items = (JSONObject) data.get(i);

            EatingDisorder element = new EatingDisorder(this, (JSONObject) items.get(0));
            element.setChild(child);
            if(items.get(1) != null) element.getType().setValue((String) items.get(1));
            element.refreshModel();
            list.add(element);
        }
        return list;
    }

    public void remove() {
        ControllerManager.getInstance().removePopup();
    }

}
