package ChatServer;

import ChatClient.ChatClientI;
import Whiteboard.ColoredShape;
import Whiteboard.DrawBoard;
import com.sun.org.apache.regexp.internal.RE;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by TriXXeD on 20/09/2017.
 */
public class Server extends UnicastRemoteObject implements ChatServer.ServerI {
    private ArrayList<ChatClientI> chatClients;
    private List<ColoredShape> shapes;
    private List<ColoredShape> redoShapes;

    protected Server() throws RemoteException {
        chatClients = new ArrayList<>();
        shapes = new Stack<>();
        redoShapes = new Stack<>();
    }
    public synchronized void registerChatClient(ChatClientI chatClient) throws RemoteException{
        this.chatClients.add(chatClient);
    }
    public synchronized void unregisterChatClient(ChatClientI chatClient) throws RemoteException{
        this.chatClients.remove(chatClient);
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

}
