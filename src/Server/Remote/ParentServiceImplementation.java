package Server.Remote;

import Server.Entity.Adult;
import Server.Entity.Child;
import Server.Repository.AdultRepository;
import Server.Repository.ChildRepository;
import Server.Result;
import Shared.RelationService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ParentServiceImplementation extends UnicastRemoteObject implements RelationService {
    protected AdultRepository adultRepository;
    protected ChildRepository childRepository;

    public ParentServiceImplementation() throws RemoteException {
        adultRepository = new AdultRepository();
        childRepository = new ChildRepository();
    }

    @Override
    public JSONObject assign(Integer childId, Integer adultId) throws Exception {
        Child child = childRepository.getChildById(childId);
        Adult adult = adultRepository.getAdultById(adultId);

        for (Adult parent : child.getParents()) {
            if (parent.getId().equals(adultId)) {
                Result result = new Result();
                result.addData(adult);
                return result.toJson();
            }
        }

        adult.getChildren().add(child);
        return adult.save().toJson();
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
    public JSONObject deAssign(Integer childId, Integer adultId) throws Exception {
        Adult adult = adultRepository.getAdultById(adultId);

        for (Child child : adult.getChildren()) {
            if (child.getId().equals(childId)) {
                adult.getChildren().remove(child);
                return adult.save().toJson();
            }
        }

        Result result = new Result();
        result.addData(adult);
        return result.toJson();
    }

    @Override
    public JSONObject leftRead(Integer adultId) throws Exception {
        List list = childRepository.getChildByAdult(adultId);
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
        List list = adultRepository.getAdultByChild(childId);
        return new Result(true, list).toJson();
    }
}
