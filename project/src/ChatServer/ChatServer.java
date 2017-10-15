package ChatServer;

import ChatClient.ChatClientI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by TriXXeD on 20/09/2017.
 */
public class ChatServer extends UnicastRemoteObject implements ChatServerI {
    //private static final long serialVersUID = 1L;
    private ArrayList<ChatClientI> chatClients;
    protected ChatServer() throws RemoteException {
        chatClients = new ArrayList<ChatClientI>();
    }

    public synchronized void registerChatClient(ChatClientI chatClient) throws RemoteException{
        this.chatClients.add(chatClient);
    }
    //TODO: This method breaks once a client leaves the network, the server will attempt to send a message to the client who left, causing any user who sends a message to get an exception
    // error is RemoteException in server thread, caused by ConnectExecption: connection refused to host, connection refused: connect
    public synchronized void broadcastMessage(String message) throws RemoteException {
        int i = 0;
        while (i < chatClients.size()) {
            chatClients.get(i++).retrieveMessage(message);
        }
    }
}