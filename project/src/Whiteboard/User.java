package Whiteboard;

import ChatClient.ChatClient;

public class User {

	private String ip, port, username;
	private boolean isHost;
    public ChatClient chatClient;

	public User(String ip, String port, String username, Boolean host) {
		this.ip = ip;
		this.port = port;
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

	public Boolean IsHost() {
		return isHost;
	}

	public void IsHost(Boolean state) {
		this.isHost = state;
	}

}
