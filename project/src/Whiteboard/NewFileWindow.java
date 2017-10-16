package Whiteboard; /**
* @author NNavnita
* @version 1.4
*/

import ChatClient.ChatClientDriver;
import ChatServer.ServerDriver;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.*;


public class NewFileWindow {

public void createNew(User u) {

JFrame jf = new JFrame();
jf.setTitle("WhiteBoard");
jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
jf.setSize(new Dimension(500, 200));
jf.setResizable(false);
jf.setLocationRelativeTo(null);
jf.setLayout(new BorderLayout());

// port input
JPanel PortPanel = new JPanel();
PortPanel.setLayout(new BorderLayout());
JTextField portField = new JTextField(20);
PortPanel.add(new JLabel("Port:"), BorderLayout.LINE_START);
PortPanel.add(portField, BorderLayout.LINE_END);

JButton confirmBT = new JButton("Launch New Whiteboard");

jf.add(PortPanel, BorderLayout.NORTH);
jf.add(confirmBT, BorderLayout.SOUTH);

confirmBT.addActionListener(e -> {

// TODO input validation on ip and port
 User user = new User(u.getIp(), portField.getText(), u.getUsername(),true);

//Setting up RMI chat client if host, joining RMI chat client if not
joinOrCreateRMIServer(user, u.getIp(), portField.getText());

WhiteBoardGUI gui = new WhiteBoardGUI(user);
jf.dispose();
});
}
public void joinOrCreateRMIServer(User user, String ip, String port){
//Setting up RMI chat client if host, joining RMI chat client if not
if(user.IsHost() == true){
try {
ServerDriver.setupRMI();
} catch (IOException e1) {
e1.printStackTrace();
}
} else {
String[] args = new String[]{user.getUsername(), ip, port};
try {
ChatClientDriver.main(args);
} catch (RemoteException e1) {
e1.printStackTrace();
} catch (NotBoundException e1) {
e1.printStackTrace();
} catch (MalformedURLException e1) {
e1.printStackTrace();
}
}
}
}
