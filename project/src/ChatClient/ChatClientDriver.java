package ChatClient;

import ChatServer.ServerI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by TriXXeD on 20/09/2017.
 */
public class ChatClientDriver {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        //Default for testing
        String chatServerURL = "//localhost/RMIChatServer";
        ServerI chatServer = (ServerI) Naming.lookup(chatServerURL);
        new Thread(new ChatClient(args[0], chatServer)).start();
    }


}
