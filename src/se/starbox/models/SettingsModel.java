package se.starbox.models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Model for getting and changing local user settings.
 * Keeps local variables and writes changes to UserSettings.xml.
 * @author Anders
 *
 */
public class SettingsModel {
	private static final String XML_PATH = "UserSettings.xml";
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<UserSettings xmlns=\"starbox\">\n";
	private static final String XML_FOOTER = "</UserSettings>";
	
	private static final String DEFAULT_STARBOX_FOLDER = "C:\\Documents\\";
	private static final String DEFAULT_DISPLAY_NAME = "default display name";
	private static final String DEFAULT_EMAIL = "";
	private static final int DEFAULT_INDEX_UPDATE_INTERVAL = 900;
	
	private String starboxFolder;
	private String displayName;
	private String email;
	private int indexUpdateInterval; // in seconds
	
	/**
	 * Creates a new SettingsModel that immediately attempts to read from UserSettings.xml and populate local fields.
	 * If UserSettings.xml does not exist, fields get default values as given by private constants in this class.
	 */
	public SettingsModel() {
		initModel();
	}
	
	/**
	 * Loads and parses XML-document and updates local fields with the values. If the XML-document does not exist, the default values are used.
	 */
	private void initModel() {
		File xml = new File(XML_PATH);
		
		// If UserSettings.xml does not exist, create it and write the default values to it, then return
		if (!xml.exists()) {
			try {
				xml.createNewFile();
				starboxFolder = DEFAULT_STARBOX_FOLDER;
				displayName = DEFAULT_DISPLAY_NAME;
				email = DEFAULT_EMAIL;
				indexUpdateInterval = DEFAULT_INDEX_UPDATE_INTERVAL;
				writeToFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		// Otherwise, we found a UserSettings.xml and can start parsing the XML document
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document d = db.parse(xml);
			
			starboxFolder = (d.getElementsByTagName("StarboxFolder")).item(0).getTextContent();
			displayName = (d.getElementsByTagName("DisplayName")).item(0).getTextContent();
			email = (d.getElementsByTagName("Email")).item(0).getTextContent();
			// If something other than a number is stored, just take the default value
			try {
				indexUpdateInterval = Integer.parseInt((d.getElementsByTagName("IndexUpdateInterval")).item(0).getTextContent());
			} catch (NumberFormatException e) {
				indexUpdateInterval = DEFAULT_INDEX_UPDATE_INTERVAL;
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes current local values to UserSettings.xml
	 */
	private void writeToFile() {
		File xml = new File(XML_PATH);
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(xml));
			w.write(XML_HEADER);
			w.write("\t<StarboxFolder><![CDATA[" + starboxFolder + "]]></StarboxFolder>\n");
			w.write("\t<DisplayName><![CDATA[" + displayName + "]]></DisplayName>\n");
			w.write("\t<Email><![CDATA[" + email + "]]></Email>\n");
			w.write("\t<IndexUpdateInterval><![CDATA[" + indexUpdateInterval + "]]></IndexUpdateInterval>\n");
			w.write(XML_FOOTER);
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * Gets user's IP address and returns it as a String
	 * TODO: Not implemented yet
	 * @return The user's IP address
	 */
	public String getIP() {
		// TODO Get IP address and return it
		return null;
	}
	
	/**
	 * Shuts down program cleanly
	 */
	public void shutDown() {
		System.exit(0);
		// TODO: Needs to be fixed for Tomcat use and also making sure all changes have been saved, check current downloads are done etc.
	}
	
	/**
	 * Gets starbox folder from UserSettings.xml
	 * @return The user's starbox folder as a String
	 */
	public String getStarboxFolder() {
		return starboxFolder;
	}
	
	/**
	 * Gets user's display name from UserSettings.xml
	 * @return The user's display name as a String
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * Gets user's email from UserSettings.xml
	 * @return The user's email as a String
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Gets user's index update interval (in seconds) from UserSettings.xml
	 * @return The number of seconds between each interval as an integer value
	 */
	public int getIndexUpdateInterval() {
		return indexUpdateInterval;
	}
	
	/**
	 * Updates user's starbox folder setting and writes to UserSettings.xml
	 * @param path The new starbox folder as a String
	 */
	public void setStarboxFolder(String path) {
		starboxFolder = path;
		writeToFile();
	}
	
	/**
	 * Updates user's display name setting and writes to UserSettings.xml
	 * @param displayName The new display name as a String
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		writeToFile();
	}
	
	/**
	 * Updates user's email address and writes to UserSettings.xml
	 * @param email The new email as a String
	 */
	public void setEmail(String email) {
		this.email = email;
		writeToFile();
	}
	
	/**
	 * Update's user's index update interval and writes to UserSettings.xml, also updates OpenPipeline job to use the new interval (not implemented yet)
	 * @param seconds The new interval in seconds as an integer
	 */
	public void setIndexUpdateInterval(int seconds) {
		indexUpdateInterval = seconds;
		writeToFile();
		
		// TODO Update OpenPipeline job with new interval
	}
	
	
}
