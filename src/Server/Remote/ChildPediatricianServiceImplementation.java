package Server.Remote;

import Server.Entity.Child;
import Server.Entity.Pediatrician;
import Server.Repository.ChildRepository;
import Server.Repository.PediatricianRepository;
import Server.Result;
import Shared.RelationService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ChildPediatricianServiceImplementation extends UnicastRemoteObject implements RelationService {
    protected PediatricianRepository pediatricianRepository;
    protected ChildRepository childRepository;

    public ChildPediatricianServiceImplementation() throws RemoteException {
        pediatricianRepository = new PediatricianRepository();
        childRepository = new ChildRepository();
    }

    @Override
    public JSONObject assign(Integer childId, Integer pediatricianId) throws Exception {
        Child child = childRepository.getChildById(childId);
        Pediatrician pediatrician = pediatricianRepository.getPediatricianById(pediatricianId);

        for (Pediatrician follow : child.getPediatrician()) {
            if (follow.getId().equals(pediatricianId)) {
                Result result = new Result();
                result.addData(follow);
                return result.toJson();
            }
        }

        pediatrician.getChildren().add(child);
        return pediatrician.save().toJson();
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
    public JSONObject deAssign(Integer childId, Integer pediatricianId) throws Exception {
        Pediatrician pediatrician = pediatricianRepository.getPediatricianById(pediatricianId);

        for (Child child : pediatrician.getChildren()) {
            if (child.getId().equals(childId)) {
                pediatrician.getChildren().remove(child);
                return pediatrician.save().toJson();
            }
        }

        Result result = new Result();
        result.addData(pediatrician);
        return result.toJson();
    }

    @Override
    public JSONObject leftRead(Integer pediatricianId) throws Exception {
        List list = childRepository.getChildByPediatrician(pediatricianId);
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
    public JSONObject rightRead(Integer childId) throws Exception {
        List list = pediatricianRepository.getPediatricianByChild(childId);
        return new Result(true, list).toJson();
    }
}
