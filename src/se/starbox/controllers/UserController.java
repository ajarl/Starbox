package se.starbox.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
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
	private static String EDIT_JSP = "/edituser.jsp";
	private static String LIST_JSP = "/users.jsp";
	private static String SHOW_JSP = "/user.jsp";
	private static String ADD_JSP = "/newuser.jsp";
	private static String EMPTY_JSP = "/empty.jsp";
	private UserModel userModel;
	private SettingsModel settingsModel;
	
	private static final String ACTION_CREATE = "create";
       
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
       userModel = new UserModel();
       settingsModel = new SettingsModel();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward="";
		String action = request.getParameter("action");
		
		if (action == null) {
			List<User> users = userModel.getUsers();
			request.setAttribute("userList", users);
			forward = LIST_JSP;
		}/* else if (action.equals("show")){
			request.setAttribute("userEmail", "user email. fix this");
			forward = SHOW_JSP;
		}*/else if (action.equals("add")) {
			forward = ADD_JSP;
		} else if (action.equals("edit")) {
			request.setAttribute("userID","1");
			request.setAttribute("userEmail","emajl@test.se");
			request.setAttribute("userIP","1.2.3.4.5.6.7.8.9");
			request.setAttribute("userName","TestNamn");
			forward = EDIT_JSP;
		} else if (action.equals("accept")) {
			request.setAttribute("userEmail", " accepted a new user ");
			forward = SHOW_JSP;
		} else if (action.equals("deny")) {
			request.setAttribute("userEmail", " denied a new user");
			forward = LIST_JSP;
		} else if (action.equals("destroy")) {
			//userModel.deleteUser();
			forward = LIST_JSP;
			
		} else if(action.equals(Requests.REQUEST_ADD)){
			String ip = (String) request.getAttribute("ip");
			String email = (String) request.getAttribute("email");
			String name = (String) request.getAttribute("name");
			userModel.addIncomingUser(ip, email, name);
			forward = EMPTY_JSP;
			
		} else if(action.equals(Requests.REQUEST_RESPONSE)){
			String ip = (String) request.getAttribute("ip");
			String email = (String) request.getAttribute("email");
			String name = (String) request.getAttribute("name");
			String requestResponse = (String) request.getAttribute("response");
			userModel.setRequestResponse(ip,requestResponse,email,name);
			forward = EMPTY_JSP;
			
		} else {
			forward = LIST_JSP;
		}
		
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward="";
		String action = request.getParameter("action");
		
		if (action == null) {
			//error?
			request.setAttribute("errorMessage", "Du kan inte göra en post till users controllen utan en action. Tänk över ditt beteende.");
			forward = LIST_JSP;
		} else if (action.equals(ACTION_CREATE)){
			String ip = (String) request.getAttribute("ip");
			userModel.addUser(ip, settingsModel.getEmail(), settingsModel.getDisplayName(),"");
			request.setAttribute("addedUser", ip);
			forward = ADD_JSP;
		} else if (action.equals("update")){
			//userModel.updateUser();
			request.setAttribute("userEmail", "update?");
			forward = SHOW_JSP;
		} else {
			request.setAttribute("errorMessage", "Nu har du valt en knasig action. Felaktigt beteende igen.");
			forward = LIST_JSP;
		}
		
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
