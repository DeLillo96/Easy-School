package Server.Remote;

import Server.Entity.Adult;
import Server.Entity.EntityInterface;
import Server.Entity.Pediatrician;
import Server.Entity.Supplier;
import Server.Repository.AdultRepository;
import Server.Repository.PediatricianRepository;
import Server.Repository.SupplierRepository;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;

public class PediatricianServiceImplementation extends AbstractBaseService implements BaseService {
    public PediatricianServiceImplementation() throws RemoteException {
        super(new PediatricianRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Pediatrician.class);
    }
}
