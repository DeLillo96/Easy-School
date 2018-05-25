package Server.Remote;

import Server.Entity.Trip;
import Server.Entity.EntityInterface;
import Server.Repository.TripRepository;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;

public class TripServiceImplementation extends AbstractBaseService implements BaseService {
    public TripServiceImplementation() throws RemoteException {
        super(new TripRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Trip.class);
    }
}
