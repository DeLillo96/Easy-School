package Shared;

import org.json.simple.JSONObject;

import java.rmi.Remote;

public interface PlaceToPlaceService extends Remote {
    JSONObject getBusesFromToPlaces(Integer fromId, Integer toId) throws Exception;
}
