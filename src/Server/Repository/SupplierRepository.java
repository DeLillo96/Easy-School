package Server.Repository;

import Server.Entity.Aliment;
import Server.Entity.Supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SupplierRepository extends AbstractRepository {

    public SupplierRepository() {
        super("Supplier");
    }

    public Supplier getSupplierById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List suppliers = read(params);

        return suppliers != null && suppliers.size() == 1 ? (Supplier) suppliers.get(0) : null;
    }

    public Supplier getSupplierByName(String name) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        List suppliers = read(params);

        return suppliers != null && suppliers.size() == 1 ? (Supplier) suppliers.get(0) : null;
    }

    public Supplier getSupplierByIVA(String IVA) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("IVA", IVA);
        List suppliers = read(params);

        return suppliers != null && suppliers.size() == 1 ? (Supplier) suppliers.get(0) : null;
    }

    public List getSupplierByAliment(Integer alimentId) {
        AlimentRepository alimentRepository = new AlimentRepository();
        Aliment aliment = alimentRepository.getAlimentById(alimentId);

        return new ArrayList(aliment.getSuppliers());
    }

}
