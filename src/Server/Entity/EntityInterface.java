package Server.Entity;

import Server.Result;

/**
 * Interface which defines a single specific DB table's tuple and implements save and delete operations on that tuple
 */
public interface EntityInterface {
    Result save();

    Result delete();
}
