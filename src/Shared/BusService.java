package Shared;

import org.json.simple.JSONObject;

public interface BusService extends BaseService {
    JSONObject readUsedBusesByDayTrip(Integer dayTripId) throws Exception;
    JSONObject setUsedBusesFromJSON(JSONObject data) throws Exception;
}