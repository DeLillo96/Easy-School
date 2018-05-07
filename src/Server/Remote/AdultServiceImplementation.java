package Server.Remote;

import Server.Entity.Adult;
import Server.Entity.Child;
import Server.Entity.EntityInterface;
import Server.Repository.AdultRepository;
import Server.Repository.ChildRepository;
import Server.Result;
import Shared.AdultService;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class AdultServiceImplementation extends AbstractBaseService implements AdultService {
    public AdultServiceImplementation() throws RemoteException {
        super(new AdultRepository());
    }

    @Override
    public JSONObject readParentsByChild(String childFiscalCode) throws Exception {
        Result result = new Result();

        List response = new ArrayList((new AdultRepository()).getAdultByChildFiscalCode(childFiscalCode));
        if(response != null) {
            result.setData(response);
        } else {
            result.setSuccess(false);
            result.addMessage("adult category are invalid");
        }

        return result.toJson();
    }

    @Override
    public JSONObject setParentFromJSON(JSONObject data) throws Exception {

        Result result = new Result();
        boolean flag = false;
        Child mainChild = (new ChildRepository()).getChildById((Integer) data.get("childId"));
        Adult adultToAdd = (new AdultRepository()).getAdultById((Integer) data.get("adultId"));
        boolean check = (boolean) data.get("check");
        Set<Adult> actualParents = (new AdultRepository()).getAdultByChildFiscalCode(mainChild.getFiscalCode());
        Set<Adult> adultsToRemove = new HashSet<>();
        for(Adult a:actualParents) {
            if(a.getFiscalCode().equals(adultToAdd.getFiscalCode())) {
                if(check) {
                    result.setSuccess(true);
                    result.addMessage("DB was already updated");
                    return result.toJson();
                }else {
                    flag = true;
                    adultsToRemove.add(a);
                }
            }
        }
        if(flag) {
            actualParents.removeAll(adultsToRemove);
            mainChild.setParents(actualParents);
            result = mainChild.save();
            return result.toJson();
        }else if(check)
        {
            actualParents.add(adultToAdd);
            mainChild.setParents(actualParents);
            result = mainChild.save();
            return result.toJson();
        }

        result.setSuccess(true);
        result.addMessage("DB was already updated");
        return result.toJson();
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Adult.class);
    }
}
