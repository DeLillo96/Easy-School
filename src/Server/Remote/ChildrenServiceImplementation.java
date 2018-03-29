package Server.Remote;

import Server.Entity.Child;
import Server.Entity.EntityInterface;
import Server.Repository.ChildRepository;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;

public class ChildrenServiceImplementation extends AbstractBaseService implements BaseService {
    public ChildrenServiceImplementation() throws RemoteException {
        super(new ChildRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Child.class);
    }
}
