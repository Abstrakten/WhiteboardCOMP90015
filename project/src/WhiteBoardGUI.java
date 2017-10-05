
/**
 * @author Xin Qi
 * @version 1.4
 * 
 * @comment this class is used to initialize the main gui of the program, the new session method(at line 436) still need some changes.
 * 						this class will pass the users list which is accept from server to drawboard class to achieve some drawing operation.
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.Graphics2D;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

// this class contains the GUI of the white board, there has a main method at end, to test this class.
// using swing and awt extends JFrame.
// hi Marc the method sendMessage needs you! Pull the scroll bar in the end then you can see it.
public class WhiteBoardGUI extends JFrame {

	private String username, ip, port;
	private Boolean isHost = false;
	private List<User> users = new ArrayList<>();
	private User user;
	private Color penColor;
	private BasicStroke penStroke;
	private JRadioButton colorButton1 = new JRadioButton();
	private ButtonGroup funcBG, colorBG;
	public JLabel xJLabel = new JLabel("0");
	public JLabel yJLabel = new JLabel("0");
	private JPanel drawBoard;
	private static JTabbedPane tab;
	private boolean newSessionAvaliable = false;
    public JRadioButton funcButtonClr,funcButtonUndo,funcButtonRedo;
	

	

	// frame class constructor.
	// initialize the frame. include frame title, size, location, and minimum size.
	// the arguments are from InputAddrWindow class, called in the method connectBT
	// actionPerformed.
	public WhiteBoardGUI(List<User> users) {

		super();
		this.users = users;
		for(User user:users) {
			try {
				
				if (user.getIp().equals(InetAddress.getLocalHost().getHostAddress())) {
					this.user = user;
					System.out.println(this.user.toString());
					
				}
			} catch (UnknownHostException e) {
				
				e.printStackTrace();
				
			}
		}
		
		this.username = this.user.getUsername();
		this.ip = this.user.getIp();
		this.isHost =this.user.getState();
		this.port = this.user.getPort();

		String titleHost = "Client";

		if (isHost) {
			
			titleHost = "Host";
			
		}

		this.setTitle("WhiteBoard 1.4" + " " + titleHost + " " + username);
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

		JPanel rightMainPanel = new JPanel();
		rightMainPanel.setLayout(new BorderLayout());

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		
		tab = new JTabbedPane();

		JPanel colorAndFunc = colorAndFunc();
		JPanel chatWindow = chatWindow();
		JPanel colorAndStroke = colorAndStroke();
		JMenuBar jMenuBar = menuBar();

		setDrawBoard(new DrawBoard(funcBG, colorBG, this, this.users));
		/*this.drawBoard.add(xJLabel);
		this.drawBoard.add(yJLabel);*/

		rightMainPanel.add(chatWindow, BorderLayout.WEST);
		southPanel.add(colorAndFunc, BorderLayout.CENTER);
		southPanel.add(colorAndStroke, BorderLayout.EAST);
		leftMainPanel.add(drawBoard, BorderLayout.CENTER);
		leftMainPanel.add(southPanel, BorderLayout.SOUTH);
		mainPanel.add(rightMainPanel, BorderLayout.EAST);
		mainPanel.add(leftMainPanel, BorderLayout.CENTER);

		//tab.addTab("Untitled", mainPanel);
		setPenStroke(new BasicStroke(1));
		this.setJMenuBar(jMenuBar);
		this.add(mainPanel);
		this.setVisible(true);
		this.validate();

	}

	private JPanel colorAndStroke() {
		JPanel cAndS = new JPanel();
		cAndS.setLayout(new BorderLayout());
		cAndS.setBackground(Color.GRAY);
		JButton color = new JButton("More Color");
		JButton stroke = new JButton("Choose Stroke");
		color.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Color c = getPenColor();
				c = JColorChooser.showDialog(getParent(), "White Board1.4 ColorChooser", getPenColor());
				setPenColor(c);
				colorButton1.setBackground(c);
				colorButton1.setSelected(true);

			}
		});
		cAndS.add(color, BorderLayout.NORTH);
		stroke.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame jf = new JFrame("WhiteBoard1.4 StrokeChooser");

				jf.setSize(300, 100);
				jf.setResizable(false);
				jf.setBackground(Color.GRAY);
				jf.setLayout(new BorderLayout());
				jf.setLocationRelativeTo(null);
				JPanel size = new JPanel();
				size.setLayout(new FlowLayout());
				// size.setBackground(Color.GRAY);;
				jf.add(size, BorderLayout.CENTER);

				int i = 0;
				String[] sizeArray = { "2", "4", "6", "8", "10" };
				ButtonGroup sizeBG = new ButtonGroup();
				JRadioButton sizeBTDefault = new JRadioButton();
				JLabel sizeLabel1 = new JLabel("1");
				sizeBTDefault.setActionCommand("1");
				sizeBTDefault.setSelected(true);
				sizeBG.add(sizeBTDefault);
				size.add(sizeBTDefault);
				size.add(sizeLabel1);
				while (i < 5) {
					JRadioButton sizeBT = new JRadioButton();
					JLabel sizeLabel = new JLabel(sizeArray[i]);
					sizeBT.setActionCommand(sizeArray[i]);
					sizeBG.add(sizeBT);
					size.add(sizeBT);
					size.add(sizeLabel);
					i++;
				}
				JButton strokeConfirm = new JButton("Confirm");
				JButton strokeCancel = new JButton("Cancel");
				strokeConfirm.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int i = Integer.parseInt(sizeBG.getSelection().getActionCommand());
						setPenStroke(new BasicStroke((float) i));
						jf.setVisible(false);
						jf.dispose();
						System.out.println("Stroke is: " + getPenStroke());
					}
				});

				strokeCancel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						jf.setVisible(false);
						jf.dispose();
						System.out.println("Stroke is: " + getPenStroke());
					}
				});
				JPanel confrimAndCancel = new JPanel();
				confrimAndCancel.setLayout(new FlowLayout());
				confrimAndCancel.add(strokeConfirm);
				confrimAndCancel.add(strokeCancel);
				jf.add(confrimAndCancel, BorderLayout.SOUTH);
				jf.setVisible(true);
			}
		});
		cAndS.add(stroke, BorderLayout.SOUTH);
		return cAndS;
	}

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
		String funcBT[] = { "Line", "Circle", "Rectangle", "Oval", "Erase", "Text", "Choose" };

		for (int i = 0; i < 7; i++) {
            ImageIcon img = new ImageIcon(this.getClass().getResource("icons/"+funcBT[i]+".png"));
			JRadioButton funcButton = new JRadioButton(img);
            ImageIcon img2 = new ImageIcon(this.getClass().getResource("icons/"+funcBT[i]+"2.png"));
            funcButton.setSelectedIcon(img2);
			funcButton.setActionCommand(funcBT[i]);
			funcBG.add(funcButton);
			functionP.add(funcButton);
		}

		// define default button.
        ImageIcon img = new ImageIcon(this.getClass().getResource("icons/Draw.png"));
		JRadioButton funcButton1 = new JRadioButton(img, true);
        ImageIcon img2 = new ImageIcon(this.getClass().getResource("icons/Draw2.png"));
        funcButton1.setSelectedIcon(img2);
		funcButton1.setActionCommand("Free draw");
		funcBG.add(funcButton1);
		functionP.add(funcButton1);
        funcButtonClr = new JRadioButton("");
        funcButtonClr.setActionCommand("Clear");
        funcBG.add(funcButtonClr);
        funcButtonUndo = new JRadioButton("");
        funcButtonUndo.setActionCommand("Undo");
        funcBG.add(funcButtonUndo);
        funcButtonRedo = new JRadioButton("");
        funcButtonRedo.setActionCommand("Redo");
        funcBG.add(funcButtonRedo);

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
					System.out.println("color chosen " + "R:" + getPenColor().getRed() + " G:"
							+ getPenColor().getGreen() + " B:" + getPenColor().getBlue());
				}
			});
			colorBG.add(colorButton);
			colorP.add(colorButton);
		}

		// set one more default selected color button as black. then pack it into color
		// panel and color button group.

		colorButton1.setOpaque(true);
		colorButton1.setFocusPainted(false);
		colorButton1.setBackground(Color.BLACK);
		colorButton1.setPreferredSize(new Dimension(30, 30));
		colorBG.add(colorButton1);
		colorP.add(colorButton1);
		colorButton1.setSelected(true);
		colorButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setPenColor(colorButton1.getBackground());
				System.out.println("color chosen");
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
		JMenuItem saveMenu = new JMenuItem("Save");
		JMenuItem saveAsMenu = new JMenuItem("Export to Image");
		JMenuItem closeMenu = new JMenuItem("Close");
		JMenuItem about = new JMenuItem("About");
		fileMenu.add(newSession);
		fileMenu.add(openMenu);
		fileMenu.add(saveMenu);
		fileMenu.add(saveAsMenu);
		fileMenu.add(closeMenu);
		fileMenu.add(about);
        JMenu editMenu = new JMenu("Edit");
        JMenuItem undoOption = new JMenuItem("Undo");
        undoOption.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        JMenuItem redoOption = new JMenuItem("Redo");
        redoOption.setAccelerator(KeyStroke.getKeyStroke('Y', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        JMenuItem clrOption = new JMenuItem("Erase Board");
        clrOption.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        editMenu.add(undoOption);
        editMenu.add(redoOption);
        editMenu.add(clrOption);
		JMenuBar jMenuBar = new JMenuBar();
		jMenuBar.add(fileMenu);
        jMenuBar.add(editMenu);
        
		saveMenu.addActionListener(e->{
			FileUtil.save(this.users);
		});
		
		openMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				newSessionAvaliable = true;
				List<User> newUsers = FileUtil.load();
				WhiteBoardGUI gui = new WhiteBoardGUI(newUsers);
				gui.initOperationInterface();
				
			}
		});
        
        closeMenu.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e){
                //code to close window
            }
            
        });
		
		newSession.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				WelcomeWindow welcomeWindow = new WelcomeWindow();
				welcomeWindow.createOrJoin();
			}
		});
		
		saveAsMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FileUtil.saveAs(users, getDrawBoard());
			}
		});
        
        about.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = "WhiteBoard 1.4 is a shared whiteboard application that allows users \nto draw simultaneously, as well as chat with other users on the board.\nThe application supports shapes (line,circle,oval,rectangle,as well as \nfree draw and text. It also allows the users to export the whiteboard \nto an image file(JPG).";
                String credit = "Developed by \nKasper, Marc, Navnita and Xin \n(Assignment 2 - COMP90015)";
                JOptionPane.showMessageDialog(null, msg+"\n\n"+credit, "About WhiteBoard 1.4", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        undoOption.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                funcButtonUndo.setSelected(true);
                drawBoard.repaint();
                System.out.println("Undo...");
            }
        });

        redoOption.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                funcButtonRedo.setSelected(true);
                drawBoard.repaint();
                System.out.println("Redo...");
            }
        });
        
        clrOption.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to erase the board?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    funcButtonClr.setSelected(true);
                    drawBoard.repaint();
                    System.out.println("Clearing board...");
                }
            }
        });
        
		return jMenuBar;

	}

	// This is the online list
	private JPanel onlineList() {
		
		// TODO onlineList may need some method in user class.
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
//	public static void main(String[] args) {
//		List<User> users1 = new ArrayList<>();
//		User xin;
//		try {
//			xin = new User(InetAddress.getLocalHost().getHostAddress(), "Port", "Xin", true);
//			users1.add(xin);
//			WhiteBoardGUI test = new WhiteBoardGUI(users1);
//			test.initOperationInterface();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

	/**
	 * @return the penColor
	 */
	public Color getPenColor() {
		return penColor;
	}

	/**
	 * @param penColor
	 *            the penColor to set
	 */
	public void setPenColor(Color penColor) {
		this.penColor = penColor;
	}

	public BasicStroke getPenStroke() {
		return penStroke;
	}

	public void setPenStroke(BasicStroke stroke) {
		this.penStroke = stroke;
	}

	public JPanel getDrawBoard() {
		return drawBoard;
	}

	public void setDrawBoard(JPanel drawBoard) {
		this.drawBoard = drawBoard;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public boolean isNewSessionAvaliable() {
		return newSessionAvaliable;
	}

	public void setNewSessionAvaliable(boolean newSessionAvaliable) {
		this.newSessionAvaliable = newSessionAvaliable;
	}
	
}
