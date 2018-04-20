package Client.Model;

import Client.Controller.AbstractTableController;
import Client.Remote.RemoteManager;
import org.json.simple.JSONObject;

public class EatingDisorder extends AbstractRowModel {
    private int id;
    private int child_id;
    private int aliment_id;
    private String type;

    public EatingDisorder(AbstractTableController tableController) throws Exception {
        this(tableController, 0, 0,0, "");
    }

    public EatingDisorder(AbstractTableController tableController, Integer id, Integer child_id, Integer aliment_id, String type) throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getEatingDisorderService(), tableController);

        setId(id);
        setChild_id(child_id);
        setAliment_id(aliment_id);
        setType(type);
    }

    @Override
    protected JSONObject makeRequest() {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public int getAliment_id() {
        return aliment_id;
    }

    public void setAliment_id(int aliment_id) {
        this.aliment_id = aliment_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
