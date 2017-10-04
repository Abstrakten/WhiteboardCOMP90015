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
	private List<InputString> strings = new ArrayList<>();

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
	}

	public void addString(InputString i) {
		this.strings.add(i);
	}
}
