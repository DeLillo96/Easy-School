package Server.Repository;

import Server.Entity.Adult;
import Server.Entity.Child;

import java.util.ArrayList;
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
}
