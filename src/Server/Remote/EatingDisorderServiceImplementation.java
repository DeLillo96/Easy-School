package Server.Remote;

import Server.Entity.Aliment;
import Server.Entity.Child;
import Server.Entity.EatingDisorder;
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
    public JSONObject assign(Integer alimentId, Integer childId, String type) throws Exception {
        Child child = (new ChildRepository()).getChildById(childId);
        Aliment aliment = (new AlimentRepository()).getAlimentById(alimentId);
        List<EatingDisorder> list = eatingDisorderRepository.read();

        for (EatingDisorder eatingDisorder : list) {
            if (eatingDisorder.getAffectedAlimentId().equals(alimentId) &&
                    eatingDisorder.getAffectedChildId().equals(childId)
                    ) {
                eatingDisorder.setType(type);
                return eatingDisorder.save().toJson();
            }
        }

        EatingDisorder newEatingDisorder = new EatingDisorder();
        newEatingDisorder.setAffectedAliment(aliment);
        newEatingDisorder.setAffectedChild(child);
        newEatingDisorder.setType(type);

        return newEatingDisorder.save().toJson();
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
}
