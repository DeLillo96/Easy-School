package Server.Repository;

import Server.Entity.Adult;
import Server.Entity.Users;

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

    public Adult getAdultByFiscalCode(String codiceFiscale) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("codiceFiscale", codiceFiscale);
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
}
