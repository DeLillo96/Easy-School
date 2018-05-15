package Server.Remote;

import Server.Entity.Bus;
import Server.Entity.EntityInterface;
import Server.Repository.BusRepository;
import Server.Result;
import Shared.BaseService;
import Shared.BusService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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
