package Server.Repository;

import java.util.List;

public class ChildInVehicleRepository extends AbstractRepository {

    public ChildInVehicleRepository() {
        super("ChildInVehicle");
    }

    public List getChildInVehicleRepository(Integer tripId, Integer childId) {
        return read("select B, " +
                "  case when\n" +
                "    B.id in (" +
                "       select V.id" +
                "       from ChildInVehicle CV\n" +
                "           join CV.vehicles V\n" +
                "       where CV.child.id = " + childId +
                "           and CV.trip.id = " + tripId +
                "       ) then true\n" +
                "  else false\n" +
                "    end\n" +
                "from Bus B\n" +
                "   join B.trips T\n" +
                "where T.id = " + tripId, null);
    }
}
