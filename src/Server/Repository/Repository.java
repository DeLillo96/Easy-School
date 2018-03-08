package Server.Repository;

import Server.Entity.EntityInterface;

import java.util.HashMap;
import java.util.List;

public interface Repository {
    void save(List<EntityInterface> list);
    List readAll();
    List read(HashMap<String, Object> param);
}
