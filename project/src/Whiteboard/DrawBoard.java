package Whiteboard;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DrawBoard extends JPanel implements MouseListener, MouseMotionListener {

	public ButtonGroup funcBG, colorBG;
	public WhiteBoardGUI gui;
	public int x, y, x1, y1, x2, y2;
	private String command;
	public List<ColoredShape> shapes;
	private ColoredShape TempColoredShape;
	private List<ColoredShape> redoShapes;
	private List<ColoredShape> sendShapesList;

    public DrawBoard(ButtonGroup funcBG, ButtonGroup colorBG, WhiteBoardGUI whiteBoardGUI) {

	    shapes = new Stack<>();
	    redoShapes = new Stack<>();
	    sendShapesList = new ArrayList<>();
		this.TempColoredShape = new ColoredShape(new Line2D.Float(), Color.white, new BasicStroke(2));
		this.colorBG = colorBG;
		this.funcBG = funcBG;
		this.gui = whiteBoardGUI;

		// TODO check if this layout stuff is necessary
		this.setLayout(new FlowLayout());
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(700, 620));

		repaint();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		System.out.println("draw board initial complete...");
	}

	@Override
	public void paintComponent(Graphics g) {
		this.command = this.funcBG.getSelection().getActionCommand();
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

        if (command.equals("Clear")) {
            g.fillRect(0, 0, 900, 620);
            shapes.clear();
        }

		for (ColoredShape cs : this.shapes) {
		    if(cs.IsText()){
		        // TODO fix and add custom fonts
		        g2d.setColor(cs.getColor());
                g2d.drawString(cs.getText(), cs.getX(), cs.getY());
            } else {
                g2d.setStroke(cs.getStroke());
                g2d.setColor(cs.getColor());
                g2d.draw(cs.getShape());
            }
        }
        if (TempColoredShape.IsText()) {
            g2d.setColor(TempColoredShape.getColor());
            g2d.drawString(TempColoredShape.getText(), TempColoredShape.getX(), TempColoredShape.getY());
        } else {
            g2d.setStroke(TempColoredShape.getStroke());
            g2d.setColor(TempColoredShape.getColor());
            g2d.draw(TempColoredShape.getShape());
        }
        
		this.validate();
	}

	@Override
	public void mousePressed(MouseEvent e) {

		this.command = this.funcBG.getSelection().getActionCommand();
		x1 = e.getX();
		y1 = e.getY();
		System.out.println("Mouse pressed...");

		if (command.equals("Choose")) {
		    for (ColoredShape s : shapes) {
                if ((x1 < s.getX() + 10 && x1 > s.getX() - 10) && (y1 < s.getY() + 10 && y1 > s.getY() - 10)){
                    TempColoredShape = s;
                    break;
                }
            }
		}
        repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
        redoShapes.clear();
		command = this.funcBG.getSelection().getActionCommand();
		x2 = e.getX();
		y2 = e.getY();
		System.out.println("Mouse released...");
		System.out.println("command is: " + command);
        switch (command) {
            case "Line":
                Line2D line2d = new Line2D.Float(x1, y1, x2, y2);
                shapes.add(new ColoredShape(line2d, gui.getPenColor(), gui.getPenStroke()));
                System.out.println("Drawing line...");
                break;

            case "Rectangle":
                Rectangle2D rect = new Rectangle2D.Float(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1),
                        Math.abs(y1 - y2));
                shapes.add(new ColoredShape(rect, gui.getPenColor(), gui.getPenStroke()));
                System.out.println("Drawing rect...");
                break;

            case "Circle":
                Ellipse2D circle = new Ellipse2D.Float(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1),
                        Math.abs(x2 - x1));
                shapes.add(new ColoredShape(circle, gui.getPenColor(), gui.getPenStroke()));
                System.out.println("Drawing circle...");
                break;

            case "Oval":
                Ellipse2D oval = new Ellipse2D.Float(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1),
                        Math.abs(y1 - y2));
                shapes.add(new ColoredShape(oval, gui.getPenColor(), gui.getPenStroke()));
                System.out.println("Drawing oval...");
                break;

            case "Text":
                JFrame stringInput = new JFrame("Whiteboard Text");
                stringInput.setLocationRelativeTo(null);
                stringInput.setSize(300, 150);
                stringInput.setResizable(false);
                stringInput.setLayout(new BorderLayout());
                JLabel tips = new JLabel("What do you want to write?");
                JButton confirm = new JButton("Confirm");
                JButton cancel = new JButton("Cancel");
                JTextArea text = new JTextArea();
                text.setEditable(true);
                text.setRequestFocusEnabled(true);
                confirm.addActionListener(e1 -> {
                    if (text.getText().trim().isEmpty()) {
                        // TODO if text is empty, maybe just consider it a cancel? or ignore?
                        JOptionPane.showMessageDialog(stringInput,
                                "Text can not be null",
                                "whiteboard", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        shapes.add(new ColoredShape(text.getText(), gui.getPenColor(), x1, y1));
                        stringInput.dispose();
                        repaint();
                    }
                });
                cancel.addActionListener(e1 -> stringInput.dispose());

                JPanel buttons = new JPanel();
                buttons.setLayout(new FlowLayout());
                buttons.add(confirm);
                buttons.add(cancel);
                stringInput.add(tips, BorderLayout.NORTH);
                stringInput.add(text, BorderLayout.CENTER);
                stringInput.add(buttons, BorderLayout.SOUTH);
                stringInput.setVisible(true);
                break;

            case "Choose":
                if (TempColoredShape.IsText()){
                    TempColoredShape.setX(x2);
                    TempColoredShape.setY(y2);
                } else {

                }

                // TODO the shapes can be chosen but can not be moved, to move it ,the method below may need a little change.
                // but i don't know how. In mouse pressed method, the shape is already chosen.
//			for (ColoredShape s : user.getShapes()) {
//
//				float shapeX = 0;
//				float shapeY = 0;
//				float shapeHeight = 0;
//				float shapeWeight = 0;
//				shapeX = (float) s.getShape().getBounds2D().getX();
//				shapeY = (float) s.getShape().getBounds2D().getY();
//
//				if ((x1 < shapeX + 10 && x1 > shapeX - 10) && (y1 < shapeY + 10 && y1 > shapeY - 10)) {
//
//					System.out.println("get graphic");
//					shapeHeight = (float) s.getShape().getBounds2D().getHeight();
//					shapeWeight = (float) s.getShape().getBounds2D().getWidth();
//					s.getShape().getBounds2D().setRect(x2, y2, shapeWeight, shapeHeight);
//
//				}
//			}
                break;
        }
        
		TempColoredShape = new ColoredShape(new Line2D.Float(), Color.white, new BasicStroke(2));
		repaint();

		if(!(command.equals("Free draw") || command.equals("Erase"))) {
		    sendShapesList.add(shapes.get(shapes.size()-1));
        }
		WhiteBoardGUI.drawOnServer(sendShapesList);
		sendShapesList.clear();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.command = this.funcBG.getSelection().getActionCommand();
		x = e.getX();
		y = e.getY();
		gui.xJLabel.setText("x -> " + x);
		gui.yJLabel.setText("y -> " + y);

		System.out.println("Mouse dragged...");
        switch (command) {
            case "Free draw":
                Line2D freeDrawLine = new Line2D.Float(x1, y1, x, y);
                shapes.add(new ColoredShape(freeDrawLine, gui.getPenColor(), gui.getPenStroke()));
                sendShapesList.add(new ColoredShape(freeDrawLine, gui.getPenColor(), gui.getPenStroke()));
                x1 = x;
                y1 = y;
                System.out.println("Free drawing...");
                break;

            case "Erase":
                Line2D erase = new Line2D.Float(x1, y1, x, y);
                shapes.add(new ColoredShape(erase, Color.WHITE, gui.getPenStroke()));
                sendShapesList.add(new ColoredShape(erase, Color.WHITE, gui.getPenStroke()));
                x1 = x;
                y1 = y;
                System.out.println("Erase using...");
                break;

            case "Rectangle":
                Rectangle2D rect = new Rectangle2D.Float(Math.min(x, x1), Math.min(y, y1), Math.abs(x - x1),
                        Math.abs(y1 - y));
                TempColoredShape.setShape(rect);
                TempColoredShape.setColor(gui.getPenColor());
                TempColoredShape.setStroke(gui.getPenStroke());
                System.out.println("Drawing rect...");
                break;

            case "Circle":
                Ellipse2D circle = new Ellipse2D.Float(Math.min(x, x1), Math.min(y, y1), Math.abs(x - x1),
                        Math.abs(x - x1));
                TempColoredShape.setShape(circle);
                TempColoredShape.setColor(gui.getPenColor());
                TempColoredShape.setStroke(gui.getPenStroke());
                System.out.println("Drawing circle...");
                break;

            case "Oval":
                Ellipse2D oval = new Ellipse2D.Float(Math.min(x, x1), Math.min(y, y1), Math.abs(x - x1), Math.abs(y1 - y));
                TempColoredShape.setShape(oval);
                TempColoredShape.setColor(gui.getPenColor());
                TempColoredShape.setStroke(gui.getPenStroke());
                System.out.println("Drawing oval...");
                break;

            case "Line":
                Line2D line = new Line2D.Float(x1, y1, x, y);
                TempColoredShape.setShape(line);
                TempColoredShape.setColor(gui.getPenColor());
                TempColoredShape.setStroke(gui.getPenStroke());
                System.out.println("Drawing line...");
                break;

            case "Choose":
                if (TempColoredShape.IsText()) {
                    TempColoredShape.setX(x);
                    TempColoredShape.setY(y);
                } else {
                    // TODO reconsider this logic
                    double h = TempColoredShape.getShape().getBounds2D().getHeight();
                    double w = TempColoredShape.getShape().getBounds2D().getWidth();
                    TempColoredShape.getShape().getBounds2D().setRect(x, y, w, h);
                }
                break;
        }
		repaint();
	}

	public void Undo () {
	    if (!shapes.isEmpty()) {
	        // TODO Free draw undo is currently not working well
            redoShapes.add(((Stack<ColoredShape>) shapes).pop());
        }
    }

    public void Redo () {
        if (!redoShapes.isEmpty()) {
            shapes.add(((Stack<ColoredShape>) redoShapes).pop());
        }
    }
    public void Erase() {
        if(!shapes.isEmpty()){
            shapes.clear();
        }
        if(!redoShapes.isEmpty()){
            redoShapes.clear();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseMoved(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

}
