package se.starbox.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.starbox.models.SearchModel;

/**
 * Servlet implementation class SearchController
 */
@WebServlet(
		description = "Handles search requests", 
		urlPatterns = { 
				"/SearchController", 
				"/", 
				"/query"
		})
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SearchModel sm;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchController() {
        super();
        // TODO Auto-generated constructor stub
        
        sm = new SearchModel();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String, String[]> params = request.getParameterMap();
		String viewStr="";
		if (params.containsKey("param1")) {
			viewStr = "/view.jsp";
		} else {
			viewStr = "/noview.jsp";
		}
		RequestDispatcher view = request.getRequestDispatcher(viewStr);
		if (params.get("param1") != null)
			request.setAttribute("message", sm.getHello(params.get("param1")[0]));
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
