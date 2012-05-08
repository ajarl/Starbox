package se.starbox.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Handles requests to get files of the file system from the outside,
 * this includes file download requests and index data file requests.
 * The requests are handled in the sense that either you are accepted
 * or denied a <code>File</code> object of the file you are requesting.
 * Weather or not a request is accepted depends on if your IP address
 * is {@linkplain UserModel#getWhiteList() white-listed} or not.
 * @author Henrik Boström
 * @version 2012-02-28
 */
public class FileSystemModel {
	/**
	 * Checks if the specified ip is white-listed.
	 */
	protected static boolean isRequestAllowed(String ip) {
		boolean ret = ip != null;
		if (ret) {
			ret = false;
			List<User> users;
			synchronized (UserModel.class) {
				users = UserModel.getWhitelistStatic();
			}
			for (User user : users) {
				if (user.getIp().equals(ip)) {
					ret = true;
					break;
				}
			}
		}
		System.out.println("[" + ip + "] FileSystemModel.isRequestAllowed: " + ret);
		return ret;
	}
	
	/**
	 * Requests to download one of your files. The file is not actually transfered,
	 * but if the request is accepted then a <code>File</code> object will be returned
	 * for the corresponding local file, if not then <code>null</code> is returned.<br>
	 * For the request to be accepted <code>relativeFilepath</code> must correspond to
	 * an existing (readable) file of the Starbox folder and the <code>ip</code> must be
	 * {@linkplain UserModel#getWhiteList() white-listed}.
	 * @param relativeFilepath The file path of the file relative to the starbox folder
	 * @param ip The IP address of the person requesting the file
	 * @return The file requested to be download, or <code>null</code> if the request was declined
	 * @see UserModel#getWhiteList()
	 */
	public static File requestDownload(String relativeFilepath, String ip) {
		// File path must be non-null, non-zero + Is accepted request IP?
		if (relativeFilepath == null || relativeFilepath.length() == 0 || !isRequestAllowed(ip))
			return null;
		
		// Do not allow ".." in path; could be used to locate files outside the Starbox folder
		for (int i = 0; i < relativeFilepath.length() - 1; i++)
			if (relativeFilepath.charAt(i) == '.' && relativeFilepath.charAt(i + 1) == '.')
				return null;
		
		// Get starbox folder/
		String starboxFolder = new SettingsModel().getStarboxFolder();	// TODO: Should not create new SettingsModel just to get starbox folder?
		starboxFolder = starboxFolder.replace('\\', '/');
		if (starboxFolder.length() > 0 && starboxFolder.charAt(starboxFolder.length() - 1) != '/')
			starboxFolder += '/';
		
		// File exists?
		String filePath = starboxFolder + relativeFilepath;
		System.out.println("[" + ip + "] FileSystemModel.requestDownload: Using file path: " + filePath);
		File file = new File(filePath);
		if (!file.exists() || !file.canRead()) {
			System.out.println("[" + ip + "] FileSystemModel.requestDownload: File does not exist");
			return null;
		}
		//System.out.println("[" + ip + "] FileSystemModel.requestDownload: File exists");
		return file;
	}
	
	/**
	 * Requests to get the local index data. The file is not actually transfered,
	 * but if the request is accepted then a <code>File</code> object for will be returned
	 * for the XML-file of the local index data, if not then <code>null</code> is returned.<br>
	 * For the request to be accepted the <code>ip</code> must be {@linkplain UserModel#getWhiteList() white-listed}
	 * and the file must exist and be readable.
	 * @param ip The IP address of the person requesting the index
	 * @return The local index file, or <code>null</code> if the request was declined
	 */
	public static File requestIndexData(String ip) {
		// Is accepted request IP?
		if (!isRequestAllowed(ip))
			return null;
		
		// Get index path
		String indexPath = SettingsModel.getProjectRootPath();	// TODO: should be able to get index path instead, if it changes this line will no longer work!
		indexPath = indexPath.replace('\\', '/');
		if (indexPath.length() > 0 && indexPath.charAt(indexPath.length() - 1) != '/')
			indexPath += '/';
		indexPath += "Index/indexData.xml";
		
		// Index file exists?
		System.out.println("[" + ip + "] FileSystemModel.requestIndexData: Using index path: " + indexPath);
		File file = new File(indexPath);
		if (!file.exists() || !file.canRead()) {
			System.out.println("[" + ip + "] FileSystemModel.requestIndexData: File does not exist");
			return null;
		}
		//System.out.println("[" + ip + "] FileSystemModel.requestIndexData: File exists");
		return file;
	}
	
	/**
	 * The result of {@link FileSystemModel#downloadFile(String, File)}.
	 */
	public enum DownloadFileResult {
		/**
		 * Download completed successfully.
		 */
		SUCCESS,
		
		/**
		 * Download failed but before the destination file was written to.
		 * If the file already existed it is still intact.
		 */
		EARLY_FAIL,
		
		/**
		 * Download failed during transfer. The destination file is expected to be corrupted.
		 */
		INTERRUPTED_FAIL
	};
	
	/**
	 * Downloads a file over HTTP of the specified url to the destination file specified.
	 * @param url A url to a file (http)
	 * @param destinationFile The file to store the download in
	 * @return True on success, false otherwise
	 */
	public static DownloadFileResult downloadFile(String fileUrl, File destinationFile) {
		//fileUrl = "http://213.65.122.202:8080/starbox/file?file=u.png";
		URL url = null;
		try {
			url = new URL(fileUrl);
		} catch (MalformedURLException e1) {
			System.out.println("FileSystemModel.downloadFile: Malformed url: " + fileUrl);
			return DownloadFileResult.EARLY_FAIL;
		}
		
		//System.out.println("FileSystemModel.downloadFile: Connecting...");
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection)url.openConnection();
		} catch (IOException e) {
			System.out.println("FileSystemModel.downloadFile: IOException occurred when opening url-connection");
			return DownloadFileResult.EARLY_FAIL;
		} catch (ClassCastException e) {
			System.out.println("FileSystemModel.downloadFile: url => not http");
			return DownloadFileResult.EARLY_FAIL;
		}
		
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// surely this will never happen
			System.out.println("FileSystemModel.downloadFile: request method get => protocol exception");
			return DownloadFileResult.EARLY_FAIL;
		}
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(false);
		
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(5000);
		try {
			conn.connect();
		} catch (UnknownHostException e) {
			System.out.println("FileSystemModel.downloadFile: Unknown Host!");
			return DownloadFileResult.EARLY_FAIL;
		} catch (SocketTimeoutException e) {
			System.out.println("FileSystemModel.downloadFile: Timeout!");
			return DownloadFileResult.EARLY_FAIL;
		} catch (IOException e) {
			System.out.println("FileSystemModel.downloadFile: connect => IOException");
			return DownloadFileResult.EARLY_FAIL;
		}
		
		//try {
		//	System.out.println("FileSystemModel.downloadFile: Response Code:  " + conn.getResponseCode() + " (200 = OK)");
		//} catch (IOException e) { e.printStackTrace(); }
		//System.out.println("FileSystemModel.downloadFile: Content type: " + conn.getContentType());
		//System.out.println("FileSystemModel.downloadFile: Content length: " + conn.getContentLength());
		
		if (conn.getContentType().equals("JSONObject;charset=ISO-8859-1")) {
			System.out.println("FileSystemModel.downloadFile: Download request was rejected (either you do not have permissions or the file does not exist).");
			return DownloadFileResult.EARLY_FAIL;
		}
		
		InputStream in;
		try {
			in = conn.getInputStream();
		}
		catch (FileNotFoundException e) {
			System.out.println("FileSystemModel.downloadFile: getInputStream => FileNotFoundException");
			return DownloadFileResult.EARLY_FAIL;
		} catch (IOException e) {
			System.out.println("FileSystemModel.downloadFile: getInputStream => IOException");
			return DownloadFileResult.EARLY_FAIL;
		}
		
		OutputStream out;
		try {
			out = new FileOutputStream(destinationFile);
		}
		catch (FileNotFoundException e) {
			System.out.println("FileSystemModel.downloadFile: FileOutputStream for destinationFile => IOException");
			try {
				in.close();
			} catch (IOException e2) { }
			return DownloadFileResult.EARLY_FAIL;
		}
		
		//System.out.println("FileSystemModel.downloadFile: Read from input stream...");
		byte[] buffer = new byte[4096];
		int bytesRead;
		int totalNumBytes = 0;
		boolean success = false;
		try {
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				totalNumBytes += bytesRead;
			}
			success = true;
		}
		catch (SocketTimeoutException e) {
			System.out.println("FileSystemModel.downloadFile: InputStream.read Timeout (during file transfer)!");
		} catch (IOException e) {
			System.out.println("FileSystemModel.downloadFile: InputStream.read IOException (during file transfer)!");
		}
		finally {
			try {
				out.close();
				in.close();
			}
			catch (IOException e) {
			}
			if (!success) {
				System.out.println("FileSystemModel.downloadFile: Bytes read before fail: " + totalNumBytes);
				return DownloadFileResult.INTERRUPTED_FAIL;
			}
		}
		
		//System.out.println("FileSystemModel.downloadFile: Done (" + totalNumBytes + " bytes transferred).");
		conn.disconnect();
		return DownloadFileResult.SUCCESS;
	}
}
