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
        super(RemoteManager.getInstance().getRemoteServicesManager().getAlimentService());
    }

    public void setChild(Children child) {
        this.child = child;
        childName.setText(child.getStringName());
        childSurname.setText(child.getStringSurname());
        childFiscalCode.setText(child.getStringFiscalCode());
    }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<EatingDisorder> list = search(takeFilters());

            RelationService eatingDisorderService = RemoteManager.getInstance().getRemoteServicesManager().getEatingDisorderService();
            JSONObject result = eatingDisorderService.rightRead(child.getId());

            ArrayList<EatingDisorder> eatingDisorders = parseEatingDisorderIntoRows((JSONObject) result.get("data"));
            for (EatingDisorder eatingDisorder : list) {
                eatingDisorders.forEach(o -> {
                    if (o.getId().equals(eatingDisorder.getId())) {
                        eatingDisorder.getType().setValue(o.getType().getValue());
                    }
                });
            }

            ObservableList<AbstractRowModel> items = FXCollections.observableArrayList(list);
            tableView.setItems(items);
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("name", nameTextField.getText());

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        return null;
    }

    protected ArrayList parseEatingDisorderIntoRows(JSONObject data) throws Exception {
        ArrayList<EatingDisorder> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject eatingDisorder = (JSONObject) data.get(i);
            JSONObject aliment = (JSONObject) eatingDisorder.get("affectedAliment");

            EatingDisorder element = new EatingDisorder(this, aliment);
            element.setChild(child);
            element.getType().setValue((String) eatingDisorder.get("type"));
            list.add(element);
        }
        return list;
    }

    public void remove() {
        ControllerManager.getInstance().removePopup();
    }

}
