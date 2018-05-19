package Server.Remote;

import Server.Entity.EntityInterface;
import Server.Entity.Staff;
import Server.Repository.StaffRepository;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;

public class StaffServiceImplementation extends AbstractBaseService implements BaseService {

    public StaffServiceImplementation() throws RemoteException {
        super(new StaffRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Staff.class);
    }
}
