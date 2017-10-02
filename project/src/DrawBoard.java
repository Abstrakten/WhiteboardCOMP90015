import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;

public class DrawBoard extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public ButtonGroup funcBG, colorBG;
	public Graphics2D pen;
	public WhiteBoardGUI gui;
	public static float x, y, x1, y1, x2, y2;
	private String command;
	public static List<ColoredShape> shapes = new ArrayList<>();
    private ColoredShape TempColoredShape;

    public DrawBoard(ButtonGroup funcBG, ButtonGroup colorBG, WhiteBoardGUI whiteBoardGUI) {

        this.TempColoredShape = new ColoredShape(new Line2D.Float(),Color.white,new BasicStroke(2));
		this.colorBG = colorBG;
		this.funcBG = funcBG;
		this.gui = whiteBoardGUI;
		this.setLayout(new FlowLayout());
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(700, 620));
		repaint();
		this.pen = (Graphics2D) gui.getGraphics();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
		System.out.println("draw board initial complete...");
	}

	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

        for (ColoredShape s : shapes) {
            g2d.setStroke(s.stroke);
            g2d.setColor(s.color);
            g2d.draw(s.shape);

            // TODO check what this does
            this.validate();

        }

        g2d.setStroke(TempColoredShape.stroke);
        g2d.setColor(TempColoredShape.color);
        g2d.draw(TempColoredShape.shape);
	}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) {
		this.command = this.funcBG.getSelection().getActionCommand();
		x1 = e.getX();
		y1 = e.getY();
		System.out.println("Mouse pressed...");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
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


            // TODO get stroke from gui
			shapes.add(new ColoredShape(rect,gui.getPenColor(),new BasicStroke(2)));
			
			// pen.drawRect(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1),
			// Math.abs(y1 - y2));
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
            TempColoredShape.shape = rect;
			TempColoredShape.color = gui.getPenColor();
			// TODO get stroke from gui
			TempColoredShape.stroke = new BasicStroke(2);
			
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

    private class ColoredShape {
        private BasicStroke stroke;
        private Shape shape;
        private Color color;

        public ColoredShape(Shape s, Color c, BasicStroke stroke) {
            this.shape = s;
            this.color = c;
            this.stroke = stroke;
        }
    }
}
