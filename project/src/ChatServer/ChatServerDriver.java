package ChatServer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by TriXXeD on 20/09/2017.
 */
public class ChatServerDriver {
    public static void main(String[] args) throws IOException {
        LocateRegistry.createRegistry(0);
        Naming.rebind("RMIChatServer", new ChatServer());
    }
}
