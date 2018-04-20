package Server.Remote;

import Server.Entity.Aliment;
import Server.Entity.EntityInterface;
import Server.Repository.AlimentRepository;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;

public class AlimentServiceImplementation extends AbstractBaseService implements BaseService {
    public AlimentServiceImplementation() throws RemoteException {
        super(new AlimentRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Aliment.class);
    }
}
