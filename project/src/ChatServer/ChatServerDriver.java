package ChatServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Created by TriXXeD on 20/09/2017.
 */
public class ChatServerDriver {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        Naming.rebind("RMIChatServer", new ChatServer());
    }
}
