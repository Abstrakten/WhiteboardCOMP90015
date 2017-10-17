package ChatClient;


import Whiteboard.ColoredShape;
import Whiteboard.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by TriXXeD on 20/09/2017.
 */
public interface ChatClientI extends Remote {
    void retrieveMessage(String message) throws RemoteException;


    void updateUserDrawboard(List<ColoredShape> shapes) throws RemoteException;

    void retrieveUsers(ArrayList<User> list) throws RemoteException;

    void sessionClosed()throws RemoteException;

}
