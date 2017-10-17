package ChatServer;

import ChatClient.ChatClientI;

import Whiteboard.ColoredShape;
import Whiteboard.DrawBoard;
import com.sun.org.apache.regexp.internal.RE;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Whiteboard.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * Created by TriXXeD on 20/09/2017.
 */
public interface ServerI extends Remote {
    void registerChatClient(ChatClientI chatClient) throws RemoteException;
    void unregisterChatClient(ChatClientI chatClient) throws RemoteException;
    void broadcastMessage(String message) throws RemoteException;

    void draw(List<ColoredShape> shapes) throws RemoteException;

    void eraseboard() throws RemoteException;
    void Undo() throws RemoteException;
    void Redo() throws RemoteException;


    void registerUser(User user) throws RemoteException;
    void unregisterUser(User user) throws RemoteException;
    void broadcastUsers() throws RemoteException;

    void broadcastClose() throws RemoteException;

    void disconnect1(int id) throws RemoteException;
    void disconnect2(int id) throws RemoteException;
}
