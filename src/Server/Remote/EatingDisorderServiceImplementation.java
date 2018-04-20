package Server.Remote;

import Server.Entity.EatingDisorder;
import Server.Entity.EntityInterface;
import Server.Repository.EatingDisorderRepository;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;

public class EatingDisorderServiceImplementation extends AbstractBaseService implements BaseService {
    public EatingDisorderServiceImplementation() throws RemoteException {
        super(new EatingDisorderRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), EatingDisorder.class);
    }
}
