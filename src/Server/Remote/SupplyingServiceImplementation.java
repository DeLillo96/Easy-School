package Server.Remote;

import Server.Entity.Adult;
import Server.Entity.Aliment;
import Server.Entity.Child;
import Server.Entity.Supplier;
import Server.Repository.AdultRepository;
import Server.Repository.AlimentRepository;
import Server.Repository.ChildRepository;
import Server.Repository.SupplierRepository;
import Server.Result;
import Shared.RelationService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class SupplyingServiceImplementation extends UnicastRemoteObject implements RelationService {
    protected SupplierRepository supplierRepository;
    protected AlimentRepository alimentRepository;
    //ADULT = SUPPLIER, CHILD = ALIMENT

    public SupplyingServiceImplementation() throws RemoteException {
        supplierRepository = new SupplierRepository();
        alimentRepository = new AlimentRepository();
    }

    @Override
    public JSONObject assign(Integer alimentId, Integer supplierId) throws Exception {
        Aliment aliment = alimentRepository.getAlimentById(alimentId);
        Supplier supplier = supplierRepository.getSupplierById(supplierId);

        for (Supplier supply : aliment.getSuppliers()) {
            if (supply.getId().equals(supplierId)) {
                Result result = new Result();
                result.addData(supplier);
                return result.toJson();
            }
        }

        supplier.getSupply().add(aliment);
        return supplier.save().toJson();
    }

    @Override
    public JSONObject assign(Integer rightId, List leftIds) throws Exception {
        return null;
    }

    @Override
    public JSONObject assign(List rightIds, Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject deAssign(Integer alimentId, Integer supplierId) throws Exception {
        Supplier supplier = supplierRepository.getSupplierById(supplierId);

        for (Aliment aliment : supplier.getSupply()) {
            if (aliment.getId().equals(alimentId)) {
                supplier.getSupply().remove(aliment);
                return supplier.save().toJson();
            }
        }

        Result result = new Result();
        result.addData(supplier);
        return result.toJson();
    }

    @Override
    public JSONObject leftRead(Integer supplierId) throws Exception {
        List list = alimentRepository.getAlimentBySupplier(supplierId);
        return new Result(true, list).toJson();
    }

    @Override
    public Integer rightCount(Integer rightId) throws Exception {
        return null;
    }

    @Override
    public Integer leftCount(Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject rightRead(Integer alimentId) throws Exception {
        List list = supplierRepository.getSupplierByAliment(alimentId);
        return new Result(true, list).toJson();
    }
}
