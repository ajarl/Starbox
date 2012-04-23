package se.starbox.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import se.starbox.models.FileSystemModel;

/**
* Handles incoming filedownloading-requests and reformats the parameters that are passed on to the File System Model.
* The download-request can move IndexData.xml files or shared files from the Starbox folder.
* @author Lukas J. Wensby and Henrik Boström
* @version 2012-04-15
*/
@WebServlet(
	description = "Handles index file and file transfer requests", 
	urlPatterns = { "/file/", "/file" }
)
public class FileSystemController extends HttpServlet {
	private static final long serialVersionUID = 5338560270728801227L;
	
	private FileSystemModel fileSystemModel;

	/**
	 * Constructs a new FileSystemController.
	 */
	public FileSystemController() {
		// TODO: get or create FileSystemModel. Calling getServletContext() = crash! :S
		
		//fileSystemModel = new FileSystemModel(new UserModel(getServletContext()), new SettingsModel());
		//System.out.println("getServletContext(): " + getServletContext());
		
		fileSystemModel = null;
	}
	
	/*public FileSystemController(UserModel userModel, SettingsModel settingsModel) {
		this.fileSystemModel = new FileSystemModel(userModel, settingsModel);
	}*/
	   
	/**
	 * Handle web get request, it should either be a get file request or a get index request.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("FileSystemController.doGet called.");
		
		// temp fail message
		if (fileSystemModel == null) {
			/*response.setContentType("text/html");
			
			PrintWriter writer = response.getWriter();
			writer.println("<html>");
			writer.println("<head><title>Starbox - Unable to handle request</title></head>");
			writer.println("<body>");
			writer.println("<p>Unable to handle request due to 'fileSystemModel' being null.</p>");
			writer.println("</body>");
			writer.println("</html>");
			writer.flush();
			
			return;*/
		}
		
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
	 * A request to send a file (file transfer of personal file).
	 */
	private void getFile(HttpServletRequest request, HttpServletResponse response, String fileRequest) throws ServletException, IOException {
		System.out.println("FileSystemController.getFile called with fileRequest='" + fileRequest + "'.");
		
		// temp
		/*if (fileSystemModel == null) {
			sendFile(response, new File("C:/Starbox/u.png"), false);
			return;
		}*/
		
		File file = fileSystemModel != null ? fileSystemModel.requestDownload(fileRequest, request.getRemoteAddr()) : null;
		if (file != null) {
			// Request is valid and allowed
			sendFile(response, file, true);
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
	private void getIndexData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("FileSystemController.getIndexData called.");
		
		File indexFile = fileSystemModel != null ? fileSystemModel.requestIndexData(request.getRemoteAddr()) : null;
		if (indexFile != null) {
			// Request is valid and allowed
			sendFile(response, indexFile, true);
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
	 * Writes the file to the response's output stream; this sends the file through
	 * browser file transfer.
	 */
	private void sendFile(HttpServletResponse response, File file, boolean http) throws IOException {
		// Set response of "file transfer" type
		String fileName = file.getName();
		// Determine file type (file extension)
		String fileType = "octet-stream";	// default
		for (int i = fileName.length() - 2; i >= 0; i--)
			if (fileName.charAt(i) == '.') {
				fileType = fileName.substring(i + 1, fileName.length());
				break;
			}
		
		response.setContentType("application/" + fileType);
		response.setContentLength((int)file.length());
		response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

		OutputStream out = response.getOutputStream();
		
		// Transfer file - Write file to output stream
		//System.out.println("BEGIN TRANSFER");
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		byte[] buffer = new byte[4096];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) != -1)
			out.write(buffer, 0, bytesRead);
		//System.out.println("END TRANSFER");
		
		in.close();
		out.flush();
	}
}
