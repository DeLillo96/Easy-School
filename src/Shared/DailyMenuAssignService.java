package Shared;

import org.json.simple.JSONObject;

import java.util.List;

public interface DailyMenuAssignService extends AssignService{
    JSONObject saveAll(Integer calendarId, List<Integer> selectedMenuId) throws Exception;
}
