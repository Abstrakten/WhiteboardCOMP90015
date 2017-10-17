package Whiteboard; /**
 * @author Xin Qi
 * @version 1.4
 */

import ChatClient.ChatClientDriver;
import ChatServer.ServerDriver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class WelcomeWindow {

	public void createOrJoin() {
        try{
        InetAddress ip = InetAddress.getLocalHost();
		JFrame jf = new JFrame();
		jf.setTitle("WhiteBoard");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(new Dimension(500, 200));
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setLayout(new BorderLayout());

		// input panel
        JPanel InputPanel = new JPanel();
        InputPanel.setLayout(new BoxLayout(InputPanel,BoxLayout.PAGE_AXIS));

        // name input
		JPanel UsernamePanel = new JPanel();
        UsernamePanel.setLayout(new BorderLayout());
        JTextField nameField = new JTextField(20);
        UsernamePanel.add(new JLabel("Username:"), BorderLayout.LINE_START);
        UsernamePanel.add(nameField, BorderLayout.LINE_END);
        InputPanel.add(UsernamePanel);

        // ip input
        JPanel IPPanel = new JPanel();
        IPPanel.setLayout(new BorderLayout());
        JTextField ipField = new JTextField(20);
        IPPanel.add(new JLabel("IP Address:"), BorderLayout.LINE_START);
        IPPanel.add(ipField, BorderLayout.LINE_END);
        InputPanel.add(IPPanel);

        // port input
        JPanel PortPanel = new JPanel();
        PortPanel.setLayout(new BorderLayout());
        JTextField portField = new JTextField(20);
        PortPanel.add(new JLabel("Port:"), BorderLayout.LINE_START);
        PortPanel.add(portField, BorderLayout.LINE_END);
        InputPanel.add(PortPanel);

        // Join or create buttons group
        JRadioButton createBT = new JRadioButton("Create a new whiteboard");
        JRadioButton joinBT = new JRadioButton("Join an existing whiteboard");
        createBT.setActionCommand("host");
        joinBT.setActionCommand("client");
        createBT.setSelected(true);
        //initially ipField is disabled
            ipField.setEnabled(false);
            ipField.setText(ip.getHostAddress());
        ButtonGroup createOrJoinBG = new ButtonGroup();
        createOrJoinBG.add(joinBT);
        createOrJoinBG.add(createBT);
        joinBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ipField.setEnabled(true);
                ipField.setText(null);

            }
        });
        createBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ipField.setEnabled(false);
                    ipField.setText(ip.getHostAddress());

                }
        });

        // Join or create button panel
		JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.add(joinBT);
        buttonPanel.add(createBT);

		JPanel nameAndRadioPanel = new JPanel();
        nameAndRadioPanel.setBackground(Color.WHITE);
        nameAndRadioPanel.add(InputPanel, BorderLayout.CENTER);
        nameAndRadioPanel.add(buttonPanel,BorderLayout.SOUTH);

        JButton confirmBT = new JButton("Launch Whiteboard");

		jf.add(nameAndRadioPanel, BorderLayout.CENTER);
		jf.add(confirmBT, BorderLayout.SOUTH);

		confirmBT.addActionListener(e -> {
            int flag = 0;
            String str="";
		    // TODO input validation on ip and port
            if(nameField.getText().trim().isEmpty()) {
                str = "A username is required\n";
                flag++;
            }
            if(!validate(ipField.getText().trim())){
                str+="A valid IP address is required";
                flag++;
            }
            if(flag == 0){
                User user = new User(ipField.getText(), portField.getText(), nameField.getText(),
                        createOrJoinBG.getSelection().getActionCommand().equals("host"));

                //Setting up RMI chat client if host, joining RMI chat client if not
                //TODO: RMI takes a serverURL as string not an IP - Currently defaults are taking care of it
                joinOrCreateRMIServer(user, ipField.getText());

                WhiteBoardGUI gui = new WhiteBoardGUI(user);
                jf.dispose();
            }
            else{
                JOptionPane.showMessageDialog(jf,str,"whiteBoard",JOptionPane.INFORMATION_MESSAGE);
            }
        });

		jf.setVisible(true);
        }catch(UnknownHostException ex) {
        }
	}

    public void joinOrCreateRMIServer(User user, String serverURL){
        //Setting up RMI chat client if host, joining RMI chat client if not
        //Default for testing
        serverURL = "//localhost/RMIChatServer";
        //
        if(user.IsHost() == true){
            try {
                ServerDriver.setupRMI();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            joinRMIServerChat(user, serverURL);
        } else {
            joinRMIServerChat(user, serverURL);
        }
    }

    public void joinRMIServerChat(User user, String serverURL){
        try {
            user.chatClient = ChatClientDriver.startChatClient(user.getUsername(), serverURL);
        } catch (RemoteException e1) {
            e1.printStackTrace();
        } catch (NotBoundException e1) {
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
    }
    public static boolean validate(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
    }
	public static void main(String[] args) {
		WelcomeWindow welcomeWindow = new WelcomeWindow();
		welcomeWindow.createOrJoin();
	}
}
