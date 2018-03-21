package Client.Remote;

import Shared.UserService;

import java.net.Socket;

public class SocketServicesManager implements RemoteServicesManager {
    @Override
    public UserService getUserService() throws Exception {
        return new SocketUserService(new Socket("localhost", 9374));
    }
}