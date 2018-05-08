package Client.Controller;

import Client.ControllerManager;
import Client.Model.Adults;
import Client.Model.Children;
import Client.Model.EatingDisorder;
import Client.Remote.RemoteManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EatingDisordersController extends AbstractTableController {
    /* FILTERS */
    @FXML private Text childName;
    @FXML private Text childSurname;
    @FXML private Text childFiscalCode;
    @FXML private TextField nameTextField;

    @FXML private TableView<EatingDisorder> eatingDisorderTableView;

    private Children child;

    public EatingDisordersController() throws Exception {
        super( RemoteManager.getInstance().getRemoteServicesManager().getAlimentService() );
    }

    public void setChild(Children child) {
        this.child = child;
        childName.setText(child.getStringName());
        childSurname.setText(child.getStringSurname());
        childFiscalCode.setText(child.getStringFiscalCode());
        filter();
    }

    @FXML
    public void initialize() { }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<EatingDisorder> aliments = search();
            JSONObject disorders = RemoteManager.getInstance().getRemoteServicesManager().getEatingDisorderService().readDisorderByAffectedChild(child.getStringFiscalCode());
            int maxLength = (int) disorders.get("maxLength");
            int[] affectedAliments = new int[maxLength];
            String[] disorderType = new String[maxLength];
            if(maxLength>0)
            {
                for(int count=0; count<maxLength; count++) {
                    affectedAliments[count] = (int) disorders.get("aliment"+(count+1));
                    disorderType[count] = (String) disorders.get("disorder"+(count+1));
                }
                for (EatingDisorder o:aliments) {
                    for(int count=0; count<maxLength; count++) {
                        if(o.getId()==affectedAliments[count]) {
                            o.getType().setValue(disorderType[count]);
                        }
                    }
                }
            }

            ObservableList<EatingDisorder> alimentList = FXCollections.observableArrayList(aliments);
            eatingDisorderTableView.setItems(alimentList);

        } catch (Exception e ) {
            //todo render error
        }
    }

    @FXML
    public void add() throws Exception {
        eatingDisorderTableView.getItems().add(new EatingDisorder(this, child.getId()));
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("name", nameTextField.getText());

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<EatingDisorder> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject eatingDisorder = (JSONObject) data.get(i);

            Integer id = Integer.parseInt((String) eatingDisorder.get("id"));
            String name = (String) eatingDisorder.get("name");

            list.add(new EatingDisorder(this, id, name, child.getId()));
        }
        return list;
    }

    protected JSONObject makeRequest(List<EatingDisorder> saveDisorders) {
        JSONObject disordersJson = new JSONObject();
        int count = 0;
        disordersJson.put("0", child.getId());
        for (EatingDisorder e:saveDisorders) {
            disordersJson.put("aliment"+(count+1), e.getId());
            count++;
        }
        disordersJson.put("max_length", saveDisorders.size());
        return disordersJson;
    }

    public void remove() {
        ControllerManager.getInstance().removePopup();
    }
}
