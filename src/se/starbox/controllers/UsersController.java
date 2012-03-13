package se.starbox.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.starbox.models.UserModel;

/**
 * Servlet implementation class UsersController
 */
@WebServlet(description = "handles user request from the interwebs.", urlPatterns = { "/users/" })
public class UsersController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String DELETE_JSP = "/Delete.jsp";
	private static String EDIT_JSP = "/Edit.jsp";
	private static String LIST_JSP = "/users.jsp";
	private static String SHOW_JSP = "/user.jsp";
	private static String ADD_JSP = "/newuser.jsp";
	private UserModel userModel;
       
    public UsersController() {
        super();
        // TODO Auto-generated constructor stub
       userModel = new UserModel();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward="";
		String action = request.getParameter("action");
		
		if (action == null) {
			forward = LIST_JSP;
		} else if (action.equals("show")){
			request.setAttribute("userEmail", userModel.getEmail(request.getParameter("userID").toString()));
			forward = SHOW_JSP;
		} else if (action.equals("add")) {
			forward = ADD_JSP;
		} else {
			forward = LIST_JSP;
		}
		
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String forward="";
		String action = request.getParameter("action");
		
		if (action == null) {
			//error?
			request.setAttribute("errorMessage", "Du kan inte göra en post till users controllen utan en action. Tänk över ditt beteende.");
			forward = LIST_JSP;
		} else if (action.equals("create")){
			//outputar ipt igen för att vi ska kunna testa innan modellen är gjord.
			String output = userModel.createUser(request.getParameter("ip"));
			request.setAttribute("userEmail", output);
			forward = SHOW_JSP;
		} else {
			request.setAttribute("errorMessage", "Nu har du valt en knasig action. Felaktigt beteende igen.");
			forward = LIST_JSP;
		}
		
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

}
