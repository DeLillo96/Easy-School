package Server.Remote;

import Server.Entity.EntityInterface;
import Server.Entity.Menu;
import Server.Repository.MenuRepository;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;

public class MenuServiceImplementation extends AbstractBaseService implements BaseService {
    public MenuServiceImplementation() throws RemoteException {
        super(new MenuRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Menu.class);
    }
}
