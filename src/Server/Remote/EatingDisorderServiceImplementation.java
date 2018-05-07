package Server.Remote;

import Server.Entity.Aliment;
import Server.Entity.Child;
import Server.Entity.EatingDisorder;
import Server.Entity.EntityInterface;
import Server.Repository.AlimentRepository;
import Server.Repository.ChildRepository;
import Server.Repository.EatingDisorderRepository;
import Server.Result;
import Shared.BaseService;
import Shared.EatingDisorderService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EatingDisorderServiceImplementation extends AbstractBaseService implements EatingDisorderService {
    public EatingDisorderServiceImplementation() throws RemoteException {
        super(new EatingDisorderRepository());
    }

    @Override
    public JSONObject readDisorderByAffectedChild(String childFiscalCode) throws Exception {

        JSONObject returnValues = new JSONObject();
        int count = 0;
        Set<EatingDisorder> eatingDisorders = new EatingDisorderRepository().getEatingDisorderByAffectedChild(childFiscalCode);
        for (EatingDisorder o:eatingDisorders) {
            returnValues.put("aliment"+(count+1),o.getAffectedAliment().getId());
            returnValues.put("disorder"+(count+1),o.getType());
            count++;
        }
        returnValues.put("maxLength", count);
        return returnValues;
    }

    @Override
    public JSONObject setDisorderFromJSON(JSONObject data) throws Exception {

        int max = getMaxLength(data);
        boolean flag;

        AlimentRepository alimentRepository = new AlimentRepository();
        Child mainChild = (new ChildRepository()).getChildById((Integer) data.get("0"));
        Set<EatingDisorder> actualDisorders = (new EatingDisorderRepository()).getEatingDisorderByAffectedChild(mainChild.getFiscalCode());
        Set<EatingDisorder> newDisorders = new HashSet<>();
        for(int count=0; count<max; count++) {
            Aliment affectedAliment = alimentRepository.getAlimentById((Integer) data.get("aliment" + (count + 1)));
            String disorderType = (String) data.get("disorder" + (count + 1));
            EatingDisorder disorderToAdd = new EatingDisorder(mainChild, affectedAliment, disorderType);
            flag = true;
            for (EatingDisorder a : actualDisorders) {
                if (a.getAffectedAliment().getId() == affectedAliment.getId()) {
                    if (!(a.getType().equals(disorderType))) {
                        a.setType(disorderType);
                        Result singleAction = a.save();
                        if(!(singleAction.isSuccess())) {
                            return singleAction.toJson();
                        }
                    }
                    a.setType("");
                    flag = false;
                }
            }
            if (flag) {
                Result singleAction = disorderToAdd.save();
                if(!(singleAction.isSuccess())) {
                    return singleAction.toJson();
                }
            }
        }
        for (EatingDisorder a:actualDisorders) {
            if(!(a.getType().equals(""))) {
                Result singleAction = a.delete();
                if(!(singleAction.isSuccess())) {
                    return singleAction.toJson();
                }
            }
        }

        Result finalAction = new Result();
        finalAction.setSuccess(true);

        /*int max = getMaxLength(data);

        AlimentRepository alimentRepository = new AlimentRepository();
        Child mainChild = (new ChildRepository()).getChildById((Integer) data.get("0"));
        for(int count=0; count<max-1; count++)
        {
            Aliment affectedAliment = alimentRepository.getAlimentById((Integer) data.get("aliment"+(count+1)));
            String disorderType = (String) data.get("disorder"+(count+1));
            EatingDisorder disorderToAdd = new EatingDisorder(mainChild,affectedAliment,disorderType);
            Result singleSave = disorderToAdd.save();
            if(!(singleSave.isSuccess())) {
                return singleSave.toJson();
            }
        }
        Aliment affectedAliment = alimentRepository.getAlimentById((Integer) data.get("aliment"+(max)));
        String disorderType = (String) data.get("disorder"+(max));
        EatingDisorder disorderToAdd = new EatingDisorder(mainChild,affectedAliment,disorderType);
        Result finalSave = disorderToAdd.save();
        return finalSave.toJson();*/

        return finalAction.toJson();
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), EatingDisorder.class);
    }
}
