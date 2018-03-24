package Client.Remote;

import Shared.UserService;

public interface RemoteServicesManager {
    UserService getUserService() throws Exception;

    void closeConnection() throws Exception;
}
