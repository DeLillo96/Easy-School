package Server.Repository;

import Server.Entity.Staff;

import java.util.HashMap;
import java.util.List;

public class StaffRepository extends AbstractRepository {

    public StaffRepository() {
        super("Staff");
    }

    public Staff getStaffById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List staff = read(params);
        if (staff != null && staff.size() == 1)
            return (Staff) staff.get(0);
        else
            return null;
    }

    public Staff getStaffByFiscalCode(String fiscalCode) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("fiscalCode", fiscalCode);
        List staff = read(params);
        if (staff != null && staff.size() == 1)
            return (Staff) staff.get(0);
        else
            return null;
    }

}
