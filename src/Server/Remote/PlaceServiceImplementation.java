package Server.Remote;

import Server.Entity.DayTrip;
import Server.Entity.EntityInterface;
import Server.Entity.Place;
import Server.Repository.DayTripRepository;
import Server.Repository.PlaceRepository;
import Server.Result;
import Shared.BaseService;
import Shared.PlaceService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlaceServiceImplementation extends AbstractBaseService implements PlaceService {
    public PlaceServiceImplementation() throws RemoteException {
        super(new PlaceRepository());
    }

    @Override
    public JSONObject readVisitedPlaceByTripId(Integer dayTripId) throws Exception {
        Result result = new Result();

        List response = new ArrayList((new PlaceRepository()).getPlaceByDayTrip(dayTripId));
        if(response != null) {
            result.setData(response);
        } else {
            result.setSuccess(false);
            result.addMessage("place category are invalid");
        }

        return result.toJson();
    }

    @Override
    public JSONObject setVisitedPlaceFromJSON(JSONObject data) throws Exception {

        Result result = new Result();
        boolean flag = false;
        DayTrip mainTrip = (new DayTripRepository()).getDayTripById((Integer) data.get("tripId"));
        Place placeToAdd = (new PlaceRepository()).getPlaceById((Integer) data.get("placeId"));
        boolean check = (boolean) data.get("check");
        Set<Place> actualPlaces = (new PlaceRepository()).getPlaceByDayTrip(mainTrip.getId());
        Set<Place> placesToRemove = new HashSet<>();
        for(Place a:actualPlaces) {
            if(a.getId()==(placeToAdd.getId())) {
                if(check) {
                    result.setSuccess(true);
                    result.addMessage("DB was already updated");
                    return result.toJson();
                }else {
                    flag = true;
                    placesToRemove.add(a);
                }
            }
        }
        if(flag) {
            actualPlaces.removeAll(placesToRemove);
            mainTrip.setPlaces(actualPlaces);
            result = mainTrip.save();
            return result.toJson();
        }else if(check)
        {
            actualPlaces.add(placeToAdd);
            mainTrip.setPlaces(actualPlaces);
            result = mainTrip.save();
            return result.toJson();
        }

        result.setSuccess(true);
        result.addMessage("DB was already updated");
        return result.toJson();
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Place.class);
    }
}
