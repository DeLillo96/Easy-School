package Server.Repository;

import Server.Entity.Child;

import java.util.HashMap;
import java.util.List;

public class ChildRepository extends AbstractRepository{
    public ChildRepository() { super("Child"); }

    public Child getChildById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List childs = read(params);
        if(childs != null && childs.size() == 1)
            return (Child) childs.get(0);
        else
            return null;
    }

    public Child getChildByFiscalCode(String codiceFiscale) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("codiceFiscale", codiceFiscale);
        List childs = read(params);
        if(childs != null && childs.size() == 1)
            return (Child) childs.get(0);
        else
            return null;
    }
}
