package Client.Remote;

import Shared.*;

public interface RemoteServicesManager {
    UserService getUserService() throws Exception;

    BaseService getChildrenService() throws Exception;

    BaseService getAdultService() throws Exception;

    BaseService getAlimentService() throws Exception;

    BaseService getBusService() throws Exception;

    BaseService getCalendarService() throws Exception;

    BaseService getDayTripService() throws Exception;

    EatingDisorderService getEatingDisorderService() throws Exception;

    BaseService getMenuService() throws Exception;

    AssignService getRecipesService() throws Exception;

    BaseService getPlaceService() throws Exception;

    AssignService getParentService() throws Exception;

    AssignService getBusStartingPlaceService() throws Exception;

    AssignService getBusArrivalPlaceService() throws Exception;

    AssignService getTripPlaceService() throws Exception;

    BaseService getFirstDishService() throws Exception;

    BaseService getSecondDishService() throws Exception;

    BaseService getSideDishService() throws Exception;

    BaseService getSweetDishService() throws Exception;

    void closeConnection() throws Exception;
}
