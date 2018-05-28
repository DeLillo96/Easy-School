package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Client.Model.Aliment;
import Client.Model.Suppliers;
import Client.Remote.RemoteManager;
import Shared.RelationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class SuppliersController extends AbstractTableController {
    /* FILTERS */
    @FXML
    private Text alimentName;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField ivaTextField;

    private Aliment aliment;

    public SuppliersController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getSupplierService());
    }

    /**
     * Set an Aliment element to the controller, used for assignations
     * @param aliment
     */
    public void setAliment(Aliment aliment) {
        this.aliment = aliment;
        alimentName.setText(aliment.getStringName());
    }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<Suppliers> list = search(takeFilters());

            RelationService supplyService = RemoteManager.getInstance().getRemoteServicesManager().getSupplyingService();
            JSONObject result = supplyService.rightRead(aliment.getId());

            ArrayList<Suppliers> supply = parseIntoRows((JSONObject) result.get("data"));
            for (Suppliers s : list) {
                supply.forEach(o -> {
                    if (o.getId().equals(s.getId())) {
                        s.getSelect().setSelected(true);
                    }
                });
            }

            ObservableList<AbstractRowModel> items = FXCollections.observableArrayList(list);
            tableView.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    /**
     * Generates a new Supplier model in order to add it to the tableView as a new row
     * @throws Exception
     */
    @FXML
    public void add() throws Exception {
        Suppliers supplier = new Suppliers(this);
        supplier.setAliment(aliment);
        addIntoTable(supplier);
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("name", nameTextField.getText());
        filters.put("iva", ivaTextField.getText());

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Suppliers> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject supplier = (JSONObject) data.get(i);

            Suppliers item = new Suppliers(this, supplier);
            item.setAliment(aliment);
            list.add(item);
        }
        return list;
    }

    /**
     * Removes the current popup
     */
    public void remove() {
        ControllerManager.getInstance().removePopup();
    }
}
