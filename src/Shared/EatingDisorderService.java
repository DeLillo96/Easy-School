package Shared;

import org.json.simple.JSONObject;
import java.rmi.Remote;

public interface EatingDisorderService extends BaseService {
    JSONObject readDisorderByAffectedChild(String childFiscalCode) throws Exception;
    JSONObject setDisorderFromJSON(JSONObject data_ids) throws Exception;
}