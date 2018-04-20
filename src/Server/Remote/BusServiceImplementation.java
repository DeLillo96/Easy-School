package Server.Remote;

import Server.Entity.Bus;
import Server.Entity.EntityInterface;
import Server.Repository.BusRepository;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;

public class BusServiceImplementation extends AbstractBaseService implements BaseService {
    public BusServiceImplementation() throws RemoteException {
        super(new BusRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Bus.class);
    }
}
