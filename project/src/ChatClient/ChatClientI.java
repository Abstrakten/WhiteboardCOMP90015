package ChatClient;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by TriXXeD on 20/09/2017.
 */
public interface ChatClientI extends Remote {
    void retrieveMessage(String message, JTextArea textArea) throws RemoteException;
}
