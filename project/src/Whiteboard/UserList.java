package Whiteboard;

import java.util.ArrayList;

/**
 * Created by TriXXeD on 16/10/2017.
 */
public class UserList {
    public ArrayList<User> users;

    public void addUser(User user){
        this.users.add(user);
    }
    public void removeUser(User user){
        this.users.remove(user);
    }
}
