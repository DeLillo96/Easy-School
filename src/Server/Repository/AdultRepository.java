package Server.Repository;

import Server.Entity.Adult;
import Server.Entity.Child;
import Server.Entity.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdultRepository extends AbstractRepository {

    public AdultRepository() { super("Adult"); }

    public Adult getAdultById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List adults = read(params);
        if(adults != null && adults.size() == 1)
            return (Adult) adults.get(0);
        else
            return null;
    }

    public Adult getAdultByFiscalCode(String fiscalCode) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("fiscalCode", fiscalCode);
        List adults = read(params);
        if(adults != null && adults.size() == 1)
            return (Adult) adults.get(0);
        else
            return null;
    }

    public Adult getAdultByReferencedUser(Users user) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("user_id", user.getId());
        List adults = read(params);
        if(adults != null && adults.size() == 1)
            return (Adult) adults.get(0);
        else
            return null;
    }

    public List getAdultByChildFiscalCode(String childFiscalCode) {
        ChildRepository childRepository = new ChildRepository();
        Child child = childRepository.getChildByFiscalCode(childFiscalCode);

        return new ArrayList(child.getParents());
    }
}
