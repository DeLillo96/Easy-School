package Server.Remote;

import Server.Entity.Adult;
import Server.Entity.EntityInterface;
import Server.Repository.AdultRepository;
import Server.Result;
import Shared.AdultService;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class AdultServiceImplementation extends AbstractBaseService implements AdultService {
    public AdultServiceImplementation() throws RemoteException {
        super(new AdultRepository());
    }

    @Override
    public JSONObject readParentsByChild(String childFiscalCode) throws Exception {
        Result result = new Result();

        List response = (new AdultRepository()).getAdultByChildFiscalCode(childFiscalCode);
        if(response != null) {
            result.setData(response);
        } else {
            result.setSuccess(false);
            result.addMessage("adult category are invalid");
        }

        return result.toJson();
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Adult.class);
    }
}
