package Server.Remote;

import Server.Entity.Calendar;
import Server.Entity.EntityInterface;
import Server.Repository.CalendarRepository;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;

public class CalendarServiceImplementation extends AbstractBaseService implements BaseService {
    public CalendarServiceImplementation() throws RemoteException {
        super(new CalendarRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Calendar.class);
    }
}
