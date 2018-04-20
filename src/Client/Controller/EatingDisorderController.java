package Client.Controller;

import Client.Model.Children;
import Client.Model.EatingDisorder;
import Client.Remote.RemoteManager;
import Server.Entity.Aliment;
import Server.Entity.Child;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.util.ArrayList;

public class EatingDisorderController extends AbstractTableController {

    public ArrayList<EatingDisorder> affectedAliments;
    //public ArrayList<Parents> list;
    private int child_id;

    public EatingDisorderController(int childID) throws Exception {
        super( RemoteManager.getInstance().getRemoteServicesManager().getEatingDisorderService() );
        this.child_id=childID;
        filter();
    }

    @Override
    public void filter() {
        try {
            affectedAliments = search();
            System.out.println("DID IT");
        } catch (Exception e ) {
            //todo render error
        }
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<EatingDisorder> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject eatingDisorder = (JSONObject) data.get(i);

            Integer id = Integer.parseInt((String) eatingDisorder.get("id"));
            ObjectMapper objectMapper = new ObjectMapper();
            String temp = (objectMapper.writeValueAsString((Child) eatingDisorder.get("affectedChild")));
            System.out.println(temp);
            //Aliment affectedAliment = (Aliment) eatingDisorder.get("affectedAliment");
            //String type = (String) eatingDisorder.get("fiscalCode");

            //list.add(new EatingDisorder(this, id, affectedChild.getId(), affectedAliment.getId(),type));
        }

        //return list;
        return null;
    }
}