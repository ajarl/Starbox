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
	private UserModel userModel;
       
    public UsersController() {
        super();
        // TODO Auto-generated constructor stub
       userModel = new UserModel();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward="";
		
		Map parameters = request.getParameterMap();
		if (parameters.containsKey("userID")){
			
			request.setAttribute("userEmail", userModel.getEmail());
			forward = SHOW_JSP;
		} else {
			forward = LIST_JSP;
		}
		
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
