package Server.Repository;

import Server.Entity.EntityInterface;

import java.util.HashMap;
import java.util.List;

public interface Repository {
    void save(List<EntityInterface> list);
    List read();
    List read(String filterName, HashMap<String, Object> param);
    List read(HashMap<String, HashMap<String, Object>> filters);
}
