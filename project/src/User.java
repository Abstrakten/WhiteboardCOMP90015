import java.lang.Thread.State;

public class User {

	private String ip;
	private String port;

	private String username;
	private String state;

	public User(String ip, String port, String username, String state) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.state = state;
	}

	public String toString(User user) {

		return ("UserName: " + username + "\n" + "State: " + state + "\n" + "UserIP: " + ip + "\n" + "User's Port: "
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
