package Client.Remote.Adapter;

import Shared.BaseService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

/**
 * Base services adapter (C.R.U.D. operations)
 */
public class BaseServiceAdapter implements BaseService {
    protected String service;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    public BaseServiceAdapter(String service, ObjectInputStream in, ObjectOutputStream out) {
        this.service = service;
        this.in = in;
        this.out = out;
    }

    @Override
    public JSONObject readAll() throws Exception {
        return read(null);
    }

    @Override
    public JSONObject read(JSONObject parameters) throws Exception {
        submitRequest("read", parameters);

        return (JSONObject) in.readObject();
    }

    @Override
    public JSONObject save(JSONObject data) throws Exception {
        submitRequest("save", data);

        return (JSONObject) in.readObject();
    }

    @Override
    public JSONObject saveAll(JSONObject data) throws Exception {
        return null;
    }

    @Override
    public JSONObject delete(JSONObject data) throws Exception {
        submitRequest("delete", data);

        return (JSONObject) in.readObject();
    }

    @Override
    public JSONObject deleteAll(JSONObject data) throws Exception {
        return null;
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
