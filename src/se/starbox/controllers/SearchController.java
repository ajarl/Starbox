package se.starbox.controllers;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
				"/search/"
		})
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 132158L;
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
		String query = request.getParameter("query");
		String params = "";

		// If query is empty, return HTML view.
		if (query == null) {
			RequestDispatcher view = request.getRequestDispatcher("../search.jsp");
			request.setAttribute("query", query);
			request.setAttribute("params", params);
			view.forward(request, response);
		} else {
			// Else, return JSON data from SearchModel.
			// query="seanbanan" params="filetype:exe;minfilesize:20;maxfilesize:10"
			// Parse out params and remove them from query. 
			Pattern pFileType = Pattern.compile("filetype[:=][,a-z0-9]*");
			Matcher mFileType = pFileType.matcher(query);
			query = query.replaceAll("filetype[:=][,a-z0-9]*", "");
			Pattern pMinFileSize = Pattern.compile("minfilesize[:=][0-9]*");
			Matcher mMinFileSize = pMinFileSize.matcher(query);
			query = query.replaceAll("minfilesize[:=][0-9]*", "");
			Pattern pMaxFileSize = Pattern.compile("maxfilesize[:=][0-9]*");
			Matcher mMaxFileSize = pMaxFileSize.matcher(query);
			query = query.replaceAll("maxfilesize[:=][0-9]*", "");
			
			while(mFileType.find()) {
				params += mFileType.group() + ";";
			}
			
			while(mMinFileSize.find()) {
				params += mMinFileSize.group() + ";";
			}
			
			while(mMaxFileSize.find()) {
				params += mMaxFileSize.group() + ";";
			}
			
			// Trim off trailing ;
			if (params.endsWith(";"))
				params = params.substring(0, params.length()-1);
		
			// If search query isn't null someone is trying to search.
			// Fetch a list of SearchResult from the SearchModel and put them
			// in the request.
			if (query != "" || query != null) {
				// some list = sm.query(query, params);
				// request.put(some list)
			}
			
			response.getWriter().write("json");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
