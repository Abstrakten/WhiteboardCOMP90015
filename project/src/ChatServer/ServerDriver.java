package ChatServer;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * Created by TriXXeD on 20/09/2017.
 */
public class ServerDriver {
    public static void main(String[] args) throws IOException {
        //setupRMI();
    }

    public static void setupRMI(int port) throws IOException {
        //TODO: Check port number and look for alternate ports, port 0 (auto-find) creates error
        LocateRegistry.createRegistry(port);
        Naming.rebind("Whiteboard", new Server());
    }
}
