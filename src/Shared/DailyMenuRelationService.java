package Shared;

import org.json.simple.JSONObject;

import java.util.List;

/**
 * Interface containing Daily Menu relations services
 */
public interface DailyMenuRelationService extends RelationService {
    JSONObject saveAll(Integer calendarId, List<Integer> selectedMenuId) throws Exception;
}
