package Client.Remote;

import Shared.AdultService;
import Shared.BaseService;
import Shared.DishService;
import Shared.UserService;

public interface RemoteServicesManager {
    UserService getUserService() throws Exception;
    BaseService getChildrenService() throws Exception;
    AdultService getAdultService() throws Exception;
    BaseService getAlimentService() throws Exception;
    BaseService getBusService() throws Exception;
    BaseService getCalendarService() throws Exception;
    BaseService getCategoryService() throws Exception;
    BaseService getDayTripService() throws Exception;
    DishService getDishService() throws Exception;
    BaseService getEatingDisorderService() throws Exception;
    BaseService getMenuService() throws Exception;
    BaseService getPlaceService() throws Exception;

    void closeConnection() throws Exception;
}
