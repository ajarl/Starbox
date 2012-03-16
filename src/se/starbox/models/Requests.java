package se.starbox.models;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Requests {
	public static String REQUEST_ADD = "addRequest";
	public static String REQUEST_RESPONSE = "responseRequest";
	
	static public String addRequest(String ip, String email, String name){
		String charset = "UTF-8";
		String request = "";
		try {
			request = String.format("action=%s&ip=%s&email=%s&name=%s",
											URLEncoder.encode(REQUEST_ADD, charset),
											URLEncoder.encode(ip, charset),
											URLEncoder.encode(email, charset),
											URLEncoder.encode(name, charset)
											);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return request;
	}
	static public String responseRequest(String ip, String response){
		String charset = "UTF-8";
		String request = "";
		try {
			request = String.format("action=%s&ip=%s&response=%s",
											URLEncoder.encode(REQUEST_RESPONSE, charset),
											URLEncoder.encode(ip, charset),
											URLEncoder.encode(response, charset)
											);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return request;
	}

}
