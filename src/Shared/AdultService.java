package Shared;

import org.json.simple.JSONObject;

public interface AdultService extends BaseService {
    JSONObject readParentsByChild(String childFiscalCode) throws Exception;

    JSONObject setParentFromJSON(JSONObject data) throws Exception;
}