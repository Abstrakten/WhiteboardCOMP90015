package ChatClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Created by TriXXeD on 20/09/2017.
 */
public class ChatClient extends UnicastRemoteObject implements ChatClientI, Runnable {
    private ChatServer.ServerI chatServer;
    private String name = null;

    protected ChatClient(String name, ChatServer.ServerI chatServer) throws RemoteException{
        this.name = name;
        this.chatServer = chatServer;
        chatServer.registerChatClient(this);
    }

    @Override
    // Change this later to fit GUI implementation
    public void retrieveMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    public void disconnect(ChatServer.ServerI chatServer) throws RemoteException {
        chatServer.unregisterChatClient(this);
    }

    @Override
    public void run() {
        //Scanner or BufferedReader?
        Scanner scanner = new Scanner(System.in);
        String message;
        while(true){
            message = scanner.nextLine();
            try {
                chatServer.broadcastMessage(name + " : " + message);
            } catch (RemoteException e) { e.printStackTrace(); }
        }
    }
}
