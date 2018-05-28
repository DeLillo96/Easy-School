package Client.Remote.Adapter;

import Shared.RelationService;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class RelationServiceAdapter implements RelationService {
    protected String service;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    public RelationServiceAdapter(String service, ObjectInputStream in, ObjectOutputStream out) {
        this.service = service;
        this.in = in;
        this.out = out;
    }

    @Override
    public JSONObject assign(Integer rightId, Integer leftId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("rightId", rightId);
        data.put("leftId", leftId);

        submitRequest("assign", data);

        return (JSONObject) in.readObject();
    }

    @Override
    public JSONObject assign(Integer rightId, List<Integer> leftIds) throws Exception {
        JSONObject data = new JSONObject();
        data.put("rightId", rightId);
        data.put("leftId", leftIds);

        submitRequest("assign", data);

        return (JSONObject) in.readObject();
    }

    @Override
    public JSONObject assign(List<Integer> rightIds, Integer leftId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("rightId", rightIds);
        data.put("leftId", leftId);

        submitRequest("assign", data);

        return (JSONObject) in.readObject();
    }

    @Override
    public JSONObject deAssign(Integer rightId, Integer leftId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("rightId", rightId);
        data.put("leftId", leftId);

        submitRequest("deassign", data);

        return (JSONObject) in.readObject();
    }

    @Override
    public JSONObject rightRead(Integer rightId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("rightId", rightId);

        submitRequest("rightread", data);

        return (JSONObject) in.readObject();
    }

    @Override
    public JSONObject leftRead(Integer leftId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("leftId", leftId);

        submitRequest("leftread", data);

        return (JSONObject) in.readObject();
    }

    @Override
    public Integer rightCount(Integer rightId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("rightId", rightId);

        submitRequest("rightcount", data);

        return (Integer) in.readObject();
    }

    @Override
    public Integer leftCount(Integer leftId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("leftId", leftId);

        submitRequest("leftcount", data);

        return (Integer) in.readObject();
    }

    /**
     * Method which sends a new request through output PrintWriter
     * The request is a JSONObject containing the type of required service and function and the data used for the
     * operation's accomplishment
     * @param function (Required function)
     * @param data (Data used by service and function)
     */
    protected void submitRequest(String function, JSONObject data) throws IOException {
        JSONObject request = new JSONObject();
        request.put("service", service);
        request.put("function", function);
        request.put("data", data);

        out.writeObject(request);
        out.flush();
    }
}
