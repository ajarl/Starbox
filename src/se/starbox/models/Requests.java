package models;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Requests {
	
	static public String addRequest(String ip, String email, String name, String group,String status){
		String charset = "UTF-8";
		String request = "";
		try {
			request = String.format("requestType=%sip=%s&email=%s&name=%s&group=%s&status=%s",
											URLEncoder.encode("addUser", charset),
											URLEncoder.encode(ip, charset),
											URLEncoder.encode(email, charset),
											URLEncoder.encode(name, charset),
											URLEncoder.encode(group, charset),
											URLEncoder.encode(status, charset)
											);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return request;
	}

}
