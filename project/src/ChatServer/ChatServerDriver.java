package ChatServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by TriXXeD on 20/09/2017.
 */
public class ChatServerDriver {
    public static void main(String[] args) throws IOException {
        //TODO: Check port number and look for alternate ports, port 0 (auto-find) creates error
        LocateRegistry.createRegistry(1099);
        Naming.rebind("RMIChatServer", new ChatServer());
    }
}
