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
        String chatServerURL = null;
        if(args.length >= 2) {chatServerURL = args[1]; }
        //Default for testing
        //chatServerURL = "//localhost/Whiteboard";
        //ServerI chatServer = (ServerI) Naming.lookup(chatServerURL);
        ChatClient chatClient = startChatClient(args[0], chatServerURL);
        //new Thread(chatClient).start();
    }

    public static ChatClient startChatClient(String name, String serverURL) throws RemoteException, MalformedURLException, NotBoundException {
        ServerI chatServer = (ServerI) Naming.lookup(serverURL);
        ChatClient chatClient = new ChatClient(name, chatServer);
        new Thread(chatClient).start();
        return chatClient;
    }


}
