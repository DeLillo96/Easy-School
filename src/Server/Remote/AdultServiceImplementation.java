package Server.Remote;

import Server.Entity.Adult;
import Server.Entity.EntityInterface;
import Server.Repository.AdultRepository;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;

public class AdultServiceImplementation extends AbstractBaseService implements BaseService {
    public AdultServiceImplementation() throws RemoteException {
        super(new AdultRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Adult.class);
    }
}
