package Shared;

import org.json.simple.JSONObject;

/**
 * Interface containing Eating Disorders services
 */
public interface EatingDisorderService extends RelationService {

    /**
     * Prepares allergy assignation operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject assignAllergy(Integer rightId, Integer leftId) throws Exception;

    /**
     * Prepares intolerance assignation operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject assignIntolerance(Integer rightId, Integer leftId) throws Exception;
}