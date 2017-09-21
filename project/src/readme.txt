The WhiteBoard1.2 is not complete yet, but it shoulb be fine to implement other works. The GUI are built by SWING and AWT.
The drawing shapes are build by graphics and graphics2D.

The WelcomeWindow class is mainly targets to collecting state and username information, 
e.g. "Sean" "Client"

The InputAddrWindow is aims to collecting the IP and HOST address, the user object of this
particular user is created by this class, it is created after the connection established, and before
the WhiteBoard object created. 
I suggest send the user object to the server and storing it in a list, it may helpful for some other works,
e.g. Online user list in Chating Window.
There has an empty method to be filled. Comments in the method body.

The WhiteBoardGUI class is the main body of the WhiteBoard, it now has problems as below:
1. The window listener is not finished. The program should info user to save the unsaved file when window closing.
