package Server.Repository;

import Server.Entity.Adult;
import Server.Entity.Child;
import Server.Entity.Trip;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChildRepository extends AbstractRepository {
    public ChildRepository() {
        super("Child");
    }

    public Child getChildById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List childs = read(params);

        return childs != null && childs.size() == 1 ? (Child) childs.get(0) : null;
    }

    public List getChildByBirthDate(Date birthDate) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("birthDate", birthDate);
        return read(params);
    }

    public Child getChildByFiscalCode(String fiscalCode) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("fiscalCode", fiscalCode);
        List childs = read(params);

        return childs != null && childs.size() == 1 ? (Child) childs.get(0) : null;
    }

    public List getChildByAdult(Integer adultId) {
        AdultRepository adultRepository = new AdultRepository();
        Adult adult = adultRepository.getAdultById(adultId);

        return new ArrayList(adult.getChildren());
    }

    public List getChildInTrip(Integer dailyTripId) {
        return read(
                "select\n" +
                    "  CH,\n" +
                    "  case when\n" +
                    "    CH.id in (" +
                    "       select CHT.id from " +
                    "           Trip CIT join CIT.childInTrip CHT " +
                    "               where CIT.id = " + dailyTripId +
                    "       ) then true\n" +
                    "  else false\n" +
                    "    end\n" +
                    "from Child CH", null);
    }
}
