package Server.Repository;

import Server.Entity.Child;
import Server.Entity.Pediatrician;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PediatricianRepository extends AbstractRepository {

    public PediatricianRepository() {
        super("Pediatrician");
    }

    public Pediatrician getPediatricianById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List pediatricians = read(params);
        if (pediatricians != null && pediatricians.size() == 1)
            return (Pediatrician) pediatricians.get(0);
        else
            return null;
    }

    public Pediatrician getPediatricianByFiscalCode(String fiscalCode) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("fiscalCode", fiscalCode);
        List pediatrician = read(params);
        if (pediatrician != null && pediatrician.size() == 1)
            return (Pediatrician) pediatrician.get(0);
        else
            return null;
    }

    public List getPediatricianByChild(Integer childId) {
        ChildRepository childRepository = new ChildRepository();
        Child child = childRepository.getChildById(childId);

        return new ArrayList(child.getPediatrician());
    }

}
