package Client.Controller;

import Shared.BaseService;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class DishController extends AbstractTableController {
    public DishController(BaseService baseService) {
        super(baseService);
    }

    @Override
    protected JSONObject takeFilters() {
        return null;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        return null;
    }

    @Override
    public void filter() {

    }
}
