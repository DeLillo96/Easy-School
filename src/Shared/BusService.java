package Shared;

import org.json.simple.JSONObject;

public interface BusService extends BaseService {
    JSONObject readUsedBusesByDayTrip(Integer dayTripId) throws Exception;

    JSONObject readArrivalBusesByPlace(Integer dayTripId) throws Exception;

    JSONObject readStartBusesByPlace(Integer dayTripId) throws Exception;

    JSONObject setUsedBusesFromJSON(JSONObject data) throws Exception;

    JSONObject setArrivalBusesFromJSON(JSONObject data) throws Exception;

    JSONObject setStartBusesFromJSON(JSONObject data) throws Exception;
}