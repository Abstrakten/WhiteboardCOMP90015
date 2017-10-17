package Whiteboard;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


// this class contains the GUI of the white board, there has a main method at end, to test this class.
// using swing and awt extends JFrame.
// hi Marc the method sendMessage needs you! Pull the scroll bar in the end then you can see it.
public class WhiteBoardGUI extends JFrame {
    
	public static User user;
	private Color penColor = Color.BLACK;
	public static ArrayList<User> userArrayList;
	private BasicStroke penStroke;
	private JRadioButton colorButton = new JRadioButton();
	private ButtonGroup funcBG, colorBG;
	public JPanel chatWindow;
	public static JTextArea screen;
	public JLabel xJLabel = new JLabel("0");
	public JLabel yJLabel = new JLabel("0");
	private static DrawBoard drawboard;
	private static JTabbedPane tab;
	private static JList userJlist;
	private boolean newSessionAvailable = false;
	private static JList<User> users;
    private static DefaultListModel<User> usersModel;
    private static JLabel userNum;

    // frame class constructor.
	// initialize the frame. include frame title, size, location, and minimum size.
	// the arguments are from InputAddrWindow class, called in the method connectBT
	// actionPerformed.
	public WhiteBoardGUI(User user) {
		super();
		this.user = user;
		users = new JList<>();
		User userTest = new User("123","123","123",false);
		usersModel = new DefaultListModel<>();
		usersModel.addElement(userTest);
		users.setModel(usersModel);
		// user list test-->



        // TODO figure out what this part does
/*        try {
            if (user.getIp().equals(InetAddress.getLocalHost().getHostAddress())) {
                this.user = user;
                System.out.println(this.user.toString());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/

		this.setTitle("Whiteboard: " + user.getUsername());
		this.setSize(new Dimension(1100, 700));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(960, 500));
		this.addWindowListener(new MyWindowListener(this));
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // defining components of each component of the white board.
        // and adding these components to the frame, include a draw board at central,
        // a function panel (line, circle, free draw (default selected), erase, text and
        // so on) at bottom,
        // and a color panel include 16 colors beyond of the function panel, black is
        // default selected.

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
        chatWindow = chatWindow();
        JPanel colorAndStroke = colorAndStroke();
        JMenuBar jMenuBar = menuBar(user);

        drawboard = new DrawBoard(funcBG, colorBG, this);

        User nh = new User(user.getIp(), user.getPort(), user.getUsername(), user.IsHost());
        try {
            this.user.chatClient.chatServer.registerUser(nh);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            this.user.chatClient.chatServer.broadcastUsers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        updateUserList(userArrayList);

        rightMainPanel.add(chatWindow, BorderLayout.WEST);
        southPanel.add(colorAndFunc, BorderLayout.CENTER);
        southPanel.add(colorAndStroke, BorderLayout.EAST);
        leftMainPanel.add(drawboard, BorderLayout.CENTER);
        leftMainPanel.add(southPanel, BorderLayout.SOUTH);
        mainPanel.add(rightMainPanel, BorderLayout.EAST);
        mainPanel.add(leftMainPanel, BorderLayout.CENTER);

        // tab.addTab("Untitled", mainPanel);
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
		color.addActionListener(e -> {
            penColor = JColorChooser.showDialog(getParent(), "White Board Colorpicker", getPenColor());
            colorButton.setBackground(penColor);
            colorButton.setSelected(true);
        });
		cAndS.add(color, BorderLayout.NORTH);
		stroke.addActionListener(e -> {
            JFrame jf = new JFrame("WhiteBoard Stroke Chooser");

            jf.setSize(300, 100);
            jf.setResizable(false);
            jf.setBackground(Color.GRAY);
            jf.setLayout(new BorderLayout());
            jf.setLocationRelativeTo(null);
            JPanel size = new JPanel();
            size.setLayout(new FlowLayout());
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
            strokeConfirm.addActionListener(e1 -> {
                int i1 = Integer.parseInt(sizeBG.getSelection().getActionCommand());
                setPenStroke(new BasicStroke((float) i1));
                jf.setVisible(false);
                jf.dispose();
                System.out.println("Stroke is: " + getPenStroke());
            });

            strokeCancel.addActionListener(e12 -> {
                jf.setVisible(false);
                jf.dispose();
                System.out.println("Stroke is: " + getPenStroke());
            });
            JPanel confirmAndCancel = new JPanel();
            confirmAndCancel.setLayout(new FlowLayout());
            confirmAndCancel.add(strokeConfirm);
            confirmAndCancel.add(strokeCancel);
            jf.add(confirmAndCancel, BorderLayout.SOUTH);
            jf.setVisible(true);
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
			ImageIcon img = new ImageIcon(this.getClass().getResource(funcBT[i] + ".png"));
			JRadioButton funcButton = new JRadioButton(img);
			ImageIcon img2 = new ImageIcon(this.getClass().getResource(funcBT[i] + "2.png"));
			funcButton.setSelectedIcon(img2);
			funcButton.setActionCommand(funcBT[i]);
			funcBG.add(funcButton);
			functionP.add(funcButton);
		}

		// define default button.
		ImageIcon img = new ImageIcon(this.getClass().getResource("Draw.png"));
		JRadioButton funcButton1 = new JRadioButton(img, true);
		ImageIcon img2 = new ImageIcon(this.getClass().getResource("Draw2.png"));
		funcButton1.setSelectedIcon(img2);
		funcButton1.setActionCommand("Free draw");
		funcBG.add(funcButton1);
		functionP.add(funcButton1);

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
			colorButton.addActionListener(e -> {
                setPenColor(colorButton.getBackground());
                System.out.println("color chosen " + "R:" + getPenColor().getRed() + " G:"
                        + getPenColor().getGreen() + " B:" + getPenColor().getBlue());
            });
			colorBG.add(colorButton);
			colorP.add(colorButton);
		}

		// set one more default selected color button as black. then pack it into color
		// panel and color button group.

		colorButton.setOpaque(true);
		colorButton.setFocusPainted(false);
		colorButton.setBackground(Color.BLACK);
		colorButton.setPreferredSize(new Dimension(30, 30));
		colorBG.add(colorButton);
		colorP.add(colorButton);
		colorButton.setSelected(true);
		colorButton.addActionListener(e -> {
            setPenColor(colorButton.getBackground());
            System.out.println("color chosen");
        });

		// add function panel and color panel into color and function panel.
		colorAndFunctionP.add(functionP, BorderLayout.SOUTH);
		colorAndFunctionP.add(colorP, BorderLayout.NORTH);

		return colorAndFunctionP;

	}

	// this method return the chatWindow Panel, the right white one.
	private JPanel chatWindow() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout( new BorderLayout());
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		JButton kickOut = new JButton("Kick Out");
		userNum = new JLabel();
		userNum.setText(users.getModel().getSize()+" Users Online");
		JScrollPane jListScroll = new JScrollPane();
		jListScroll.setPreferredSize(new Dimension(210,210));
		listPanel.add(jListScroll,BorderLayout.CENTER);
		listPanel.add(kickOut,BorderLayout.SOUTH);
		listPanel.add(userNum,BorderLayout.NORTH);
		jListScroll.setViewportView(users);
		users.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(users.getSelectedValue()!=null){
					kickOut.setEnabled(true);
				}
			}
		});
		if(users.getSelectedValue()==null){
			kickOut.setEnabled(false);
		}
		if(user.IsHost()){
			kickOut.setVisible(true);
		} else {
			kickOut.setVisible(false);
		}
		kickOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                try {
                    kickUser(users.getSelectedValue());
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
				try {
					user.chatClient.chatServer.broadcastUsers();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});


		// define properties of chat window panel.
		JPanel chatP = new JPanel();
		chatP.setLayout(new BorderLayout());
		chatP.setBackground(Color.GRAY);
		// chatP.setPreferredSize(new Dimension(400, 700));

		// define send bar, text screen and online user panel.
		JPanel sendingWindow = new JPanel(new FlowLayout());
		JPanel chatScreen = new JPanel(new BorderLayout());

		// define text area and a label on screen.
		screen = new JTextArea();
		JLabel screenLabel = new JLabel("Chat History", JLabel.CENTER);

		// define and set a new scroll container to contain the text screen panel.
		JScrollPane js = new JScrollPane(screen);
		js.setViewportView(screen);
		screen.setEditable(false);
		screen.setLineWrap(true);
		// js.setPreferredSize(new Dimension(330, 70));
		js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// set properties of sending text bar and action of send button.
		JTextField sendText = new JTextField(15);
		JButton sendJB = new JButton("Send");
		sendJB.setActionCommand("Send");
		sendJB.addActionListener(e -> {


            // push the newest received message to chat screen. -- no, you're appending the message you just send to the screen
            //screen.append(user.getUsername() + ": " + sendText.getText() + "\n");
            sendMessage(sendText.getText(), this.user, screen);

            // reset sending text bar.
            sendText.setText("");
            // reset cursor.
            sendText.requestFocus();

        });

		// construct sending window.
		sendingWindow.add(sendText);
		sendingWindow.add(sendJB);
		sendingWindow.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		// construct the chat panel.
		chatScreen.add(screenLabel, BorderLayout.NORTH);
		chatScreen.add(js, BorderLayout.CENTER);
		chatScreen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		chatP.add(sendingWindow, BorderLayout.SOUTH);
		chatP.add(chatScreen, BorderLayout.CENTER);
		mainPanel.add(chatP,BorderLayout.CENTER);
		mainPanel.add(listPanel,BorderLayout.NORTH);

		return mainPanel;

	}

	private JMenuBar menuBar(User u) {

		JMenu fileMenu = new JMenu("File");
		JMenuItem newSession = new JMenuItem("New File");
        newSession.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		JMenuItem openMenu = new JMenuItem("Open");
        openMenu.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		JMenuItem saveMenu = new JMenuItem("Save");
        saveMenu.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		JMenuItem saveAsMenu = new JMenuItem("Export to Image");
		JMenuItem closeMenu = new JMenuItem("Close");
		JMenuItem quitMenu = new JMenuItem("Quit");
		JMenuItem about = new JMenuItem("About");
		fileMenu.add(newSession);
		fileMenu.add(openMenu);
		fileMenu.add(saveMenu);
		fileMenu.add(saveAsMenu);
		fileMenu.add(closeMenu);
		fileMenu.add(quitMenu);
		fileMenu.add(about);
		JMenu editMenu = new JMenu("Edit");
		JMenuItem undoOption = new JMenuItem("Undo");
		undoOption.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		JMenuItem redoOption = new JMenuItem("Redo");
		redoOption.setAccelerator(KeyStroke.getKeyStroke('Y', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		JMenuItem clrOption = new JMenuItem("Erase Board");
		clrOption.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		editMenu.add(undoOption);
		editMenu.add(redoOption);
		editMenu.add(clrOption);
		JMenuBar jMenuBar = new JMenuBar();
		jMenuBar.add(fileMenu);
		jMenuBar.add(editMenu);

		saveMenu.addActionListener(e -> {
			FileUtil.save(drawboard.shapes);
		});

		openMenu.addActionListener(e -> {
            newSessionAvailable = true;
            drawboard.shapes = FileUtil.load();
            drawboard.repaint();
            // TODO may not open in a new window?
            //WhiteBoardGUI gui = new WhiteBoardGUI(newUser);

        });

		closeMenu.addActionListener(e -> {
		    // TODO is the comment below a todo?
			// use thread to avoid influence between different windows.
            if (JOptionPane.showConfirmDialog(null,"Do you want to save changes?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                FileUtil.save(drawboard.shapes);
            }
			if (this.isNewSessionAvailable()) {
				this.setVisible(false);
			} else {
				this.setVisible(false);
				this.dispose();
				//code to notify other users
			}
            try {
                this.user.chatClient.chatServer.unregisterChatClient(this.user.chatClient);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
		});

		quitMenu.addActionListener(e -> {

			if(JOptionPane.showConfirmDialog(null,"Are you sure you want to quit?","WARNING",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				this.setVisible(false);
				this.dispose();
				//code to update online peer list
			}

			//This should let users leave correctly, although not sure this is the right place to put it
			try {
				this.user.chatClient.chatServer.unregisterChatClient(this.user.chatClient);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});

		newSession.addActionListener(e -> {
            String newPort = JOptionPane.showInputDialog(null,"Please input the port number used to create new file", "WhiteBoard",JOptionPane.QUESTION_MESSAGE);
            if(newPort == null||newPort.equals("")){
            	return;
			}
			try {
				int portValid = Integer.parseInt(newPort);
				User newUser = this.getUser();
				newUser.setPort(newPort);
				WhiteBoardGUI newGui = new WhiteBoardGUI(newUser);
			}
			catch(NumberFormatException ex)  {
				JOptionPane.showMessageDialog( null,
						"The port number must be a number.","Oops", JOptionPane.PLAIN_MESSAGE );
			}
		});

		saveAsMenu.addActionListener(e -> {
            FileUtil.saveAs(drawboard.shapes, getDrawboard());
        });

		about.addActionListener(e -> {
            String msg = "WhiteBoard is a shared whiteboard application that allows users \nto draw simultaneously, as well as chat with other users on the board.\nThe application supports shapes (line,circle,oval,rectangle,as well as \nfree draw and text. It also allows the users to export the whiteboard \nto an image file(JPG).";
            String credit = "Developed by \nKasper, Marc, Navnita and Xin \n(Assignment 2 - COMP90015)";
            JOptionPane.showMessageDialog(null, msg + "\n\n" + credit, "About Whiteboard",
                    JOptionPane.INFORMATION_MESSAGE);
        });

		undoOption.addActionListener(e -> {
		    // TODO not sure if we want undo/redo to de-select a figure
            try {
                user.chatClient.chatServer.Undo();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            System.out.println("Undo...");
        });

		redoOption.addActionListener(e -> {
            // TODO not sure if we want undo/redo to de-select a figure
            try {
                user.chatClient.chatServer.Redo();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            System.out.println("Redo...");
        });

		clrOption.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(null, "Are you sure you want to erase the board?", "WARNING",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				try {
					user.chatClient.chatServer.eraseboard();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				System.out.println("Clearing board...");
            }
        });

        if(!this.user.IsHost()){
            newSession.setEnabled(false);
            openMenu.setEnabled(false);
            saveMenu.setEnabled(false);
            saveAsMenu.setEnabled(false);
            closeMenu.setEnabled(false);
            editMenu.setEnabled(false);
            editMenu.setVisible(false);
            quitMenu.setVisible(true);
            quitMenu.setEnabled(true);
        }
        else{
			quitMenu.setVisible(false);
			quitMenu.setEnabled(false);
		}
		return jMenuBar;

	}

	// This is the online list
//	private JPanel onlineList() {
//
//		// TODO onlineList may need some method in user class.
//		JPopupMenu popupMenu = new JPopupMenu();
//		JList list = new JList();
//		JPanel mainPanel = new JPanel();
//		JScrollPane scrollPane = new JScrollPane(list);
//		popupMenu.add("Kick Out");
//		popupMenu.add("Clear Draws");
//		popupMenu.add("User Info");
//		list.setModel(new DefaultListModel<String>());
//
//		return null;
//
//	}

    public static void appendMsg(String msg){
	    //textArea.append(msg);
	    screen.append(msg);
	    //textArea.append(chatClient.lastMsgReceived);
	    //System.out.println(chatClient.lastMsgReceived);
    }
	public void sendMessage(String msg, User user, JTextArea textArea) {
	    try {
            user.chatClient.chatServer.broadcastMessage(user.getUsername() + " : " + msg  + "\n");
        } catch (RemoteException e) { e.printStackTrace(); }


		// the method is called at 213 at this class (sendJB's actionPerformend method).

		// the username, state, IP and PORT in the arguments are local's.

		// the method below is to append text to text screen, something is the
		// information from other source.
		// the display format in parentheses below can changed as you wish.
		// screen.append(sourceUsername + "[" + sourceState + "]: " + sourceSay+ "\n");

	}

	public static void updateDrawboard(List<ColoredShape> shapes) {
        drawboard.shapes = shapes;
        drawboard.repaint();
    }

    public static void drawOnServer(List<ColoredShape> shapes) {
        try {
            user.chatClient.chatServer.draw(shapes);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void updateUserList(List<User> urs) {
    	// remove all elements from the JList.
        usersModel.removeAllElements();
    	// add new user from the input list into JList.
    	for(int i = 0; i < urs.size();i++){
    		usersModel.addElement(urs.get(i));
		}
        userNum.setText(users.getModel().getSize()+" Users Online");
        System.out.println(users.getModel().getSize());
        System.out.println(userArrayList.size());
	}

	public static void displaySessionClosed(String s) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, s);
            System.exit(0);
        });
    }

    public static boolean newUserPrompt(User u) {
        int i = JOptionPane.showConfirmDialog(null,
                u.getUsername()+" wants to join.\n Will you allow it?", "New User",
                JOptionPane.YES_NO_OPTION);
        return (i == JOptionPane.YES_OPTION);
    }

    public void kickUser(User usr) throws RemoteException {
        if (usr.IsHost()) {
            return;
        }
        user.chatClient.chatServer.unregisterUser(usr);
        user.chatClient.chatServer.broadcastUsers();
    }



	public Color getPenColor() {
		return penColor;
	}

	public void setPenColor(Color penColor) {
		this.penColor = penColor;
	}

	public BasicStroke getPenStroke() {
		return penStroke;
	}

	public void setPenStroke(BasicStroke stroke) {
		this.penStroke = stroke;
	}

	public DrawBoard getDrawboard() {
		return drawboard;
	}

	public User getUser() {
	    return user;
    }

	public boolean isNewSessionAvailable() {
		return newSessionAvailable;
	}

	public void setNewSessionAvailable(boolean newSessionAvailable) {
		this.newSessionAvailable = newSessionAvailable;
	}
}
