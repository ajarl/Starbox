package se.starbox.util;

import org.json.simple.JSONObject;

public class SearchResult {
	
	private String name;
	private String url;

	/**
	 * Default constructor.
	 */
	public SearchResult() {
		
	}
	
	/**
	 * Returns a JSON representation of this object.
	 * This method supress a warning because JSON is not type-safe.
	 * @return JSON- A JSON formatted string representing this object.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
	
		// TODO: Här saknas det fält.
		json.put("name", this.getName());
		json.put("url", this.getUrl());
		
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
}
