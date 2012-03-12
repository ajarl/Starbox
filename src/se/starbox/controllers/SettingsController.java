package se.starbox.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.starbox.models.SettingsModel;

/**
 * Servlet implementation class SettingsController
 */
@WebServlet(
		description = "Handles local user settings",
		urlPatterns = {
				"/settings"
		}
)
public class SettingsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private SettingsModel sm;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SettingsController() {
        super();
        // TODO Auto-generated constructor stub
        
        sm = new SettingsModel();
    }

	/**
	 * Endast för teständamål, följer antagligen inte ADD. Ändra denna om det behövs!
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("displayName", sm.getDisplayName());
		request.setAttribute("email", sm.getEmail());
		request.setAttribute("starboxFolder", sm.getStarboxFolder());
		request.setAttribute("indexUpdateInterval", sm.getIndexUpdateInterval());
		
		RequestDispatcher view = request.getRequestDispatcher("settings.jsp");
		view.forward(request, response);
	}

	/**
	 * Endast för teständamål, följer antagligen inte ADD. Ändra denna om det behövs!
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> params = request.getParameterMap();
	
		if (params.containsKey("email"))
			sm.setEmail(params.get("email")[0]);
		
		request.setAttribute("displayName", sm.getDisplayName());
		request.setAttribute("email", sm.getEmail());
		request.setAttribute("starboxFolder", sm.getStarboxFolder());
		request.setAttribute("indexUpdateInterval", sm.getIndexUpdateInterval());
		
		RequestDispatcher view = request.getRequestDispatcher("settings.jsp");
		view.forward(request, response);
	}

}
