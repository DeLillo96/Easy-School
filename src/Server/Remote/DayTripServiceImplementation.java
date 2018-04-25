package Server.Remote;

import Server.Entity.DayTrip;
import Server.Entity.EntityInterface;
import Server.Repository.DayTripRepository;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;

public class DayTripServiceImplementation extends AbstractBaseService implements BaseService {
    public DayTripServiceImplementation() throws RemoteException {
        super(new DayTripRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), DayTrip.class);
    }
}
