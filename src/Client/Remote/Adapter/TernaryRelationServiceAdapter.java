package Client.Remote.Adapter;

import Shared.TernaryRelationService;
import org.json.simple.JSONObject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class TernaryRelationServiceAdapter extends RelationServiceAdapter implements TernaryRelationService{

    public TernaryRelationServiceAdapter(String service, ObjectInputStream in, ObjectOutputStream out) {
        super(service, in, out);
    }

    @Override
    public JSONObject assign(Integer rightId, Integer center, List<Integer> leftId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("rightId", rightId);
        data.put("center", center);
        data.put("leftId", leftId);

        submitRequest("assign", data);

        return (JSONObject) in.readObject();
    }

    @Override
    public JSONObject deAssign(Integer rightId, Integer center, Integer leftId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("rightId", rightId);
        data.put("center", center);
        data.put("leftId", leftId);

        submitRequest("deassign", data);

        return (JSONObject) in.readObject();
    }

    @Override
    public JSONObject rightRead(Integer rightId, Integer centerId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("rightId", rightId);
        data.put("centerId", centerId);

        submitRequest("rightread", data);

        return (JSONObject) in.readObject();
    }

    @Override
    public JSONObject leftRead(Integer leftId, Integer centerId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("leftId", leftId);
        data.put("centerId", centerId);

        submitRequest("leftread", data);

        return (JSONObject) in.readObject();
    }

    @Override
    public JSONObject centerRead(Integer leftId, Integer rightId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("leftId", leftId);
        data.put("rightId", rightId);

        submitRequest("centerread", data);

        return (JSONObject) in.readObject();
    }
}
