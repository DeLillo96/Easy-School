package Client.Remote;

import Shared.BaseService;
import Shared.UserService;

public interface RemoteServicesManager {
    UserService getUserService() throws Exception;
    BaseService getChildrenService() throws Exception;

    void closeConnection() throws Exception;
}
