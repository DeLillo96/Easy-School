package Shared;

import org.json.simple.JSONObject;

public interface PlaceService extends BaseService {
    JSONObject readVisitedPlaceByTripId(Integer dayTripId) throws Exception;

    JSONObject setVisitedPlaceFromJSON(JSONObject data) throws Exception;
}