/**
 * @author Xin Qi
 * @version 1.4
 */
 
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.security.cert.PKIXRevocationChecker.Option;

import javax.management.OperationsException;
import javax.swing.*;

public class MyWindowListener implements WindowListener {

	JFrame mainFrame;

	public MyWindowListener(JFrame jf) {

		this.mainFrame = jf;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {

		int i = JOptionPane.showConfirmDialog(mainFrame, "Do you want to save the changes?", "WhiteBoard1.4",
				JOptionPane.YES_NO_CANCEL_OPTION);

		if (i == 0) {
			// save
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		} else if(i == 1) {
			// not save
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			// cancel
			mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}

	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
