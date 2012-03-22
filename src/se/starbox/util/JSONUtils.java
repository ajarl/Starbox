package se.starbox.util;

import se.starbox.models.User;

public class JSONUtils {
	
	public static String getJSONUserHeaderFormatted(){
		return "{\"users\": {\n";
	}
	public static String getJSONUserHeader(){
		return "{\"users\": {";
	}
	public static String getJSONUserFooter(){
		return "}}";
	}
	public static String getJSONUserFooterFormatted(){
		return "\t}\n}\n";
	}
	public static String userToJSONFormatted(User u){
		StringBuilder user = new StringBuilder();
		user.append("\t\"user\": [\n");
		String jsonFluff = "\t\t{\"value: \"";
		user.append(jsonFluff+"IP\", \""+u.getIp()+"\"},\n");
		user.append(jsonFluff+"Name\", \""+u.getName()+"\"},\n");
		user.append(jsonFluff+"Email\", \""+u.getEmail()+"\"},\n");
		user.append(jsonFluff+"Status\", \""+u.getStatus()+"\"},\n");
		user.append(jsonFluff+"Group\", \""+u.getGroup()+"\"}\n");
		user.append("\t]\n");
		return user.toString();
		
	}
	public static String userToJSON(User u){
		StringBuilder user = new StringBuilder();
		user.append("\"user\": [");
		String jsonFluff = "{\"value: \"";
		user.append(jsonFluff+"IP\", \""+u.getIp()+"\"},");
		user.append(jsonFluff+"Name\", \""+u.getName()+"\"},");
		user.append(jsonFluff+"Email\", \""+u.getEmail()+"\"},");
		user.append(jsonFluff+"Status\", \""+u.getStatus()+"\"},");
		user.append(jsonFluff+"Group\", \""+u.getGroup()+"\"}");
		user.append("]");
		return user.toString();
	}

}
