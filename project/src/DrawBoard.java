import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.net.InetAddress;

import javax.sound.sampled.Line;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.RepaintManager;

import org.w3c.dom.css.Rect;

public class DrawBoard extends JPanel implements MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ButtonGroup funcBG, colorBG;
	public Graphics2D pen;
	public WhiteBoardGUI gui;
	public ButtonModel selectedShape, selectedColor;
	public static float x, y, x1, y1, x2, y2;
	private String command;
	public static Shape[] shapes = new Shape[100];
	public static Color[] shapesColor = new Color[100];
	public static int shapeIndex = 0;
	private Color c;

	public DrawBoard(ButtonGroup funcBG, ButtonGroup colorBG, WhiteBoardGUI whiteBoardGUI) {

		this.colorBG = colorBG;
		this.funcBG = funcBG;
		this.gui = whiteBoardGUI;
		this.setLayout(new FlowLayout());
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(700, 620));repaint();
		this.pen = (Graphics2D) gui.getGraphics();
		Line2D line2d = new Line2D.Float();
		shapes[shapeIndex] = line2d;
		shapesColor[shapeIndex] = Color.BLACK;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
		System.out.println("draw board initial complete...");
	}

	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int j = 0;
		System.out.println("paintComponent has been called...");
		System.out.println("shapeIndex: " + shapeIndex);
		//System.out.println(shapesColor[shapeIndex].toString());
		// if (shapeIndex > 0) {
		// System.out.println("Ready to Repaint...");
		while (j <= shapeIndex) {
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(shapesColor[j]);
			//System.out.println(shapesColor[shapeIndex].toString());
			Shape s = shapes[j];
			System.out.println("Painting: shape " + j);
			System.out.println(s);
			g2d.draw(s);
			g2d.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
			System.out.println("Painted: shape " + j+"Color is: "+ shapesColor[shapeIndex]);
			this.validate();
			j++;
		}
		// }
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		ButtonModel selectedBT = this.funcBG.getSelection();
		this.command = selectedBT.getActionCommand();

		x1 = e.getX();
		y1 = e.getY();
		System.out.println("Mouse pressed...");
		Line2D line2d = new Line2D.Float();
		shapes[shapeIndex] = line2d;
		shapesColor[shapeIndex] = gui.getPenColor();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		ButtonModel selectedBT = this.funcBG.getSelection();
		this.command = selectedBT.getActionCommand();
		x2 = e.getX();
		y2 = e.getY();
		System.out.println("Mouse released...");
		System.out.println("command is: " + command);
		if (command.equals("Line")) {
			// pen.drawLine(x1, y1, x2, y2);
			System.out.println("Drawing line...");
		} else if (command.equals("Rectangle")) {
			Rectangle2D rect = new Rectangle2D.Float(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1),
					Math.abs(y1 - y2));
			
			shapeIndex++;
			
			shapes[shapeIndex] = rect;
			this.c = gui.getPenColor();
			shapesColor[shapeIndex] = c;
			System.out.println("Shape stored in: "+shapeIndex);
			
			// pen.drawRect(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1),
			// Math.abs(y1 - y2));
			System.out.println("rect objec create complete..." + "shapeIndex is: " + (shapeIndex -1) + " "
					+ shapes[shapeIndex - 1].toString());
		} else if (command.equals("Circle")) {
			// pen.drawOval(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1),
			// Math.abs(x2 - x1));
			System.out.println("Drawing circle...");
		} else if (command.equals("Oval")) {
			// pen.drawOval(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1),
			// Math.abs(y1 - y2));
			System.out.println("Drawing oval...");
		}
		repaint();
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
		x = e.getX();
		y = e.getY();
		gui.xJLabel.setText("x -> " + x);
		gui.yJLabel.setText("y -> " + y);
		System.out.println("Mouse dragged...");
		if (command.equals("Free draw")) {
			// pen.drawLine(x1, y1, x, y);
			x1 = x;
			y1 = y;
			System.out.println("Free drawing...");
		} else if (command.equals("Erase")) {
			x = e.getX();
			y = e.getY();
			pen.setColor(Color.WHITE);
			pen.setStroke(new BasicStroke(15));
			// pen.drawLine(x1, y1, x, y);
			x1 = x;
			y1 = y;
			System.out.println("Erase using...");
		} else if (command.equals("Rectangle")) {
			Rectangle2D rect = new Rectangle2D.Float(Math.min(x, x1), Math.min(y, y1), Math.abs(x - x1),
					Math.abs(y1 - y));
			shapes[shapeIndex] = rect;
			shapesColor[shapeIndex] = gui.getPenColor();
			
			// pen.drawRect(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1),
			// Math.abs(y1 - y2));
			System.out.println("Drawing rect...");
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
