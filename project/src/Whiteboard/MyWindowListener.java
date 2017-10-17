package Whiteboard; /**
 * @author Xin Qi
 * @version 1.4
 */
 
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;

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
		if(gui.getUser().IsHost()) {
			int i = JOptionPane.showConfirmDialog(gui, "Do you want to save the changes?", "Whiteboard",
					JOptionPane.YES_NO_CANCEL_OPTION);

			if (i == 0) {
				FileUtil.save(gui.getDrawboard().shapes);
				gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try {
                    gui.getUser().chatClient.chatServer.broadcastClose();
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            } else if (i == 1) {
				// not save
				gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			} else {
				// cancel
				gui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		} else {
			gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			gui.disconnect();
        }

	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}