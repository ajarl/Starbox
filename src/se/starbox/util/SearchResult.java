package se.starbox.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.JSONObject;
import se.starbox.models.SettingsModel;

public class SearchResult {
	
	// This manages how the JSON will be formatted.
	private String datePattern = "dd-MM-yyyy";
	
	private String name, url, filetype, timestamp, username;
	private int filesize;

	/**
	 * Default constructor.
	 */
	public SearchResult() {
		
		// SearchModel skickar inte med n�gra parametrar vid skapandet av SearchResult-objekt, t�nkte att det var dumt att �ndra 
	}
	
	/**
	 * Returns a JSON representation of this object.
	 * This method supress a warning because JSON is not type-safe.
	 * @return JSON- A JSON formatted string representing this object.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		
		json.put("name", this.getName());
		// Obs: fulhack nedan, varning
		if (this.getUrl().indexOf(':') < 2)
			json.put("url", "http://localhost:8080/starbox/file?file=" + this.getUrl().replace((new SettingsModel()).getStarboxFolder(), ""));
		else
			json.put("url", "http://" + this.getUrl().substring(0, this.getUrl().indexOf(':')) + ":8080/starbox/file?file=" + this.getUrl().substring(this.getUrl().indexOf(':') + 1));
		json.put("filetype", this.getFiletype());
		json.put("username", this.getUsername());
		
		// Format timestamp
		Date timeStamp = new Date(Long.valueOf(this.getTimestamp())); // s -> ms
		SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
		json.put("timestamp", formatter.format(timeStamp));
		
		// Format filesize.
		int fileSize = this.getFilesize();
		if (fileSize <= 1000)
			json.put("filesize", (this.getFilesize()) + "B");
		else if (fileSize <= 1000000)
			json.put("filesize", (this.getFilesize()/1000) + "KB");
		else if(filesize <= 1024*1000000 )
			json.put("filesize", (this.getFilesize()/1000000) + "MB");
		else if(filesize <= 1024*1012*1000000 )
			json.put("filesize", (this.getFilesize()/1000000000) + "GB");
		else 
			json.put("filesize", this.getFilesize());
		
		return json;
	}
	
	/**
	 * Get the name of this search result.
	 * @return String - The name of this search result.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this search result.
	 * @param name - The name of this search result.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the url of this search result.
	 * @return String - The url of this search result.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set the url of this search result.
	 * @param url - The url of this search result.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Get the doctype of this search result.
	 * @return String - The doctype of this search result.
	 */
	public String getFiletype() {
		return filetype;
	}

	/**
	 * Set the filetype of this search result.
	 * @param filetype - The filetype of this search result.
	 * @param filetype - The filetype of this search result.
	 */
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	
	/**
	 * Get the timestamp of this search result.
	 * @return String - The timestamp of this search result.
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Set the timestamp of this search result.
	 * @param timestamp - The timestamp of this search result.
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Get the filesize of this search result.
	 * @return int - The filesize of this search result.
	 */
	public int getFilesize() {
		return filesize;
	}

	/**
	 * Set the filesize of this search result.
	 * @param filesize - The filesize of this search result.
	 */
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	
	/** 
	 * Return the username 
	 * @return the username that owns this file
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
