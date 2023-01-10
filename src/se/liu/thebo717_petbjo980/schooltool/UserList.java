package se.liu.thebo717_petbjo980.schooltool;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a list of several User-objects.
 * Is used to represent all employees registered at a school, for example.
 */
public class UserList
{
    private List<User> users = new ArrayList<>();

    public UserList() {
    }

    public User getUser(String username){
	for (User user: users) {
	    if(user.getUsername().equals(username)){
		return user;
	    }
	}
	return null;
    }

    public User getUser(int index){
	return users.get(index);
    }

    public boolean isEmpty(){
	return users.isEmpty();
    }

    public boolean contains(User user){
	return users.contains(user);
    }

    public List<User> getUsers(){
	return users;
    }

    public int getSize(){ return users.size(); }

    public void addUser(User user){
	users.add(user);
    }

    public void removeUser(User user){
	users.remove(user);
    }

    public UserList combine(UserList other){
	UserList combined = new UserList();
	for(User user : users){
	    combined.addUser(user);
	}
	for(User user : other.getUsers()){
	    combined.addUser(user);
	}
	return combined;
    }
}
