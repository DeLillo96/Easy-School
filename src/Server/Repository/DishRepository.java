package Server.Repository;

import Server.Entity.Aliment;
import Server.Entity.Dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DishRepository extends AbstractRepository {

    public DishRepository() {
        super("Dish");
    }

    public Dish getDishById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List dishes = read(params);

        return dishes != null && dishes.size() == 1 ? (Dish) dishes.get(0) : null;
    }

    public Dish getDishByName(String name) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        List dishes = read(params);

        return dishes != null && dishes.size() == 1 ? (Dish) dishes.get(0) : null;
    }

    public List getFirst(HashMap<String, Object> params) {
        setTableName("First");
        List result = read(params);
        setTableName("Dish");
        return result;
    }

    public List getSecond(HashMap<String, Object> params) {
        setTableName("Second");
        List result = read(params);
        setTableName("Dish");
        return result;
    }

    public List getSide(HashMap<String, Object> params) {
        setTableName("Side");
        List result = read(params);
        setTableName("Dish");
        return result;
    }

    public List getSweet(HashMap<String, Object> params) {
        setTableName("Sweet");
        List result = read(params);
        setTableName("Dish");
        return result;
    }

    public List getDishByAliment(Integer alimentId) {
        AlimentRepository alimentRepository = new AlimentRepository();
        Aliment aliment = alimentRepository.getAlimentById(alimentId);

        return aliment.getDishes() != null ? new ArrayList(aliment.getDishes()) : new ArrayList();
    }

    public List getAllDailyDish(Integer calendarId) {
        return read("select\n" +
                "  D,\n" +
                "  case when\n" +
                "    D.id in (" +
                "       select F.id from " +
                "           First F join F.calendars D " +
                "               where D.id = " + calendarId + ")\n" +
                "    or D.id in (" +
                "       select F.id from " +
                "           Second F join F.calendars D " +
                "               where D.id = " + calendarId + ")\n" +
                "    or D.id in (" +
                "       select F.id from " +
                "           Side F join F.calendars D " +
                "               where D.id = " + calendarId + ")\n" +
                "    or D.id in (" +
                "       select F.id from " +
                "           Sweet F join F.calendars D " +
                "               where D.id = " + calendarId + ")\n" +
                "       then true\n" +
                "  else false\n" +
                "    end,\n" +
                "  case when\n" +
                "    D.id in (" +
                "       select F.id from First F)" +
                "    then 'first'\n" +
                "  when\n" +
                "    D.id in (" +
                "       select F.id from Second F)" +
                "    then 'second'\n" +
                "  when\n" +
                "     D.id in (" +
                "       select F.id from Side F)" +
                "    then 'side'\n" +
                "  when\n" +
                "    D.id in (" +
                "       select F.id from Sweet F) " +
                "    then 'sweet'\n" +
                "  else null\n" +
                "    end\n" +
                "from Dish D ", null);
    }

    public List getListOfEatingDisorderByDay(Integer calendarId) {
        return read("select\n" +
                "   A.name," +
                "   count(distinct ED.affectedChild)\n" +
                "from Calendar C" +
                "   join C.dishes D" +
                "   join D.ingredients A" +
                "   join A.eatingDisorders ED\n" +
                "where C.id = " + calendarId +
                "group by A.name", null);
    }
}