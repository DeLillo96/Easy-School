package Shared;

import org.json.simple.JSONObject;

public interface EatingDisorderService extends AssignService {
    JSONObject assignAllergy(Integer rightId, Integer leftId) throws Exception;

    JSONObject assignIntolerance(Integer rightId, Integer leftId) throws Exception;
}