package Client.Remote;

import Shared.BaseService;
import Shared.UserService;

public interface RemoteServicesManager {
    UserService getUserService() throws Exception;
    BaseService getChildrenService() throws Exception;
    BaseService getAdultService() throws Exception;
    BaseService getAlimentService() throws Exception;
    BaseService getBusService() throws Exception;
    BaseService getCalendarService() throws Exception;
    BaseService getCategoryService() throws Exception;
    BaseService getDayTripService() throws Exception;
    BaseService getDishService() throws Exception;
    BaseService getEatingDisorderService() throws Exception;
    BaseService getMenuService() throws Exception;
    BaseService getPlaceService() throws Exception;

    void closeConnection() throws Exception;
}
