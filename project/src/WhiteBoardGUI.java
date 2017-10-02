
/**
 * @author Xin Qi
 * @version 1.2
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Closeable;
import java.util.List;

import javax.sound.sampled.Line;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

// this class contains the GUI of the white board, there has a main method at end, to test this class.
// using swing and awt extends JFrame.
// hi Marc the method sendMessage needs you! Pull the scroll bar in the end then you can see it.
public class WhiteBoardGUI extends JFrame {

	private String username, ip, port;
	private Boolean isHost = false;
	private User[] onlineUser = new User[100];
	private User user;
	private Color penColor;
	private ButtonGroup funcBG, colorBG;
	public JLabel xJLabel = new JLabel("0");
	public JLabel yJLabel = new JLabel("0");

	// frame class constructor.
	// initialize the frame. include frame title, size, location, and minimum size.
	// the arguments are from InputAddrWindow class, called in the method connectBT
	// actionPerformed.
	public WhiteBoardGUI(User user) {

		super();
		this.user = user;
		this.username = user.getUsername();
		this.ip = user.getIp();
		this.isHost = user.getState();
		this.port = user.getPort();

		String titleHost = "Client";

		if (isHost) {
		    titleHost = "Host";
        }

		this.setTitle("WhiteBoard 1.3" + " " + titleHost + " " + username);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(1100, 700));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(960, 500));
		this.addWindowListener(new MyWindowListener(this));
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	
	// defining components of each component of the white board.
	// and adding these components to the frame, include a draw board at central,
	// a function panel (line, circle, free draw (default selected), erase, text and
	// so on) at bottom,
	// and a color panel include 16 colors beyond of the function panel, black is
	// default selected.
	public void initOperationInterface() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.BLACK);
		
		JPanel leftMainPanel = new JPanel();
		leftMainPanel.setLayout(new BorderLayout());
		
		JPanel rightMainPnale = new JPanel();
		rightMainPnale.setLayout(new BorderLayout());
		
//		JPanel drawBoard = new JPanel();
//		drawBoard = drawBoard();
//		drawBoard.setLayout(new FlowLayout());

		JPanel colorAndFunc = colorAndFunc();
		JPanel chatWindow = chatWindow();
		JMenuBar jMenuBar = menuBar();

		JPanel drawBoard = new DrawBoard(funcBG, colorBG, this);
		drawBoard.add(xJLabel);
		drawBoard.add(yJLabel);
		//drawBoard.addMouseListener(new DrawBoard(funcBG, colorBG, this));
		//drawBoard.addMouseMotionListener(new DrawBoard(funcBG, colorBG, this));
		
		rightMainPnale.add(chatWindow, BorderLayout.WEST);
		leftMainPanel.setLayout(new BorderLayout());
		mainPanel.add(rightMainPnale, BorderLayout.EAST);
		mainPanel.add(leftMainPanel, BorderLayout.CENTER);
		leftMainPanel.add(drawBoard, BorderLayout.CENTER);
		leftMainPanel.add(colorAndFunc, BorderLayout.SOUTH);

		this.setJMenuBar(jMenuBar);
		this.add(mainPanel);
		this.setVisible(true);
		this.validate();
	}

	// this method return the drawBoard Panel (the big white one), defining draw
	// board panel.
//	private JPanel drawBoard() {
//
//		JPanel drawBoard = new JPanel();
//		// drawBoard.setPreferredSize(new Dimension(700, 620));
//		drawBoard.setLayout(new BorderLayout());
//		drawBoard.setBackground(Color.WHITE);
//		return drawBoard;
//
//	}

	// This method return a panel for both color and function panel.
	private JPanel colorAndFunc() {

		// define a panel for both color and function panel.
		JPanel colorAndFunctionP = new JPanel();
		colorAndFunctionP.setLayout(new BorderLayout());
		colorAndFunctionP.setBackground(Color.GRAY);
		// colorAndFunctionP.setPreferredSize(new Dimension(700, 80));

		// define function panel and functional button group.
		JPanel functionP = new JPanel();
		funcBG = new ButtonGroup();

		// defining the color panel and button group in the same way.
		JPanel colorP = new JPanel();
		colorBG = new ButtonGroup();

		// set properties of function panel.and add it into colorAndFunction panel.
		functionP.setBackground(Color.GRAY);
		functionP.setLayout(new FlowLayout());
		// functionP.setPreferredSize(new Dimension(700, 40));

		// set properties of color panel. and add it into colorAndFunction panel.
		colorP.setBackground(Color.GRAY);
		colorP.setLayout(new FlowLayout());
		// colorP.setPreferredSize(new Dimension(700, 40));

		// define each functional button as a JRadioButton, which means in the same
		// group, only one button could be selected.
		String funcBT[] = {"Line","Circle","Rectangle","Oval","Erase","Text"};
		
		for (int i = 0; i < 6; i++) {
			JRadioButton funcButton = new JRadioButton(funcBT[i]);
			funcButton.setActionCommand(funcBT[i]);
			funcBG.add(funcButton);
			functionP.add(funcButton);
		}

		// define default button. 
		JRadioButton funcButton5 = new JRadioButton("Free draw", true);
		funcButton5.setActionCommand("Free draw");
		funcBG.add(funcButton5);
		functionP.add(funcButton5);

		// defining 15 colors in a color list.
		Color colors[] = { new Color(0, 30, 40), new Color(98, 14, 13), new Color(123, 14, 3), new Color(50, 60, 100),
				new Color(30, 120, 10), new Color(98, 37, 12), new Color(86, 4, 17), new Color(50, 60, 10),
				new Color(33, 53, 18), new Color(64, 12, 44), new Color(175, 200, 14), new Color(33, 23, 254),
				new Color(87, 99, 46), new Color(55, 145, 84), new Color(10, 75, 100) };

		// set back ground color of color buttons from color list, and pack them into
		// color panel and color group.
		for (int i = 0; i < 15; i++) {
			JRadioButton colorButton = new JRadioButton();
			colorButton.setOpaque(true);
			colorButton.setFocusPainted(false);
			colorButton.setBackground(colors[i]);
			colorButton.setPreferredSize(new Dimension(30, 30));
			colorButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					setPenColor(colorButton.getBackground());
					System.out.println("color chosed "+"R:"+getPenColor().getRed()+" G:"+getPenColor().getGreen()+" B:"+getPenColor().getBlue());
				}
			});
			colorBG.add(colorButton);
			colorP.add(colorButton);
		}

		// set one more default selected color button as black. then pack it into color
		// panel and color button group.
		JRadioButton colorButton = new JRadioButton();
		colorButton.setOpaque(true);
		colorButton.setFocusPainted(false);
		colorButton.setBackground(Color.BLACK);
		colorButton.setPreferredSize(new Dimension(30, 30));
		colorBG.add(colorButton);
		colorP.add(colorButton);
		colorButton.setSelected(true);
		colorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setPenColor(colorButton.getBackground());
				System.out.println("color chosed");
			}
		});

		// add function panel and color panel into color and function panel.
		colorAndFunctionP.add(functionP, BorderLayout.SOUTH);
		colorAndFunctionP.add(colorP, BorderLayout.NORTH);

		return colorAndFunctionP;

	}

	// this method return the chatWindow Panel, the right white one.
	private JPanel chatWindow() {

		// define properties of chat window panel.
		JPanel chatP = new JPanel();
		chatP.setLayout(new BorderLayout());
		chatP.setBackground(Color.GRAY);
		// chatP.setPreferredSize(new Dimension(400, 700));

		// define send bar, text screen and online user panel.
		JPanel sendingWindow = new JPanel(new FlowLayout());
		JPanel chatScreen = new JPanel(new BorderLayout());
		// ----> JPanel onlineUsers = new JPanel(new BorderLayout());

		// define text area and a label on screen.
		JTextArea screen = new JTextArea();
		JLabel screenLable = new JLabel("Chat History", JLabel.CENTER);

		// define and set a new scroll container to contain the text screen panel.
		JScrollPane js = new JScrollPane(screen);
		screen.setEditable(false);
		screen.setLineWrap(true);
		// js.setPreferredSize(new Dimension(330, 70));
		js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// set properties of sending text bar and action of send button.
		JTextField sendText = new JTextField(15);
		JButton sendJB = new JButton("Send");
		sendJB.setActionCommand("Send");
		sendJB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// push the newest received message to chat screen.
				screen.append(username + sendMessage(sendText.getText(), screen) + "\n");
				// reset sending text bar.
				sendText.setText("");
				// reset cursor.
				sendText.requestFocus();

			}
		});

		// construct sending window.
		sendingWindow.add(sendText);
		sendingWindow.add(sendJB);
		sendingWindow.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		// construct the chat panel.
		chatScreen.add(screenLable, BorderLayout.NORTH);
		chatScreen.add(js, BorderLayout.CENTER);
		chatScreen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		chatP.add(sendingWindow, BorderLayout.SOUTH);
		chatP.add(chatScreen, BorderLayout.CENTER);
		return chatP;

	}

	private JMenuBar menuBar() {

		JMenu fileMenu = new JMenu("File");
		JMenuItem newSession = new JMenuItem("New File");
		JMenuItem openMenu = new JMenuItem("Open");
		JMenuItem openNewTab = new JMenuItem("Open In New Tab");
		JMenuItem saveMenu = new JMenuItem("Save");
		JMenuItem saveAsMenu = new JMenuItem("Save As");
		JMenuItem closeMenu = new JMenuItem("Close");
		JMenuItem closeAllMenu = new JMenuItem("Close All");
		JMenuItem about = new JMenuItem("About");
		fileMenu.add(newSession);
		fileMenu.add(openMenu);
		fileMenu.add(openNewTab);
		fileMenu.add(saveMenu);
		fileMenu.add(saveAsMenu);
		fileMenu.add(closeMenu);
		fileMenu.add(closeAllMenu);
		fileMenu.add(about);
		JMenuBar jMenuBar = new JMenuBar();
		jMenuBar.add(fileMenu);

		return jMenuBar;

	}

	// This is the online list
	private JPanel onlineList() {
		JPopupMenu popupMenu = new JPopupMenu();
		JList list = new JList();
		JPanel mainPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(list);
		popupMenu.add("Kick Out");
		popupMenu.add("Clear Draws");
		popupMenu.add("User Info");
		list.setModel(new DefaultListModel<String>());

		return null;

	}

	public String sendMessage(String text, JTextArea screen
	// , String ip, String port, String username, String State//
	) {

		// TODO Auto-generated method stub

		// the method is called at 213 at this class (sendJB's actionPerformend method).

		// the username, state, IP and PORT in the arguments are local's.

		// the method below is to append text to text screen, something is the
		// information from other source.
		// the display format in parentheses below can changed as you wish.
		// screen.append(sourceUsername + "[" + sourceState + "]: " + sourceSay+ "\n");
		return text;

	}

	// testing method, the main method of GUI is in WelcomeWindow class.
	public static void main(String[] args) {
		User xin = new User("Ip", "Port", "Xin", true);
		WhiteBoardGUI test = new WhiteBoardGUI(xin);
		test.initOperationInterface();

	}


	/**
	 * @return the penColor
	 */
	public Color getPenColor() {
		return penColor;
	}


	/**
	 * @param penColor the penColor to set
	 */
	public void setPenColor(Color penColor) {
		this.penColor = penColor;
	}
}