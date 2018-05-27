package Server.Repository;

import Server.Entity.Aliment;
import Server.Entity.Child;
import Server.Entity.EatingDisorder;

import java.util.*;

public class EatingDisorderRepository extends AbstractRepository {

    public EatingDisorderRepository() {
        super("EatingDisorder");
    }

    public EatingDisorder getEatingDisorderById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List eatingDisorders = read(params);

        return eatingDisorders != null && eatingDisorders.size() == 1 ? (EatingDisorder) eatingDisorders.get(0) : null;
    }

    public List<EatingDisorder> getEatingDisorderByAffectedChild(String childFiscalCode) {
        ChildRepository childRepository = new ChildRepository();
        Child children = childRepository.getChildByFiscalCode(childFiscalCode);
        return new ArrayList(children.getEatingDisorders());
    }

    public List getEatingDisorderByAffectedChild(Integer childId) {
        return read(
                "select\n" +
                "  A,\n" +
                "  case when\n" +
                "    A.id in (" +
                "       select AL.affectedAliment.id from " +
                "           Allergy AL join AL.affectedChild CH " +
                "       where CH.id = " + childId +
                "   ) then 'allergy'\n" +
                "   when\n" +
                "    A.id in (" +
                "       select I.affectedAliment.id from " +
                "           Intolerance I join I.affectedChild CH " +
                "       where CH.id = " + childId +
                "   ) then 'intolerance'\n" +
                "   else null\n" +
                "   end\n" +
                "from Aliment A", null);
    }

    public Set<EatingDisorder> getEatingDisorderByAffectedAliment(String affectedAliment) {
        AlimentRepository alimentRepository = new AlimentRepository();
        Aliment aliments = alimentRepository.getAlimentByName(affectedAliment);
        return aliments.getEatingDisorders();
    }

    public List getEatingDisorderInADay(Integer calendarId) {
        return read(
                "select\n" +
                "  A,\n" +
                "  case when\n" +
                "    A.id in (" +
                "       select AL.affectedAliment.id from " +
                "           Allergy AL join AL.affectedChild CH " +
                "       where CH.id = " + calendarId +
                "   ) then 'allergy'\n" +
                "   when\n" +
                "   else null\n" +
                "   end\n" +
                "from Aliment A", null);
    }

}