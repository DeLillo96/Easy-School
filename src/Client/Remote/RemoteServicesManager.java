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

    RelationService getRecipesService() throws Exception;

    BaseService getPlaceService() throws Exception;

    RelationService getParentService() throws Exception;

    RelationService getBusStartingPlaceService() throws Exception;

    RelationService getBusArrivalPlaceService() throws Exception;

    RelationService getTripPlaceService() throws Exception;

    DailyMenuRelationService getDailyMenuService() throws Exception;

    BaseService getDailyTripService() throws Exception;

    RelationService getChildInTripService() throws Exception;

    BaseService getFirstDishService() throws Exception;

    BaseService getSecondDishService() throws Exception;

    BaseService getSideDishService() throws Exception;

    BaseService getSweetDishService() throws Exception;

    BaseService getStaffService() throws Exception;

    PlaceToPlaceService getPlaceToPlaceService() throws Exception;

    RelationService getBusTripService() throws Exception;

    RelationService getPlaceInTripService() throws Exception;

    void closeConnection() throws Exception;
}
