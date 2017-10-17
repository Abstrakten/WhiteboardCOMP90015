package ChatClient;


import Whiteboard.ColoredShape;

import Whiteboard.User;


import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;

import java.util.ArrayList;


/**
 * Created by TriXXeD on 20/09/2017.
 */
public interface ChatClientI extends Remote {
    void retrieveMessage(String message) throws RemoteException;


    void updateUserDrawboard(List<ColoredShape> shapes) throws RemoteException;

    void retrieveUsers(ArrayList<User> list) throws RemoteException;

}
