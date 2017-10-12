
/**
 * @author Xin Qi
 * @version 1.4
 * 
 * @comment this class accept a user list from gui class, and will add the shapes and strings to
 * 					this user on this.userlist, so if any other method need the list, just use getUsers and setUsers.
 * 					the users are identified by ip address. 
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DrawBoard extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public ButtonGroup funcBG, colorBG;
	public Graphics2D pen;
	public WhiteBoardGUI gui;
	public static float x, y, x1, y1, x2, y2, cx, cy;
	private String command;
	//public static List<ColoredShape> shapes = new ArrayList<>();
	private ColoredShape TempColoredShape;
	private InputString TempString;
	//private static List<InputString> strings = new ArrayList<>();
	private User user;
	private List<User> users = new ArrayList<>();

	public DrawBoard(ButtonGroup funcBG, ButtonGroup colorBG, WhiteBoardGUI whiteBoardGUI) {

		this.TempColoredShape = new ColoredShape(new Line2D.Float(), Color.white, new BasicStroke(2));
		this.TempString = new InputString("", Color.WHITE, 0, 0);
		this.colorBG = colorBG;
		this.funcBG = funcBG;
		this.gui = whiteBoardGUI;
		this.setLayout(new FlowLayout());
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(700, 620));

		// TODO Not sure what we are doing here, but it does not seem like a thing that should be in drawboard
/*		this.users = users;
		for (User user : this.users) {
			try {
				if (user.getIp().equals(InetAddress.getLocalHost().getHostAddress())) {
					this.user = user;
					System.out.println(this.user.toString());
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}*/
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
		for (User u : this.users) {
			for (InputString i : u.getStrings()) {
				g2d.setColor(i.getColor());
				g2d.drawString(i.getString(), i.getX(), i.getY());
			}

			for (ColoredShape s : u.getShapes()) {

				g2d.setStroke(s.getStroke());
				g2d.setColor(s.getColor());
				g2d.draw(s.getShape());

			}
		}
		// if(command.equals("Choose")) {
		// AffineTransform transform = new AffineTransform();
		// transform.translate(x-x1, y-x1);
		// g2d.translate(x-x1,x-x1);
		// x1 = x;
		// y1 = y;
		// }
		g2d.setStroke(TempColoredShape.getStroke());
        g2d.setColor(TempColoredShape.getColor());
		g2d.draw(TempColoredShape.getShape());
		g2d.setColor(TempString.getColor());
		g2d.drawString(TempString.getString(), TempString.getX(), TempString.getY());
      
        if (command.equals("Clear")){
            g.fillRect(0,0,900,620);
            user.delAll();
        } else if (command.equals("Undo")){
            user.undoShape();
        }else if (command.equals("Redo")){
            user.redoShape();
        }
        
		this.validate();
        
	}

	@Override
	public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {

		this.command = this.funcBG.getSelection().getActionCommand();
		x1 = e.getX();
		y1 = e.getY();
		System.out.println("Mouse pressed...");

		if (command.equals("Choose")) {
			for (InputString i : user.getStrings()) {

				if ((x1 < i.getX() + 10 && x1 > i.getX() - 10) && (y1 < i.getY() + 10 && y1 > i.getY() - 10)) {
					System.out.println("get graphic");
					TempString.setString(i.getString());
					TempString.setColor(Color.RED);

				}
			}
			
			for (ColoredShape s : user.getShapes()) {

				float shapeX = 0;
				float shapeY = 0;
				shapeX = (float) s.getShape().getBounds2D().getX();
				shapeY = (float) s.getShape().getBounds2D().getY();

				if ((x1 < shapeX + 10 && x1 > shapeX - 10) && (y1 < shapeY + 10 && y1 > shapeY - 10)) {

					System.out.println("get graphic");
					TempColoredShape.setShape(s.getShape());
					TempColoredShape.setColor(Color.RED);
					TempColoredShape.setStroke(s.getStroke());

				}
			}
			
		}
        repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.command = this.funcBG.getSelection().getActionCommand();
		x2 = e.getX();
		y2 = e.getY();
		System.out.println("Mouse released...");
		System.out.println("command is: " + command);
		if (command.equals("Line")) {

			Line2D line2d = new Line2D.Float(x1, y1, x2, y2);
			user.addShape(new ColoredShape(line2d, gui.getPenColor(), gui.getPenStroke()));
			System.out.println("Drawing line...");

		} else if (command.equals("Rectangle")) {

			Rectangle2D rect = new Rectangle2D.Float(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1),
					Math.abs(y1 - y2));
			user.addShape(new ColoredShape(rect, gui.getPenColor(), gui.getPenStroke()));
			System.out.println("Drawing rect...");

		} else if (command.equals("Circle")) {

			Ellipse2D circle = new Ellipse2D.Float(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1),
					Math.abs(x2 - x1));
			user.addShape(new ColoredShape(circle, gui.getPenColor(), gui.getPenStroke()));
			System.out.println("Drawing circle...");

		} else if (command.equals("Oval")) {

			Ellipse2D oval = new Ellipse2D.Float(Math.min(x2, x1), Math.min(y2, y1), Math.abs(x2 - x1),
					Math.abs(y1 - y2));
			user.addShape(new ColoredShape(oval, gui.getPenColor(), gui.getPenStroke()));
			System.out.println("Drawing oval...");

		} else if (command.equals("Text")) {
			JFrame stringInput = new JFrame("White Board Text");
			stringInput.setLocationRelativeTo(null);
			stringInput.setSize(300, 150);
			stringInput.setResizable(false);
			stringInput.setLayout(new BorderLayout());
			JLabel tips = new JLabel("What do you want to text?");
			JButton confirm = new JButton("Confirm");
			JButton cancel = new JButton("Cancel");
			JTextArea text = new JTextArea();
			text.setEditable(true);
			text.setRequestFocusEnabled(true);
			confirm.addActionListener(e1 -> {
                if (text.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(stringInput,
                            "Text can not be null, if you want cancel " + "\nthis operation, please click cancel.",
                            "whiteBoard", JOptionPane.INFORMATION_MESSAGE);

                } else {

                    String userText;
                    userText = text.getText();
                    user.addString(new InputString(userText, gui.getPenColor(), x1, y1));
                    stringInput.dispose();
                    repaint();

                }
            });
			cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					stringInput.dispose();
				}
			});

			JPanel buttons = new JPanel();
			buttons.setLayout(new FlowLayout());
			buttons.add(confirm);
			buttons.add(cancel);
			stringInput.add(tips, BorderLayout.NORTH);
			stringInput.add(text, BorderLayout.CENTER);
			stringInput.add(buttons, BorderLayout.SOUTH);
			stringInput.setVisible(true);

		} else if (command.equals("Choose")) {

			for (InputString i : user.getStrings()) {

				if ((x1 < i.getX() + 10 && x1 > i.getX() - 10) && (y1 < i.getY() + 10 && y1 > i.getY() - 10)) {

					System.out.println("get graphics.");
					i.setX(x2);
					i.setY(y2);
					System.out.println("changed succeed.");

				}
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

		}
        
		TempColoredShape = new ColoredShape(new Line2D.Float(), Color.white, new BasicStroke(2));
		TempString = new InputString("", Color.WHITE, 0, 0);
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
		this.command = this.funcBG.getSelection().getActionCommand();
		x = e.getX();
		y = e.getY();
		gui.xJLabel.setText("x -> " + x);
		gui.yJLabel.setText("y -> " + y);
		System.out.println("Mouse dragged...");
		if (command.equals("Free draw")) {

			Line2D line2d = new Line2D.Float(x1, y1, x, y);
			user.addShape(new ColoredShape(line2d, gui.getPenColor(), gui.getPenStroke()));
			x1 = x;
			y1 = y;
			System.out.println("Free drawing...");

		} else if (command.equals("Erase")) {

			Line2D line2d = new Line2D.Float(x1, y1, x, y);
			user.addShape(new ColoredShape(line2d, Color.WHITE, gui.getPenStroke()));
			x1 = x;
			y1 = y;
			System.out.println("Erase using...");

		} else if (command.equals("Rectangle")) {

			Rectangle2D rect = new Rectangle2D.Float(Math.min(x, x1), Math.min(y, y1), Math.abs(x - x1),
					Math.abs(y1 - y));
			TempColoredShape.setShape(rect);
			TempColoredShape.setColor(gui.getPenColor());
			TempColoredShape.setStroke(gui.getPenStroke());
			System.out.println("Drawing rect...");

		} else if (command.equals("Circle")) {

			Ellipse2D circle = new Ellipse2D.Float(Math.min(x, x1), Math.min(y, y1), Math.abs(x - x1),
					Math.abs(x - x1));
			TempColoredShape.setShape(circle);
			TempColoredShape.setColor(gui.getPenColor());
			TempColoredShape.setStroke(gui.getPenStroke());
			System.out.println("Drawing circle...");

		} else if (command.equals("Oval")) {

			Ellipse2D oval = new Ellipse2D.Float(Math.min(x, x1), Math.min(y, y1), Math.abs(x - x1), Math.abs(y1 - y));
			TempColoredShape.setShape(oval);
			TempColoredShape.setColor(gui.getPenColor());
			TempColoredShape.setStroke(gui.getPenStroke());
			System.out.println("Drawing oval...");

		} else if (command.equals("Line")) {

			Line2D line = new Line2D.Float(x1, y1, x, y);
			TempColoredShape.setShape(line);
			TempColoredShape.setColor(gui.getPenColor());
			TempColoredShape.setStroke(gui.getPenStroke());
			System.out.println("Drawing line...");

		} else if (command.equals("Choose")) {

			TempString.setX(x);
			TempString.setY(y);
			System.out.println(TempColoredShape);
			float h = (float) TempColoredShape.getShape().getBounds2D().getHeight();
			float w = (float) TempColoredShape.getShape().getBounds2D().getWidth();
			TempColoredShape.getShape().getBounds2D().setRect(x, y, w, h);
			System.out.println(TempColoredShape);

        }
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}
