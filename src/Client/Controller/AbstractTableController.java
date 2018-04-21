package Client.Controller;

import Shared.BaseService;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public abstract class AbstractTableController {

    protected BaseService service;

    public AbstractTableController(BaseService baseService) {
        service = baseService;
    }

    protected ArrayList search() throws Exception {
        JSONObject response = service.read( takeFilters() );

        if((boolean) response.get("success")) {

            JSONObject data = (JSONObject) response.get("data");
            return parseIntoRows(data);

        } else throw new Exception("Read from server error");
    }

    protected abstract JSONObject takeFilters();
    protected abstract ArrayList parseIntoRows(JSONObject data) throws Exception;
    public abstract void filter();
}
