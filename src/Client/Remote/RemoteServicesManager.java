package Client.Remote;

import Shared.BaseService;
import Shared.DishService;
import Shared.UserService;

public interface RemoteServicesManager {
    UserService getUserService() throws Exception;
    BaseService getChildrenService() throws Exception;
    BaseService getMenuService() throws Exception;
    DishService getDishService() throws Exception;
    BaseService getAdultService() throws Exception;
    BaseService getEatingDisorderService() throws Exception;

    void closeConnection() throws Exception;
}
