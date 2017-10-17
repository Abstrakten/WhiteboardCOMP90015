package ChatClient;

import Whiteboard.*;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

/**
 * Created by TriXXeD on 20/09/2017.
 */
public class ChatClient extends UnicastRemoteObject implements ChatClientI, Runnable {
    public ChatServer.ServerI chatServer;
    public String name = null;
    public String lastMsgReceived;

    protected ChatClient(String name, ChatServer.ServerI chatServer) throws RemoteException{
        this.name = name;
        this.chatServer = chatServer;
        chatServer.registerChatClient(this);
    }

    @Override
    // Change this later to fit GUI implementation
    public void retrieveMessage(String message) throws RemoteException {
        //TODO: not allowed to append text in textArea for some reason, or at least it does not happen
        lastMsgReceived = message;
        WhiteBoardGUI.appendMsg(message);
        //textArea.append(message);
        System.out.println(message);

    }

    @Override
    public void updateUserDrawboard(List<ColoredShape> shapes) throws RemoteException {
        WhiteBoardGUI.updateDrawboard(shapes);
    }

    public void disconnect(ChatServer.ServerI chatServer) throws RemoteException {
        chatServer.unregisterChatClient(this);
    }

    @Override
    public void run() {
        /*
        //Scanner or BufferedReader?
        Scanner scanner = new Scanner(System.in);
        String message;
        while(true){
            message = scanner.nextLine();
            try {
                chatServer.broadcastMessage(name + " : " + message, textArea);
            } catch (RemoteException e) { e.printStackTrace(); }
        }*/
    }


}
