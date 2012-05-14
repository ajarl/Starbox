package se.starbox.models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.httpclient.HttpStatus;
/**
 * A Model class for executing tasks related to the contact list.
 * Keeps an internal list of current users in the contact list, aswell
 * as a file representation (in users.xml)
 * 
 * @author jens, walter
 *
 */
public class UserModel {
	private final String APP_PATH;
	private final String XML_PATH;
	private static final String XML_FILE = "users.xml";
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<Users xmlns=\"starbox\">\n";
	private static final String XML_TAIL = "</Users>";
	public static final String STATE_ACCEPTED = "accepted";
	public static final String STATE_DENIED = "denied";
	public static final String STATE_PENDING = "pending";
	public static final String STATE_SENT = "sent";
	private static final String TOMCAT_PORT = "8080";
	private static final String USER_APP_PATH = "/users/";
	private static final String USERS_URL = "/starbox/users";

	private static final int REQUEST_TIMEOUT = 5000;

	protected ArrayList<User> userList;

	/**
	 * Initiate the model instance. Parses the users.xml file on startup.
	 * @param context 
	 */
	public UserModel(ServletContext context){
		APP_PATH = SettingsModel.getProjectRootPath()+USER_APP_PATH;
		File appDir = new File(APP_PATH);
		if(!appDir.exists()){
			appDir.mkdir();
		}
		XML_PATH = APP_PATH+XML_FILE;
		initModel();
	}

	public static List<User> getWhitelistStatic(){
		String path = SettingsModel.getProjectRootPath()+USER_APP_PATH+XML_FILE;
		List<User> users = (ArrayList<User>) (new UserParser(path)).getAll();
		ArrayList<User> ipList = new ArrayList<User>();
		for(User u: users){
			if(u.getStatus().equals(STATE_ACCEPTED)){
				ipList.add(u);
			}
		}
		return ipList;
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
	 * @return status code header
	 */
	public String addUser(String ip, String email, String name, String group){
		SettingsModel settings = new SettingsModel();
		String responseHeader = "HTTP/1.1 "+HttpStatus.SC_NOT_FOUND+" not found";
		if(hasThisUser(ip))
			return "HTTP/1.1 "+HttpStatus.SC_OK+" duplicate user";
		try {
			String ownIP = InetAddress.getLocalHost().getHostAddress().toString();
			String request = Requests.addRequest(ownIP, settings.getEmail(), settings.getDisplayName());
			responseHeader = sendRequest(ip,request);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return responseHeader;
		}
		if(responseHeader.contains("200")){
			userList.add(new User(ip,STATE_SENT,email,name,group));
			writeToFile();
		}
		return responseHeader;
	}

	public void addIncomingUser(String ip, String email, String name){
		if(!hasThisUser(ip))
			userList.add(new User(ip,STATE_PENDING, email,name));
		writeToFile();
	}

	/**
	 * Return a List of all users currently in users.xml
	 * @return a List of all users currently in the contact list
	 */
	public List<User> getUsers(){
		if(userList == null){
		}
		Collections.sort(userList);
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
				break;
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
				u.setGroup(newGroup);
				break;
			}
		}
		writeToFile();
	}
	
	public void changeEmail(String IP, String newEmail){
		for(User u : userList){
			if(u.getIp().equals(IP)){
				u.setEmail(newEmail);
				break;
			}
		}
		writeToFile();
	}
	/**
	 * Set if a friend request was accepted by a user (change STATE_SENT to STATE_ACCEPTED)
	 * @param IP the ip address of the accepting contact
	 */
	public void setRequestResponse(String IP,String response, String email, String name){
		for(int i=0;i<userList.size();i++){
			if(userList.get(i).getIp().equals(IP)){
				userList.get(i).setStatus(response);
				userList.get(i).setEmail(email);
				userList.get(i).setName(name);
				break;
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
	public void acceptRequest(String IP,String email, String name){
		for(User u : userList){
			if(u.getIp().equals(IP)){
				u.setStatus(STATE_ACCEPTED);
				break;
			}
		}
		sendRequestResponse(STATE_ACCEPTED, IP, email, name);
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
				break;
			}
		}
		sendRequestResponse(STATE_DENIED, IP,"","");
		writeToFile();
	}

	private void sendRequestResponse(String response, String IP,String email, String name){
		String ownIP;
		try {
			ownIP = InetAddress.getLocalHost().getHostAddress().toString();
			String request = Requests.responseRequest(ownIP,response, email, name);
			sendRequest(IP,request);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private String sendRequest(String IP,String request){
		IP = IP.trim();
		String responseCodeHeader = "";
		int responseCode = HttpStatus.SC_NOT_FOUND;
		String url = "http://"+IP+":"+TOMCAT_PORT+USERS_URL;
		try {
			String requestString = url+"?"+request;
			System.out.println("Requeststring: "+requestString);
			HttpURLConnection connection = (HttpURLConnection) new URL(requestString).openConnection();
			connection.setConnectTimeout(REQUEST_TIMEOUT);
			responseCode = connection.getResponseCode();
			responseCodeHeader = "HTTP/1.1 "+responseCode+" "+connection.getResponseMessage();
			connection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			responseCodeHeader = "HTTP/1.1 "+HttpStatus.SC_BAD_REQUEST+" bad request";
			return responseCodeHeader;
		} catch(java.net.SocketTimeoutException e){
			responseCodeHeader = "HTTP/1.1 "+HttpStatus.SC_REQUEST_TIMEOUT+" timeout";
			return responseCodeHeader;
		} catch (IOException e) {
			e.printStackTrace();
			responseCodeHeader = "HTTP/1.1 "+HttpStatus.SC_INTERNAL_SERVER_ERROR+" internal server error";
			return responseCodeHeader;
		}
		return responseCodeHeader;
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
	protected void writeToFile(){
		synchronized(this){
			BufferedWriter out = null;
			try {
				File xmlFile = new File(XML_PATH);
				out = new BufferedWriter(new FileWriter(xmlFile));
				out.write(XML_HEADER);
				for(User u : userList){
					out.write(getUserBlock(u));
				}
				out.write(XML_TAIL);
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
	private boolean hasThisUser(String ip){
		for(User u : userList){
			if(u.getIp().equals(ip))
				return true;
		}
		return false;
	}
	public void removeUsersXML(){
		File xmlfile = new File(XML_PATH);
		if(xmlfile.exists())
			xmlfile.delete();
	}
}
