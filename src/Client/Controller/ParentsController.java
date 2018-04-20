package Client.Controller;

import Client.Model.Children;
import Client.Remote.RemoteManager;
import Server.Entity.Child;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import org.json.simple.JSONObject;
import java.util.ArrayList;

public class ParentsController extends AbstractTableController {

    public ArrayList<Children> child;
    //public ArrayList<Parents> list;
    private int id;

    /*public ParentsController(int childID) throws Exception {
        super( RemoteManager.getInstance().getRemoteServicesManager().getChildrenService() );
        this.id=childID;
        filter();
    }

    @Override
    public void filter() {
        try {
            child = search();
            System.out.println("DID IT");
        } catch (Exception e ) {
            //todo render error
        }
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("id", id);

        return filters;
    }*/

    public ParentsController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getAdultService());
    }

    @Override
    protected JSONObject takeFilters() { return null; }

    @Override
    public void filter() {}

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        return null;
    }
}