package Client.Remote.Adapter;

import Shared.BaseService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Base services adapter (C.R.U.D. operations)
 */
public class BaseServiceAdapter implements BaseService {
    protected String service;
    protected BufferedReader in;
    protected PrintWriter out;

    public BaseServiceAdapter(String service, BufferedReader in, PrintWriter out) {
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

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(in.readLine());
    }

    @Override
    public JSONObject save(JSONObject data) throws Exception {
        submitRequest("save", data);

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(in.readLine());
    }

    @Override
    public JSONObject saveAll(JSONObject data) throws Exception {
        return null;
    }

    @Override
    public JSONObject delete(JSONObject data) throws Exception {
        submitRequest("delete", data);

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(in.readLine());
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
    protected void submitRequest(String function, JSONObject data) {
        JSONObject request = new JSONObject();
        request.put("service", service);
        request.put("function", function);
        request.put("data", data);

        out.println(request.toString());
    }
}
