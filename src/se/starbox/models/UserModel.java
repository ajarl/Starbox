package se.starbox.models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
/**
 * A Model class for executing tasks related to the contact list.
 * Keeps an internal list of current users in the contact list, aswell
 * as a file representation (in users.xml)
 * 
 * @author jens, walter
 *
 */
public class UserModel {
	private static final String XML_PATH = "src/files/users.xml";
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<Users xmlns=\"starbox\">\n";
	private static final String XML_TAIL = "</Users>";
	private static final String STATE_ACCEPTED = "accepted";
	private static final String STATE_DENIED = "denied";
	private static final String STATE_PENDING = "pending";
	private static final String STATE_SENT = "sent";
	private static final String TOMCAT_PORT = "8080";
	private static final String USER_APP_PATH = "/starbox/users/";
	
	private ArrayList<User> userList;
	
	/**
	 * Initiate the model instance. Parses the users.xml file on startup.
	 */
	public UserModel(){
		initModel();
	}
	private void initModel(){
		userList = (ArrayList<User>) (new UserParser(XML_PATH)).getAll();
		
	}
	/**
	 * Add a user to the contact list ( IP and email are required, rest are optional)
	 * @param ip the IP address of the contact
	 * @param email the email address of the contact
	 * @param name the name of the contact
	 * @param group the group the contact belongs to
	 * @param status the status of the contact(pending,accepted,denied)
	 */
	public void addUser(String ip, String email, String name, String group){
		try {
			String ownIP = InetAddress.getLocalHost().toString();
			String request = Requests.addRequest(ownIP, email, name);
			sendRequest(ip,request);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userList.add(new User(ip,email,name,group,STATE_SENT));
		writeToFile();
	}
	
	public void addIncomingUser(String ip, String email, String name){
		userList.add(new User(ip,email,name,STATE_PENDING));
		writeToFile();
	}
	
	/**
	 * Return a List of all users currently in users.xml
	 * @return a List of all users currently in the contact list
	 */
	public List<User> getUsers(){
		if(userList == null){
		}
		return userList;
	}
	/**
	 * Change the display name of a contact
	 * @param IP  the IP address of the contact to change
	 * @param newName the new display name
	 */
	public void changeName(String IP, String newName){
		for(User u : userList){
			if(u.getIp().equals(IP)){
				u.setName(newName);
			}
		}
		writeToFile();
	}
	/**
	 * Change the group of a contact
	 * @param IP  the IP address of the contact to change
	 * @param newGroup the name of the group to change to
	 */
	public void changeGroup(String IP, String newGroup){
		for(User u : userList){
			if(u.getIp().equals(IP)){
				u.setName(newGroup);
			}
		}
		writeToFile();
	}
	/**
	 * Set if a friend request was accepted by a user (change STATE_SENT to STATE_ACCEPTED)
	 * @param IP the ip address of the accepting contact
	 */
	public void requestResponse(String IP,String response){
		for(int i=0;i<userList.size();i++){
			if(userList.get(i).getIp().equals(IP)){
				userList.get(i).setStatus(response);
			}
		}
		writeToFile();
	}
	/**
	 * Remove a user from the contact list
	 * @param IP the IP address of the contact to remove
	 */
	public void removeUser(String IP){
		for(int i=0;i<userList.size();i++){
			if(userList.get(i).getIp().equals(IP)){
				userList.remove(i);
			}
		}
		writeToFile();
	}
	/**
	 * Set a contact's status to accepted
	 * @param IP the IP address of the contact to accept
	 */
	public void acceptRequest(String IP){
		for(User u : userList){
			if(u.getIp().equals(IP)){
				u.setStatus(STATE_ACCEPTED);
			}
		}
		sendRequestResponse(STATE_ACCEPTED, IP);
		writeToFile();
	}
	/**
	 * Set a contact's status to denied
	 * @param IP the IP address of the contact to deny
	 */
	public void denyRequest(String IP){
		for(User u : userList){
			if(u.getIp().equals(IP)){
				u.setStatus(STATE_DENIED);
			}
		}
		sendRequestResponse(STATE_DENIED, IP);
		writeToFile();
	}
	
	private void sendRequestResponse(String response, String IP){
		String ownIP;
		try {
			ownIP = InetAddress.getLocalHost().toString();
			String request = Requests.responseRequest(ownIP,response);
			sendRequest(IP,request);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	private void sendRequest(String IP,String request){
		String url = IP+":"+TOMCAT_PORT+USER_APP_PATH;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url+"?"+request).openConnection();
			int responseCode = connection.getResponseCode();
			if(responseCode != 200){
				//FUCK, FEL
				System.out.println("FUCK,FEL");
			} else{
				//Rätt
				System.out.println("SHU");
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Get a list of the IP addresses which are whitelisted, 
	 * that is, their status is set to accepted.
	 * @return a List of whitelisted IP addresses
	 */
	public List<String> getWhiteList(){
		ArrayList<String> ipList = new ArrayList<String>();
		for(User u: userList){
			if(u.getStatus().equals(STATE_ACCEPTED)){
				ipList.add(u.getIp());
			}
		}
		return ipList;
	}
	private void writeToFile(){
		BufferedWriter out = null;
		try {
			File xmlFile = new File(XML_PATH);
			out = new BufferedWriter(new FileWriter(xmlFile));
			out.write(XML_HEADER);
			for(User u : userList){
				out.write(getUserBlock(u));
			}
			out.write(XML_TAIL);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String getUserBlock(User u){
		String block = "\t<User>\n\t\t<IP>"+u.getIp()+"</IP>\n"+
						"\t\t<DisplayName>"+u.getName()+"</DisplayName>\n"+
						"\t\t<Email>"+u.getEmail()+"</Email>\n"+
						"\t\t<GroupName>"+u.getGroup()+"</GroupName>\n"+
						"\t\t<Status>"+u.getStatus()+"</Status>\n"+
						"\t</User>\n";
		return block;
	}

}
