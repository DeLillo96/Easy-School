package Server.Remote;

import Server.Entity.*;
import Server.Repository.AlimentRepository;
import Server.Repository.ChildRepository;
import Server.Repository.EatingDisorderRepository;
import Server.Result;
import Shared.EatingDisorderService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class EatingDisorderServiceImplementation extends UnicastRemoteObject implements EatingDisorderService {
    protected EatingDisorderRepository eatingDisorderRepository;

    public EatingDisorderServiceImplementation() throws RemoteException {
        eatingDisorderRepository = new EatingDisorderRepository();
    }

    @Override
    public JSONObject assign(Integer rightId, Integer leftId) throws Exception {
        return null;
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
    public JSONObject assignAllergy(Integer alimentId, Integer childId) throws Exception {
        Child child = (new ChildRepository()).getChildById(childId);
        Aliment aliment = (new AlimentRepository()).getAlimentById(alimentId);
        List<EatingDisorder> list = eatingDisorderRepository.read();

        for (EatingDisorder eatingDisorder : list) {
            if (eatingDisorder.getAffectedAlimentId().equals(alimentId) &&
                    eatingDisorder.getAffectedChildId().equals(childId)
                    ) {
                if(eatingDisorder instanceof Allergy) {
                    return eatingDisorder.save().toJson();
                } else {
                    eatingDisorder.delete();
                }
            }
        }


        Allergy newAllergy = new Allergy();
        newAllergy.setAffectedAliment(aliment);
        newAllergy.setAffectedChild(child);

        return newAllergy.save().toJson();
    }

    @Override
    public JSONObject assignIntolerance(Integer alimentId, Integer childId) throws Exception {
        Child child = (new ChildRepository()).getChildById(childId);
        Aliment aliment = (new AlimentRepository()).getAlimentById(alimentId);
        List<EatingDisorder> list = eatingDisorderRepository.read();

        for (EatingDisorder eatingDisorder : list) {
            if (eatingDisorder.getAffectedAlimentId().equals(alimentId) &&
                    eatingDisorder.getAffectedChildId().equals(childId)
                    ) {
                if(eatingDisorder instanceof Intolerance) {
                    return eatingDisorder.save().toJson();
                } else {
                    eatingDisorder.delete();
                }
            }
        }


        Intolerance newIntolerance = new Intolerance();
        newIntolerance.setAffectedAliment(aliment);
        newIntolerance.setAffectedChild(child);

        return newIntolerance.save().toJson();
    }

    @Override
    public JSONObject deAssign(Integer rightId, Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject rightRead(Integer childId) throws Exception {
        List list = eatingDisorderRepository.getEatingDisorderByAffectedChild(childId);
        return new Result(true, list).toJson();
    }

    @Override
    public JSONObject leftRead(Integer leftId) throws Exception {
        return null;
    }

    @Override
    public Integer rightCount(Integer rightId) throws Exception {
        return null;
    }

    @Override
    public Integer leftCount(Integer leftId) throws Exception {
        return null;
    }
}
