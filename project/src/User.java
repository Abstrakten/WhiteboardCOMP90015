import java.awt.Shape;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xin Qi
 * @version 1.4
 */

public class User implements Serializable {

	private String ip, port, username;
	boolean isHost;
	private List<ColoredShape> shapes = new ArrayList<>();
    private List<ColoredShape> redoShape = new ArrayList<>();
    private List<InputString> redoString = new ArrayList<>();
	private List<InputString> strings = new ArrayList<>();
    private List<String> trackList = new ArrayList<>();
    private List<String> redoTrack = new ArrayList<>();

	public List<ColoredShape> getShapes() {
		return shapes;
	}

	public void setShapes(List<ColoredShape> shapes) {
		this.shapes = shapes;
	}

	public List<InputString> getStrings() {
		return strings;
	}

	public void setStrings(List<InputString> strings) {
		this.strings = strings;
        this.trackList.add("text");
	}

	public User(String ip, String port, String username, Boolean host) {
		this.ip = ip;
		this.port = "10086";
		this.username = username;
		this.isHost = host;
	}

	public String toString() {

		return ("UserName: " + username + "\n" + "Is host: " + isHost + "\n" + "UserIP: " + ip + "\n" + "User's Port: "
				+ port + "\n");

	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getState() {
		return isHost;
	}

	public void setState(Boolean state) {
		this.isHost = state;
	}

	public void addShape(ColoredShape s) {
		this.shapes.add(s);
        this.trackList.add("Shape");
	}
    
	public void addString(InputString i) {
		this.strings.add(i);
        this.trackList.add("String");
	}
    
    //for clear screen
    public void delAll() {
        this.shapes.clear();
        this.strings.clear();
        this.trackList.clear();
        this.redoShape.clear();
        this.redoString.clear();
        this.redoTrack.clear();
    }
    
    //for undo
    public void undoShape(){
        if (!this.trackList.isEmpty()){
        if (this.trackList.get(this.trackList.size()-1).equals("Shape")){
        if (!this.shapes.isEmpty()) {
            this.redoShape.add(this.shapes.get(this.shapes.size()-1));
            this.shapes.remove(this.shapes.size()-1);
            this.redoTrack.add("Shape");
        }} else if (this.trackList.get(this.trackList.size()-1).equals("String")){
            if (!this.strings.isEmpty()) {
                this.redoString.add(this.strings.get(this.strings.size()-1));
                this.strings.remove(this.strings.size()-1);
                this.redoTrack.add("String");
            }
        }
        this.trackList.remove(this.trackList.size()-1);
        }
    }
    //for redo
    public void redoShape(){
        if (!this.redoTrack.isEmpty()){
         if (this.redoTrack.get(this.redoTrack.size()-1).equals("Shape")){
        if (!this.redoShape.isEmpty()){
            this.shapes.add(this.redoShape.get(this.redoShape.size()-1));
            this.redoShape.remove(this.redoShape.size()-1);
        }
         } else if (this.redoTrack.get(this.redoTrack.size()-1).equals("String")){
             if (!this.redoString.isEmpty()){
                 this.strings.add(this.redoString.get(this.redoString.size()-1));
                 this.redoString.remove(this.redoString.size()-1);
             }
         }
        this.redoTrack.remove(this.redoTrack.size()-1);
        }
    }
}
