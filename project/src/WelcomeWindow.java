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
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;



public class WelcomeWindow {

//    public enum state { HOST, CLIENT }

	public void createOrJoin() {

		JFrame jf = new JFrame();
		jf.setTitle("WhiteBoard 1.4");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(new Dimension(400, 150));
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setLayout(new BorderLayout());

		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		JPanel jp1_2 = new JPanel();
		JPanel jp3 = new JPanel();

		jp1.setLayout(new FlowLayout(FlowLayout.CENTER));
		jp1.setBackground(Color.WHITE);
		jp2.setLayout(new FlowLayout(FlowLayout.CENTER));
		jp2.setBackground(Color.WHITE);
		jp1_2.setBackground(Color.WHITE);
		jp3.setLayout(new FlowLayout(FlowLayout.CENTER));
		jp3.setBackground(Color.WHITE);

		JLabel userNameJL = new JLabel("Username");
		JTextField userNameJTF = new JTextField(20);
		jp1.add(userNameJL, FlowLayout.LEFT);
		jp1.add(userNameJTF, FlowLayout.CENTER);

		JRadioButton createBT = new JRadioButton("Create a new whiteboard");
		JRadioButton joinBT = new JRadioButton("Join an exist whiteboard");
		createBT.setActionCommand("1");
		joinBT.setActionCommand("0");
		ButtonGroup createOrJoinBG = new ButtonGroup();
		createBT.setSelected(true);

		JButton confirmBT = new JButton("Confirm");
		JButton cancelBT = new JButton("Cancel");
		confirmBT.setActionCommand("Confirm");
		cancelBT.setActionCommand("Cancel");
		jp3.add(confirmBT, FlowLayout.LEFT);
		jp3.add(cancelBT, FlowLayout.CENTER);

		createOrJoinBG.add(joinBT);
		createOrJoinBG.add(createBT);
		jp2.add(joinBT, FlowLayout.LEFT);
		jp2.add(createBT, FlowLayout.CENTER);

		jp1_2.add(jp1, BorderLayout.CENTER);
		jp1_2.add(jp2,BorderLayout.SOUTH);

		jf.add(jp1_2, BorderLayout.CENTER);
		jf.add(jp3, BorderLayout.SOUTH);

		confirmBT.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(userNameJTF.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(jf,
							"Username can not be null!", "whiteBoard1.1",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				ButtonModel bm = createOrJoinBG.getSelection();
				String command = bm.getActionCommand();
				if (command.equals("1")) {
					JOptionPane.showMessageDialog(jf,
							"Please enter the server IP address and the server Port Number", "whiteBoard1.4",
							JOptionPane.INFORMATION_MESSAGE);
					InputAddrWindow inputAddWin = new InputAddrWindow(true, userNameJTF.getText());
					jf.setVisible(false);
					jf.dispose();
				} else {
					JOptionPane.showMessageDialog(jf, "Please enter the host's IP address and Port Number",
							"whiteBoard1.4", JOptionPane.INFORMATION_MESSAGE);
					InputAddrWindow inputAddWin = new InputAddrWindow(false, userNameJTF.getText());
					jf.setVisible(false);
					jf.dispose();
				}
			}
		});

		cancelBT.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		jf.setVisible(true);
	}

	public static void main(String[] args) {
		WelcomeWindow welcomeWindow = new WelcomeWindow();
		welcomeWindow.createOrJoin();
	}
}
