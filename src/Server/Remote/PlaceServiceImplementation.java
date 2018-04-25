package Server.Remote;

import Server.Entity.EntityInterface;
import Server.Entity.Place;
import Server.Repository.PlaceRepository;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;

public class PlaceServiceImplementation extends AbstractBaseService implements BaseService {
    public PlaceServiceImplementation() throws RemoteException {
        super(new PlaceRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Place.class);
    }
}
