package Server.Remote;

import Server.Entity.EntityInterface;
import Server.Entity.Place;
import Server.Repository.PlaceRepository;
import Server.Result;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class PlaceServiceImplementation extends AbstractBaseService implements BaseService {
    public PlaceServiceImplementation() throws RemoteException {
        super(new PlaceRepository());
    }

    public JSONObject readVisitedPlaceByTripId(Integer dayTripId) throws Exception {
        Result result = new Result();

        List response = new ArrayList((new PlaceRepository()).getPlaceByTrip(dayTripId));
        if (response != null) {
            result.setData(response);
        } else {
            result.setSuccess(false);
            result.addMessage("place category are invalid");
        }

        return result.toJson();
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Place.class);
    }
}
