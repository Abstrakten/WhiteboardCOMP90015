package ChatClient;

import Whiteboard.*;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.List;

import java.util.ArrayList;

import java.util.Scanner;

/**
 * Created by TriXXeD on 20/09/2017.
 */
public class ChatClient extends UnicastRemoteObject implements ChatClientI, Runnable {
    public ChatServer.ServerI chatServer;
    public String name = null;
    public String lastMsgReceived;
    public int id;

    protected ChatClient(String name, ChatServer.ServerI chatServer) throws RemoteException{
        this.name = name;
        this.chatServer = chatServer;
        chatServer.registerChatClient(this);
    }

    @Override
    public void setId(int i) throws RemoteException {
        id = i;
    }

    @Override
    public int getId() throws RemoteException {
        return id;
    }

    @Override
    // Change this later to fit GUI implementation
    public void retrieveMessage(String message) throws RemoteException {
        lastMsgReceived = message;
        WhiteBoardGUI.appendMsg(message);
    }

    @Override
    public void updateUserDrawboard(List<ColoredShape> shapes) throws RemoteException {
        WhiteBoardGUI.updateDrawboard(shapes);
    }


    public void retrieveUsers(ArrayList<User> users) throws RemoteException{
        WhiteBoardGUI.userArrayList = users;
        WhiteBoardGUI.updateUserList(users);
    }

    @Override
    public void sessionClosed(String s) throws RemoteException {
        WhiteBoardGUI.displaySessionClosed(s);
    }

    public void disconnect(ChatServer.ServerI chatServer) throws RemoteException {
        chatServer.unregisterChatClient(this);
    }

    public void run() { }
}
