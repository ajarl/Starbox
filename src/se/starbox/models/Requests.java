package se.starbox.models;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Requests {
	public static final String REQUEST_ADD = "addRequest";
	public static final String REQUEST_RESPONSE = "responseRequest";
	public static final String ATTRIBUTE_IP = "ip";
	public static final String ATTRIBUTE_EMAIL = "email";
	public static final String ATTRIBUTE_NAME = "name";
	
	static public String addRequest(String ip, String email, String name){
		String charset = "UTF-8";
		String request = "";
		try {
			request = String.format("action=%s&"+ATTRIBUTE_IP+
									"=%s&"+ATTRIBUTE_EMAIL+
									"=%s&"+ATTRIBUTE_NAME+"=%s",
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
	static public String responseRequest(String ip, String response, String email, String name){
		String charset = "UTF-8";
		String request = "";
		try {
			request = String.format("action=%s&"+
									ATTRIBUTE_IP+"=%s&" +
									"response=%s&"+
									ATTRIBUTE_EMAIL+"=%s&"+
									ATTRIBUTE_NAME+"=%s",
											URLEncoder.encode(REQUEST_RESPONSE, charset),
											URLEncoder.encode(ip, charset),
											URLEncoder.encode(response, charset),
											URLEncoder.encode(email, charset),
											URLEncoder.encode(name, charset)
											);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return request;
	}

}
