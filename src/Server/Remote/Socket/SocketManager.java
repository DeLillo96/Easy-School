package Server.Remote.Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketManager extends Thread {
    final private ServerSocket serverSocket;
    final private List<SocketParser> clients;
    final private int port = 9374;

    public SocketManager() throws IOException {
        this.clients = new ArrayList<>();
        this.serverSocket = new ServerSocket(port);
        start();
    }

    public int getPort() {
        return port;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                Socket socket = this.serverSocket.accept();

                clients.add(new SocketParser(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}