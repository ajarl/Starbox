package se.starbox.models;

import java.io.File;

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
		// TODO: Use UserModel.getWhitelistStatic() in determining if request is allowed (at time of writing the method causes crash)
		boolean ret = ip != null;// && UserModel.getWhitelistStatic().contains(ip);
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
		// TODO: Should not create new SettinsModel just to get starbox folder?
		String starboxFolder = new SettingsModel().getStarboxFolder();
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
}
