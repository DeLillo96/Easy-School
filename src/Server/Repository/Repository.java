package Server.Repository;

import Server.Entity.EntityInterface;
import Server.Result;

import java.util.HashMap;
import java.util.List;

/**
 * Each repository is linked to a specific DB table, implements read operations and instantiate save operations
 */
public interface Repository {
    Result save(List<EntityInterface> list);

    List read();

    List read(HashMap<String, Object> filters);

    List read(String sql, HashMap<String, Object> filters);
}
