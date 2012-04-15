package se.starbox.util;

import org.json.simple.JSONObject;

public class SearchResult {
	
	private String name;


	/**
	 * Default constructor.
	 */
	public SearchResult() {
		
	}
	
	/**
	 * Returns a JSON representation of this object.
	 * @return JSON- A JSON formatted string representing this object.
	 */
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
	
		// TODO: Här saknas det fält.
		json.put("name", this.getName());
		
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
}
