package Whiteboard;

import ChatClient.ChatClient;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class User implements Serializable{

	private String ip, port, username;
	private boolean isHost;
    public ChatClient chatClient;
    public int id;

	public User(String ip, String port, String username, Boolean host) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.isHost = host;
	}

	public void kickUser(User user) throws RemoteException {
	    if(this.isHost != true) { return; }
	    this.chatClient.chatServer.unregisterChatClient(user.chatClient);
	    this.chatClient.chatServer.unregisterUser(user);
    }

	public String toString() {
		return (username + " ID: " + id);
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
