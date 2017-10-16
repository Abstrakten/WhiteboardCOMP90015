package ChatServer;

import ChatClient.ChatClient;
import ChatClient.ChatClientI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by TriXXeD on 20/09/2017.
 */
public interface ServerI extends Remote {
    void registerChatClient(ChatClientI chatClient) throws RemoteException;
    void unregisterChatClient(ChatClientI chatClient) throws RemoteException;
    void broadcastMessage(String message) throws RemoteException;
}