package se.starbox.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.starbox.util.IndexDownloader;

/**
 * A Servlet that initializes background processes, if not already running.
 * This should be called (visit url) when Starbox first starts.
 */
@WebServlet(
	description = "Initializes background processes", 
	urlPatterns = { "/init" }
)
public class InitBackgroundProcessesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IndexDownloader.start();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
