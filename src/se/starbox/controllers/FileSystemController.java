package se.starbox.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import se.starbox.models.FileSystemModel;
import se.starbox.models.SettingsModel;
import se.starbox.models.UserModel;

/**
* Handles incoming filedownloading-requests and reformats the parameters that are passed on to the File System Model.
* The download-request can move IndexData.xml files or shared files from the Starbox folder.
* @author Lukas J. Wensby and Henrik Boström
* @version 2012-03-13
*/
@WebServlet(
	description = "Handles index file and file transfer requests", 
	urlPatterns = { "/file/" }
)
public class FileSystemController extends HttpServlet {
	private static final long serialVersionUID = 5338560270728801227L;
	
	private FileSystemModel fileSystemModel;
	   
	/**
	 * Constructs a new FileSystemController.
	 */
	public FileSystemController(UserModel userModel, SettingsModel settingsModel) {
		this.fileSystemModel = new FileSystemModel(userModel, settingsModel);
	}
	   
	/**
	 * Handle web get request, it should either be a get file request or a get index request.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Is get file reqeust?
		String file = request.getParameter("file");
		if (file != null)
			getFile(request, response, file);
		else {
			// Is get index request?
			String index = request.getParameter("index");
			if (index != null)
				getIndexData(request, response);
			else {
				// Invalid request, respond with error page
				response.setContentType("text/html");
				
				PrintWriter writer = response.getWriter();
				writer.println("<html>");
				writer.println("<head><title>Starbox - Invalid request</title></head>");
				writer.println("<body>");
				writer.println("<p>Invalid request! Expects parameter 'file' or 'index'...</p>");
				writer.println("</body>");
				writer.println("</html>");
				writer.flush();
			}
		}
	}
	/**
	 * With a HTTP-request that gets interpreted the controller calls the model.
	 * @param request The HTTP-request that will be interpreted and filtered.
	 */
	public void getFile(HttpServletRequest request, HttpServletResponse response, String fileRequest) throws ServletException, IOException {
		File file = fileSystemModel.requestDownload(fileRequest, request.getRemoteAddr());
		if (file != null) {
			// Request is valid and allowed
			sendFile(response, file);
		}
		else {
			// Request is invalid or not allowed
			response.setContentType("text/html");
			
			PrintWriter writer = response.getWriter();
			writer.println("<html>");
			writer.println("<head><title>Starbox - Download failed</title></head>");
			writer.println("<body>");
			writer.println("<p>The request was invalid or denied by the other end. Nobody likes you.</p>");
			writer.println("</body>");
			writer.println("</html>");
			writer.flush();
		}
	}
	
	/**
	 * A request to send the indexData-file.
	 */
	@SuppressWarnings("unchecked")
	public void getIndexData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File indexFile = fileSystemModel.requestIndexData(request.getRemoteAddr());
		if (indexFile != null) {
			// Request is valid and allowed
			sendFile(response, indexFile);
		}
		else {
			// Request is invalid or not allowed
			response.setContentType("JSONObject");		// TODO: what content type to set for JSONObject?
			
			JSONObject jso = new JSONObject();
			jso.put("indexRequestFailed", "true");
			response.getWriter().write(jso.toString());
		}
	}
	
	/**
	 * Writes the file content to the HttpServletResponse's writer (after settings its "content type").
	 */
	private void sendFile(HttpServletResponse response, File file) throws IOException {
		// Set content type to "file transfer"
		response.setContentType("application/octet-stream");
		
		// Write file content
		BufferedReader in = new BufferedReader(new FileReader(file));
		PrintWriter writer = response.getWriter();
		String line;
		while ((line = in.readLine()) != null)
			writer.println(line);
		
		in.close();
		writer.flush();
	}
}
