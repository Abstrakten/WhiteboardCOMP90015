import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

public class DrawAction implements MouseListener, MouseMotionListener {

	public ButtonGroup funcBG;
	public ButtonGroup colorBG;
	public Graphics2D pen;
	public WhiteBoardGUI gui;
	public ButtonModel selectedShape;
	public ButtonModel selectedColor;
	private int x1, y1, x2, y2, ox, oy, x3, y3;
	private String command;

	public DrawAction(ButtonGroup funcBG, ButtonGroup colorBG, Graphics pen, WhiteBoardGUI whiteBoardGUI) {

		this.colorBG = colorBG;
	    this.funcBG = funcBG;
		this.pen = (Graphics2D) pen;
		this.gui = whiteBoardGUI;
		
		System.out.println("draw action loading complete..");

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		ButtonModel selectedBT = this.funcBG.getSelection();
		this.command = selectedBT.getActionCommand();
		pen.setColor(gui.penColor);
		x1 = e.getX();
		y1 = e.getY();
		System.out.println("Mouse pressed...");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		ButtonModel selectedBT = this.funcBG.getSelection();
		this.command = selectedBT.getActionCommand();
		x2 = e.getX();
		y2 = e.getY();
		System.out.println("Mouse released...");
		System.out.println("command is: "+command);
		if (command.equals("Line")) {
			pen.drawLine(x1, y1, x2, y2);
			System.out.println("Drawing line...");
		} else if (command.equals("Rectangle")) {
			pen.drawRect(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1), Math.abs(y1 - y2));
			System.out.println("Drawing rect...");
		} else if (command.equals("Circle")) {
			pen.drawOval(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1), Math.abs(x2 - x1));
			System.out.println("Drawing circle...");
		} else if (command.equals("Oval")) {
			pen.drawOval(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1), Math.abs(y1 - y2));
			System.out.println("Drawing oval...");
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		ButtonModel selectedBT = this.funcBG.getSelection();
		this.command = selectedBT.getActionCommand();
		int x = e.getX();
		int y = e.getY();
		System.out.println("Mouse dragged...");
		gui.xJLabel.setText("x -> "+x);
		gui.yJLabel.setText("y -> "+y);
		if (command.equals("Free draw")) {
			pen.drawLine(x1, y1, x, y);
			x1 = x;
			y1 = y;
			System.out.println("Free drawing...");
		} else if(command.equals("Erase")) {
			pen.setColor(Color.WHITE);
			pen.setStroke(new BasicStroke(15));
			pen.drawLine(x1, y1, x, y);
			x1 = x;
			y1 = y;
			System.out.println("Erase using...");
		} else if (command.equals("Text")) {
			
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
