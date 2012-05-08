package se.starbox.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelpController
 */
@WebServlet(description = "handles user request from the interwebs.", urlPatterns = { "/help", "/help/" })
    public class HelpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String HELP_JSP = "/help.jsp";       
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HelpController() {
	    super();
	    // TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String forward = HELP_JSP;
	    RequestDispatcher view = request.getRequestDispatcher(forward);
	    view.forward(request, response);		
	    // TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // TODO Auto-generated method stub
	}

    }
