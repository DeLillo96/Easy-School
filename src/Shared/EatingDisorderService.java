package Shared;

import org.json.simple.JSONObject;

public interface EatingDisorderService extends AssignService {
    JSONObject assign(Integer rightId, Integer leftId, String type) throws Exception;
}