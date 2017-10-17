package Whiteboard; /**
 * @author Xin Qi
 * @version 1.4
 */
 
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class MyWindowListener implements WindowListener {

	private WhiteBoardGUI gui;

	public MyWindowListener(WhiteBoardGUI gui) {
		this.gui = gui;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e) {
		int i = JOptionPane.showConfirmDialog(gui, "Do you want to save the changes?", "WhiteBoard",
				JOptionPane.YES_NO_CANCEL_OPTION);

		if (i == 0) {
			FileUtil.save(gui.getDrawboard().shapes);
			gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		} else if(i == 1) {
			// not save
			gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		} else {
			// cancel
			gui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}

	}

	@Override
	public void windowClosed(WindowEvent e) {
		//System.exit(0);

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