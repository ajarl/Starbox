package se.starbox.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.starbox.models.Requests;
import se.starbox.models.SettingsModel;
import se.starbox.models.User;
import se.starbox.models.UserModel;

/**
 * Servlet implementation class UsersController
 */
@WebServlet(description = "handles user request from the interwebs.", urlPatterns = { "/users/", "/users" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String LIST_JSP = "/users.jsp";
	private static String ADD_JSP = "/newuser.jsp";
	private static String EMPTY_JSP = "/empty.jsp";
	private static String TEST_JSP = "/JSONTests.jsp";
	private UserModel userModel;
	private SettingsModel settingsModel;

	private static final String ACTION_ADD_USER = "create";
	private static final String ACTION_UPDATE = "update";
	private static final String ACTION_REMOVE = "remove";
	private static final String ACTION_ANSWER = "friendrequest";
	private static final String ACTION_DELETE_XML = "deletexml";

	private static final String ACTION_GO_TO_ADD = "gotoadd";

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Initializes background processes if necessary
		new InitBackgroundProcessesServlet().doGet(request, response);
		
		setFields(request);
		String forward="";
		String action = request.getParameter("action");
		if (action == null) {
			request = getUserlistRequest(request);
			forward = LIST_JSP;
		}
		else if (action.equals(ACTION_GO_TO_ADD)) {
			forward = ADD_JSP;
		}
		else if(action.equals(Requests.REQUEST_ADD)){
			String ip = (String) request.getParameter(Requests.ATTRIBUTE_IP);
			ip = format(ip);
			String email = (String) request.getParameter(Requests.ATTRIBUTE_EMAIL);
			email = format(email);
			String name = (String) request.getParameter(Requests.ATTRIBUTE_NAME);
			name = format(name);
			userModel.addIncomingUser(ip, email, name);
			forward = EMPTY_JSP;

		} else if(action.equals(Requests.REQUEST_RESPONSE)){
			String ip = (String) request.getParameter(Requests.ATTRIBUTE_IP);
			ip = format(ip);
			String email = (String) request.getParameter(Requests.ATTRIBUTE_EMAIL);
			email = format(email);
			String name = (String) request.getParameter(Requests.ATTRIBUTE_NAME);
			name = format(name);
			String requestResponse = (String) request.getParameter("response");
			requestResponse = format(requestResponse);
			userModel.setRequestResponse(ip,requestResponse,email,name);
			forward = EMPTY_JSP;

		} else {
			request = getUserlistRequest(request);
			forward = LIST_JSP;
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setFields(request);
		String forward="";
		String action = request.getParameter("action");

		if (action == null) {
			//error?
			request.setAttribute("errorMessage", "Du kan inte göra en post till users controllen utan en action. Tänk över ditt beteende.");
			request = getUserlistRequest(request);
			forward = LIST_JSP;
		} else if (action.equals(ACTION_ADD_USER)){
			String ip = (String) request.getParameter(Requests.ATTRIBUTE_IP);
			ip = format(ip);
			String email = (String) request.getParameter(Requests.ATTRIBUTE_EMAIL);
			email = format(email);
			String name = (String) request.getParameter(Requests.ATTRIBUTE_NAME);
			name = format(name);
			String group = (String) request.getParameter(Requests.ATTRIBUTE_GROUP);
			group = format(group);
			String responseHeader = userModel.addUser(ip, email, name,group);
			request.setAttribute("response", responseHeader);
			if(!responseHeader.contains("200"))
				response.setStatus(404);
			request.setAttribute("addedUser", ip);
			forward = ADD_JSP;
		} else if (action.equals(ACTION_UPDATE)){
			String newName = (String) request.getParameter(Requests.ATTRIBUTE_NAME);
			newName = format(newName);
			String newGroup = (String) request.getParameter(Requests.ATTRIBUTE_GROUP);
			newGroup = format(newGroup);
			String ip = (String) request.getParameter(Requests.ATTRIBUTE_IP);
			ip = format(ip);
			if (newName != null){
				userModel.changeName(ip, newName);
			}
			if (newGroup != null){
				userModel.changeGroup(ip, newGroup);
			}
			request = getUserlistRequest(request);
			forward = LIST_JSP;
		} else if(action.equals(ACTION_REMOVE)){
			String ip = (String) request.getParameter(Requests.ATTRIBUTE_IP);
			ip = format(ip);
			userModel.removeUser(ip);
			request = getUserlistRequest(request);
			forward = LIST_JSP;
		} else if(action.equals(ACTION_ANSWER)){
			String answer = request.getParameter(Requests.ATTRIBUTE_RESPONSE);
			answer = format(answer);
			String IP = request.getParameter(Requests.ATTRIBUTE_IP);
			IP = format(IP);
			boolean doAccept = answer.equals(UserModel.STATE_ACCEPTED);
			if(doAccept){
				SettingsModel ownSettings = new SettingsModel();
				String email = ownSettings.getEmail();
				String name = ownSettings.getDisplayName();
				userModel.acceptRequest(IP, email, name);
			}else
				userModel.denyRequest(IP);
			request = getUserlistRequest(request);
			forward = LIST_JSP;
		} else if(action.equals(ACTION_DELETE_XML)){
			userModel.removeUsersXML();
			request = getUserlistRequest(request);
			forward = LIST_JSP;
		} else {
			request.setAttribute("errorMessage", "Nu har du valt en knasig action. Felaktigt beteende igen.");
			request = getUserlistRequest(request);
			forward = LIST_JSP;
		}

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}
	private String format(String toTrim){
		toTrim = toTrim.replaceAll("\n","");
		toTrim = toTrim.replaceAll("\r","");
		return toTrim;
	}
	private HttpServletRequest getUserlistRequest(HttpServletRequest request){
		List<User> allUsers = userModel.getUsers();
		request.setAttribute("USERS_ALL", allUsers);
		request.setAttribute("USERS_PENDING", getPendingUsers(allUsers));
		request.setAttribute("USERS_ACCEPTED", getAcceptedUsers(allUsers));
		request.setAttribute("USERS_SENT", getSentUsers(allUsers));
		return request;
	}
	private void setFields(HttpServletRequest request){
		ServletContext context = request.getServletContext();
		userModel = new UserModel(context);
		settingsModel = new SettingsModel();
	}
	
	private List<User> getPendingUsers(List<User> list){
		return getUserlistWithStatus(list,UserModel.STATE_PENDING);
	}
	private List<User> getAcceptedUsers(List<User> list){
		return getUserlistWithStatus(list,UserModel.STATE_ACCEPTED);
	}
	private List<User> getSentUsers(List<User> list){
		return getUserlistWithStatus(list,UserModel.STATE_SENT);
	}
	private List<User> getUserlistWithStatus(List<User> list, String state){
		List<User> returnList = new ArrayList<User>();
		for (User u : list){
			if(u.getStatus().equals(state))
				returnList.add(u);
		}
		return returnList;
	}
	
	
	

}
