package Server.Remote;

import Server.Entity.Category;
import Server.Entity.EntityInterface;
import Server.Repository.CategoryRepository;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;

public class CategoryServiceImplementation extends AbstractBaseService implements BaseService {
    public CategoryServiceImplementation() throws RemoteException {
        super(new CategoryRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Category.class);
    }
}
