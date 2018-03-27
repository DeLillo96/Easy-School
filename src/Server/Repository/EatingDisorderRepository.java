package Server.Repository;

import Server.Entity.Aliment;
import Server.Entity.Child;
import Server.Entity.EatingDisorder;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class EatingDisorderRepository extends AbstractRepository {

    public EatingDisorderRepository() {super("EatingDisorder"); }

    public EatingDisorder getEatingDisorderById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List eatingDisorders = read(params);

        return eatingDisorders != null && eatingDisorders.size() == 1 ? (EatingDisorder) eatingDisorders.get(0) : null;
    }

    /*public List<EatingDisorder> getEatingDisorderByAffectedChild(String affectedChild) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("affectedChildFilter", affectedChild);
        List<EatingDisorder> eatingDisorders = read(params);
        return eatingDisorders != null ? eatingDisorders : null;
    }

    public List<EatingDisorder> getEatingDisorderByAffectedAliment(String affectedAliment) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("affectedAlimentFilter", affectedAliment);
        List<EatingDisorder> eatingDisorders = read(params);
        return eatingDisorders != null ? eatingDisorders : null;
    }*/

    public Set<EatingDisorder> getEatingDisorderByAffectedChild(String affectedChild) {
        ChildRepository childRepository = new ChildRepository();
        Child children = childRepository.getChildByFiscalCode(affectedChild);
        return children.getEatingDisorders();
    }

    public Set<EatingDisorder> getEatingDisorderByAffectedAliment(String affectedAliment) {
        AlimentRepository alimentRepository = new AlimentRepository();
        Aliment aliments = alimentRepository.getAlimentByName(affectedAliment);
        return aliments.getEatingDisorders();
    }

    public List<EatingDisorder> getEatingDisorderByType(String type) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("type", type);
        List<EatingDisorder> eatingDisorders = read(params);
        return eatingDisorders != null ? eatingDisorders : null;
    }

}