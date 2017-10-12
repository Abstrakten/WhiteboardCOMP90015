
/**
 * @author Xin Qi
 * @version 1.4
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputAddrWindow {
	public Boolean state;
	public String username;
	private String serverIp;
	private String serverPort;
	private List<User> users = new ArrayList<>(); 

	public InputAddrWindow(boolean state, String username) {
		this.state = state;
		this.username = username;

		String titleHost = "Client";

		if (state) {
			
			titleHost = "Host";
			
		}
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setTitle("WhiteBoard1.4" + " " + titleHost + " " + username);
		jf.setBackground(Color.WHITE);
		jf.setLayout(new BorderLayout());
		jf.setSize(new Dimension(400, 150));
		jf.setResizable(false);

		JPanel IPPanel = new JPanel();
		JPanel PortPanel = new JPanel();
		JPanel jp3 = new JPanel();
		JLabel labelIp = new JLabel("  IP Address ");
		JTextField jtfIP = new JTextField(20);
		JLabel labelPort = new JLabel("Port Number");
		JTextField jtfPort = new JTextField(20);
		JButton connectBT = new JButton("Connect");
		JButton cancelBT = new JButton("Cancel");
		IPPanel.setBackground(Color.WHITE);
		IPPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		PortPanel.setBackground(Color.WHITE);
		PortPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		jp3.setBackground(Color.WHITE);
		jp3.setLayout(new FlowLayout(FlowLayout.CENTER));
		IPPanel.add(labelIp);
		IPPanel.add(jtfIP);
		jf.add(IPPanel, BorderLayout.NORTH);
		PortPanel.add(labelPort);
		PortPanel.add(jtfPort);
		jf.add(PortPanel, BorderLayout.CENTER);
		jp3.add(connectBT);
		jp3.add(cancelBT);
		jf.add(jp3, BorderLayout.SOUTH);
		jf.setVisible(true);

		connectBT.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (jtfIP.getText().trim().isEmpty() || jtfPort.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(jf, "IP and PORT can not be null!", "whiteBoard1.4",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (testConnect(state, username, jtfIP.getText(), jtfPort.getText()).equals("1")) {
					
					// TODO 
					serverIp = jtfIP.getText();
					serverPort = jtfPort.getText();
					
					
					User user;
					try {
						user = new User(InetAddress.getLocalHost().getHostAddress(), "10086", username, state);
						
						// TODO get the users list from server and this user into the list, then transform the whole list to GUI.
						WhiteBoardGUI gui = new WhiteBoardGUI(user);
						jf.dispose();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(jf,
							testConnect(state, username, jtfIP.getText(), jtfPort.getText()) + "..."
									+ "Please check the IP and PORT address then retry...", //
							"whiteBoard", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		cancelBT.addActionListener(e -> jf.dispose());
	}

	protected String testConnect(Boolean state, String username, String ipAddress, String portAddress) {

		// TODO Auto-generated method stub

		// the method is used to test server and connection, the arguments are inputs
		// from users, so it may not valid.

		// and the IP and PORT are both String, so u may need to cast them to whatever
		// u want.

		// if the server confirmed the connection, please return String "1", and the
		// program will processes to white board part.

		// if any exceptions occured, include the wrong format of ipAddress and
		// portAddress, just return some information. e.g server no response.

		// the state means: a Host or a Client, it only has 2 values: "Host" and
		// "Client".

		// the username is given by the user. So it may contain any character.

		// according to the requirements (bullet 1 on page 7 in the slide), if user
		// choose to join as client, the IP address and PORT number is belongs to the
		// target host. Otherwise they should be the server's addresses. i didn't
		// distinguish them, so u may need to.

		return "1";
	}
}
