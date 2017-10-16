/**
 * @author Xin Qi
 * @version 1.4
 */

import ChatClient.ChatClientDriver;
import ChatServer.ChatServerDriver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.sound.sampled.Port;
import javax.swing.*;
import javax.swing.text.FlowView;


public class WelcomeWindow {

	public void createOrJoin() {

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
        ButtonGroup createOrJoinBG = new ButtonGroup();
        createOrJoinBG.add(joinBT);
        createOrJoinBG.add(createBT);

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

		    // TODO input validation on ip and port

            if(nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(jf,
                        "A username is required", "whiteBoard",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                User user = new User(ipField.getText(), portField.getText(), nameField.getText(),
                        createOrJoinBG.getSelection().getActionCommand().equals("host"));

                //Setting up RMI chat client if host, joining RMI chat client if not
                joinOrCreateRMIChat(user, ipField.getText(), portField.getText());

                WhiteBoardGUI gui = new WhiteBoardGUI(user);
                jf.dispose();
            }
        });

		jf.setVisible(true);
	}

    public void joinOrCreateRMIChat(User user, String ip, String port){
        //Setting up RMI chat client if host, joining RMI chat client if not
        if(user.IsHost() == true){
            try {
                ChatServerDriver.setupRMI();
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
	public static void main(String[] args) {
		WelcomeWindow welcomeWindow = new WelcomeWindow();
		welcomeWindow.createOrJoin();
	}
}
