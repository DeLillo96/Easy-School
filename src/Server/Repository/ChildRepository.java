package Server.Repository;

import Server.Entity.Child;

import java.util.HashMap;
import java.util.List;

public class ChildRepository extends AbstractRepository{
    public ChildRepository() { super("Child"); }

    public Child getChildById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List childs = read("byId", params);
        return (Child) childs.get(0);
    }

    public Child getChildByFiscalCode(String codiceFiscale) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("CodiceFiscale", codiceFiscale);
        List childs = read("byCodiceFiscale", params);
        return (Child) childs.get(0);
    }
}
