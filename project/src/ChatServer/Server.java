package ChatServer;

import ChatClient.ChatClient;
import ChatClient.ChatClientI;

import Whiteboard.ColoredShape;
import Whiteboard.DrawBoard;
import Whiteboard.WhiteBoardGUI;
import com.sun.org.apache.regexp.internal.RE;

import Whiteboard.User;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

/**
 * Created by TriXXeD on 20/09/2017.
 */
public class Server extends UnicastRemoteObject implements ChatServer.ServerI {
    private ArrayList<ChatClientI> chatClients;

    private List<ColoredShape> shapes;
    public ArrayList<User> users;
    private List<ColoredShape> redoShapes;
    private int idCounter = 1;

    protected Server() throws RemoteException {
        chatClients = new ArrayList<ChatClientI>();
        shapes = new Stack<>();
        redoShapes = new Stack<>();
        users = new ArrayList<>();
    }

    public synchronized void registerChatClient(ChatClientI chatClient) throws RemoteException{
        chatClient.setId(idCounter);
        chatClients.add(chatClient);
    }
    public synchronized void registerUser(User user) throws RemoteException {
        if(user.IsHost() || WhiteBoardGUI.newUserPrompt(user)) {
            user.id = idCounter++;
            users.add(user);
            updateClients();
        } else {
            ChatClientI mychat = null;
            for (ChatClientI c : chatClients) {
                if (c.getId() == idCounter) {
                    mychat = c;
                }
            }
            chatClients.remove(mychat);
            mychat.sessionClosed("Host has not allowed you to join. Shutting down");
        }
    }
    public synchronized void unregisterUser(User user) throws RemoteException {
        users.removeIf(u -> u.id == user.id);
        ChatClientI mychat = null;
        for (ChatClientI c : chatClients) {
            if (c.getId() == user.id) {
                mychat = c;
            }
        }
        chatClients.remove(mychat);
        mychat.sessionClosed("Host has kicked you. Shutting down");
    }

    public synchronized void unregisterChatClient(ChatClientI chatClient) throws RemoteException{
        chatClients.remove(chatClient);
    }
    //TODO: This method breaks once a client leaves the network, the server will attempt to send a message to the client who left, causing any user who sends a message to get an exception
    // error is RemoteException in server thread, caused by ConnectExecption: connection refused to host, connection refused: connect
    public synchronized void broadcastMessage(String message) throws RemoteException {
        int i = 0;

        while (i < chatClients.size()) {
            chatClients.get(i++).retrieveMessage(message);
        }
    }


    public synchronized void draw(List<ColoredShape> shapes) throws RemoteException {
        redoShapes.clear();
        this.shapes.addAll(shapes);
        updateClients();
    }

    private synchronized void updateClients() throws RemoteException {
        for (ChatClientI cc : chatClients) {
            cc.updateUserDrawboard(this.shapes);
        }
    }

    @Override
    public synchronized void eraseboard() throws RemoteException {
        this.shapes.clear();
        updateClients();
    }

    @Override
    public synchronized void Undo() throws RemoteException {
        if (!shapes.isEmpty()) {
            redoShapes.add(((Stack<ColoredShape>) shapes).pop());
            updateClients();
        }
    }

    @Override
    public synchronized void Redo() throws RemoteException {
        if (!redoShapes.isEmpty()) {
            shapes.add(((Stack<ColoredShape>) redoShapes).pop());
            updateClients();
        }
    }
    
    public synchronized void broadcastUsers() throws RemoteException{
        int i = 0;
        while (i < chatClients.size()) {
            chatClients.get(i++).retrieveUsers(users);

        }
    }

    @Override
    public synchronized void broadcastClose() throws RemoteException {
        for (ChatClientI cc: chatClients) {
            cc.sessionClosed("Host has closed the session. Shutting down");
        }
    }

    @Override
    public void disconnect1(int id) throws RemoteException {
        users.removeIf(u -> u.id == id);
    }

    public void disconnect2(int id) throws RemoteException {
        ChatClientI mychat = null;
        for (ChatClientI c : chatClients) {
            if (c.getId() == id) {
                mychat = c;
            }
        }
        chatClients.remove(mychat);
    }
}
