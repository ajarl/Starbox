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
	public static final String FILEPATH_INDEXDATA = "src/IndexData.xml";	// TODO: place this field somewhere else
	
	private UserModel userModel;
	private SettingsModel settingsModel;
	
	/**
	 * Creates a new FileSystemModel.
	 * @param userModel The user model to use (for checking the white-list)
	 * @param settingsModel The settings model to use (for checking the Starbox folder file path)
	 */
	public FileSystemModel(UserModel userModel, SettingsModel settingsModel) {
		this.userModel = userModel;
		this.settingsModel = settingsModel;
	}
	
	/**
	 * Checks if the specified ip is white-listed.
	 */
	protected boolean isRequestAllowed(String ip) {
		return ip != null && userModel.getWhiteList().contains(ip);
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
	public File requestDownload(String relativeFilepath, String ip) {
		// File path must be non-null, non-zero + Is accepted request IP?
		if (relativeFilepath == null || relativeFilepath.length() == 0 || !isRequestAllowed(ip))
			return null;
		
		// Do not allow ".." in path; could be used to locate files outside the Starbox folder
		for (int i = 0; i < relativeFilepath.length() - 1; i++)
			if (relativeFilepath.charAt(i) == '.' && relativeFilepath.charAt(i + 1) == '.')
				return null;
		if (relativeFilepath.charAt(0) != '/')
			relativeFilepath = "/" + relativeFilepath;
		
		// File exists?
		File file = new File(settingsModel.getStarboxFolder() + relativeFilepath);
		if (!file.exists() || !file.canRead())
			return null;
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
	public File requestIndexData(String ip) {
		// Is accepted request IP?
		if (!isRequestAllowed(ip))
			return null;
		
		// Index file exists?
		File file = new File(FILEPATH_INDEXDATA);
		if (!file.exists() || !file.canRead())
			return null;
		return file;
	}
}
