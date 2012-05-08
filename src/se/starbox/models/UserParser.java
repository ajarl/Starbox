package se.starbox.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Small parser class to parse users.xml
 * @author jens, walter
 *
 */
public class UserParser {
	private BufferedReader in;
	private static final String IP_REGEXP = "(<IP>)(.*)(</IP>)";
	private static final String EMAIL_REGEXP = "(<Email>)(.*)(</Email>)";
	private static final String NAME_REGEXP = "(<DisplayName>)(.*)(</DisplayName>)";
	private static final String GROUP_REGEXP = "(<GroupName>)(.*)(</GroupName>)";
	private static final String STATUS_REGEXP = "(<Status>)(.*)(</Status>)";
	private static final String IP_TAG = "</*IP>";
	private static final String NAME_TAG = "</*DisplayName>";
	private static final String EMAIL_TAG = "</*Email>";
	private static final String GROUP_TAG = "</*GroupName>";
	private static final String STATUS_TAG = "</*Status>";

	public UserParser(String path){
		String sep = System.getProperty("file.separator");
		boolean windows = sep.equals("\\");
		if(windows)
			path = path.replaceAll("/", "\\\\");
		String fileDirPath = path.substring(0, path.lastIndexOf(sep))+sep;
		File fileDir = new File(fileDirPath);
		fileDir.mkdirs();
		File xmlFile = new File(path);
		try {
			xmlFile.createNewFile();
			in = new BufferedReader(new FileReader(xmlFile));
			parserInit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void parserInit() throws IOException{
		in.readLine();in.readLine();

	}

	/**
	 * Get the next user from the xml document
	 * @return null if at the end of the document, else the next user in the document.
	 */
	public User getNext(){
		try{
			String email = "";
			String name = "";
			String ip ="";
			String group = "";
			String status ="";
			String line = in.readLine();
			while(line != null){
				line = line.trim();
				if(line.contains("<User>")){
				}
				if(Pattern.matches(EMAIL_REGEXP, line)){
					Pattern reg = Pattern.compile(EMAIL_TAG);
					String[] splitLine =  reg.split(line);
					if(splitLine.length>1){
					email = splitLine[1];
					}
				}
				if(Pattern.matches(NAME_REGEXP, line)){
					Pattern reg = Pattern.compile(NAME_TAG);
					String[] splitLine =  reg.split(line);
					if(splitLine.length>1){
						name = splitLine[1];
					}
				}
				if(Pattern.matches(IP_REGEXP, line)){
					Pattern reg = Pattern.compile(IP_TAG);
					String[] splitLine =  reg.split(line);
					if(splitLine.length>1){
						ip = splitLine[1];
					}
				}
				if(Pattern.matches(GROUP_REGEXP, line)){
					Pattern reg = Pattern.compile(GROUP_TAG);
					String[] splitLine =  reg.split(line);
					if(splitLine.length>1){
						group = splitLine[1];
					}
				}
				if(Pattern.matches(STATUS_REGEXP, line)){
					Pattern reg = Pattern.compile(STATUS_TAG);
					String[] splitLine =  reg.split(line);
					if(splitLine.length>1){
						status = splitLine[1];
					}
				}
				if(line.contains("</User>")){
					User u = new User(ip,status,email,name,group);
					return u;
				}
				if(line.contains("</Users>")){
					return null;
				}
				line = in.readLine();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Get all users from the users.xml document
	 * @return a List of users
	 */
	public List<User> getAll(){
		ArrayList<User> userList = new ArrayList<User>();
		User curUser = getNext();
		while(curUser != null){
			userList.add(curUser);
			curUser = getNext();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userList;

	}

}