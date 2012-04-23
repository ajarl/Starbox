package se.starbox.controllers;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import se.starbox.models.SearchModel;
import se.starbox.util.SearchResult;

/**
 * Servlet implementation class SearchController
 * 
 * @author Kim Nilsson
 */
@WebServlet(description = "Handles search requests", urlPatterns = { "/search/" })
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 132158L;
	private static SearchModel sm;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchController() {
		super();

		if (sm == null)
			sm = new SearchModel();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("query");
		String params = "";

		// If query is empty, return HTML view.
		if (query == null) {
			RequestDispatcher view = request
					.getRequestDispatcher("../search.jsp");
			request.setAttribute("query", query);
			request.setAttribute("params", params);
			view.forward(request, response);
		} else {
			// Else, return JSON data from SearchModel.
			// query="seanbanan"
			// params="filetype:exe;minfilesize:20;maxfilesize:10"
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

			while (mFileType.find()) {
				params += mFileType.group() + ";";
			}

			while (mMinFileSize.find()) {
				params += mMinFileSize.group() + ";";
			}

			while (mMaxFileSize.find()) {
				params += mMaxFileSize.group() + ";";
			}

			// Trim off trailing ;
			if (params.endsWith(";"))
				params = params.substring(0, params.length() - 1);

			// If search query isn't null someone is trying to search.
			// Fetch a list of SearchResult from the SearchModel and put them
			// in the request.
			if (query != "" || query != null) {
				LinkedList<SearchResult> searchResults;
				searchResults = sm.query(query, params);
				
				// Iterate through the results and print the JSON representation of the results.
				Iterator<SearchResult> it = searchResults.iterator();
			
				// Begin JSON array.
				response.setContentType("application/json");
				response.getWriter().println("[");
				
				while(it.hasNext()) {
					SearchResult res = (SearchResult) it.next();
					response.getWriter().println(res.toJSON().toJSONString());
					
					// If there's more. Print a comma so the array will be correctly formatted.
					if (it.hasNext())
						response.getWriter().println(",");
				}
				
				// End JSON array.
				response.getWriter().print("]");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
