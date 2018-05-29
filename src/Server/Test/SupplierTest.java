package Server.Test;

import Server.Entity.Aliment;
import Server.Entity.Supplier;
import Server.Repository.SupplierRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SupplierTest {
    private static Supplier supplier = new Supplier("Winterfell winter supplier", "WNTRFLLWNTRSPPLS");
    private static Aliment aliment = new Aliment("Wolf");
    private SupplierRepository supplierRepository = new SupplierRepository();

    @BeforeAll
    static void createSupplier() {
        aliment.save();
        supplier.getSupply().add(aliment);
        supplier.save();
    }

    @AfterAll
    static void deleteSupplier() {
        supplier.delete();
        aliment.delete();
    }

    @Test
    void readAlimentsWithSupplier() {
        Supplier readSupplier = supplierRepository.getSupplierByIVA(supplier.getIva());

        assertNotNull(readSupplier, "read aliment error");
        assertTrue(readSupplier.getSupply().size() >= 1, "Failed join");
    }

    @Test
    void addAliment() {
        assertTrue(aliment.getSuppliers().add(supplier), "Failed add supplier operation");

        Result result = aliment.save();
        assertTrue(result.isSuccess(), "Error during saving operation " + result.getMessages().toString());
    }
}
