package Server.Controller;

import Server.Entity.EntityInterface;
import Server.Repository.Repository;
import Server.Result;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public abstract class AbstractController extends UnicastRemoteObject implements Controller {
    protected Repository repository;

    public AbstractController(Repository repository) throws RemoteException {
        this.repository = repository;
    }

    @Override
    public JSONObject readAll() {
        List list = repository.read();
        return (new Result(true, list)).toJson();
    }

    @Override
    public JSONObject read(JSONObject parameters) {
        HashMap<String, Object> params;
        try {
            params = prepareReadParameter(parameters);
        } catch (IOException e) {
            return (new Result(e.getMessage(), false)).toJson();
        }
        List list = repository.read(params);
        return (new Result(true, list)).toJson();
    }

    @Override
    public JSONObject save(JSONObject data) {
        EntityInterface entity;
        try {
            entity = castJsonIntoEntity(data);
        } catch (IOException e) {
            Result result = new Result(e.getMessage(), false);
            return result.toJson();
        }
        Result result = entity.save();
        return result.toJson();
    }

    @Override
    public JSONObject saveAll(JSONObject jsonData) {
        ArrayList<EntityInterface> data = new ArrayList<>();
        Set keys = jsonData.keySet();
        EntityInterface entity;

        for (Object key : keys) {
            try {
                entity = castJsonIntoEntity((JSONObject) jsonData.get(key));
                data.add(entity);
            } catch (IOException e) {
                Result result = new Result(e.getMessage(), false);
                return result.toJson();
            }
        }

        Result result = repository.save(data);
        return result.toJson();
    }

    @Override
    public JSONObject delete(JSONObject data) {
        return null;
    }

    @Override
    public JSONObject deleteAll(JSONObject data) {
        return null;
    }

    protected HashMap<String, Object> prepareReadParameter(JSONObject parameters) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(String.valueOf(parameters), new TypeReference<Map<String, Object>>() {});
    }

    protected abstract EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException;
}
