package se.starbox.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import se.starbox.models.UserModel;

/**
 * Servlet implementation class UserController
 */
@WebServlet(
	description = "Handles users",
	urlPatterns = {
		"/users"
	}
)
public class UserController extends HttpServlet {
	private static final long serialversionUID = 1L; //ändra TODO

	private UserModel userModel;


	public UserController() {
		super();
		userModel = new UserModel();
	}

	// /user/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// list all users
		request.setAttribute("displayName", userModel.getUsers());

		RequestDispatcher view = request.getRequestDispatcher("users.jsp");
		view.forward(request, response);
	}

	// HTTP POST update or create user? Can you do both?
	//Dvs add?
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//update in model

		if (userModel.updateUser(request.getParameterMap())) {
			request.setAttribute("updated", true);
		} else {
			request.setAttribute("updated", false);
		}

		request.setAttribute("user", userModel.getUser(request.getParameterMap()));

		RequestDispatcher view = request.getRequestDispatcher("users.jsp");
		view.forward(request, response);
	}


	// /user/destroy
	protected void doDestroy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (userModel.deleteUser(request.getParameterMaP())) {
			request.setAttribute("deleted", true);
		} else {
			request.setAttribute("deleted", false);
		}
		
		RequestDispatcher view = request.getRequestDispatcher("users.jsp");
		view.forward(request, response);
		
	}
}