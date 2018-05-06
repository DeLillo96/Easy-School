package Shared;

import org.json.simple.JSONObject;
import java.rmi.Remote;

public interface AdultService extends BaseService {
    JSONObject readParentsByChild(String childFiscalCode) throws Exception;
    JSONObject setParentsFromJSON(JSONObject data_ids) throws Exception;
}