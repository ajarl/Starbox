package se.starbox.models;

public class User implements Comparable<User> {
	private String name;
	private String email;
	private String ip;
	private String status;
	private String group;

	public User(String ip, String status,String email,String name,String group){
		this.setName(name);
		this.setEmail(email);
		this.setIp(ip);
		this.setStatus(status);
		this.setGroup(group);
	}

	public User(String ip, String status, String email,String name){
		this(ip,status,email,name,"");
	}
	public User(String ip,String status){
		this(ip,status,"","");
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int compareTo(User otherUser){
		boolean hasHigherPrio = points(otherUser.getStatus()) > points(status);
		if(hasHigherPrio){
			return -1;
		}
		boolean hasSamePrio = points(otherUser.getStatus()) == points(status);
		if(hasSamePrio){
			if(otherUser.getName().equals("") | name.equals("")){
				return 0;
			}
			char otherFirstLetter = otherUser.getName().charAt(0);
			char myFirstLetter = name.charAt(0);
			if(otherFirstLetter < myFirstLetter){
				return -1;
			}else if(otherFirstLetter > myFirstLetter)
				return 1;
			else{
				return 0;
			}
		} else{
			return 1;
		}
	}
	private int points(String status){
		if(status.equals(UserModel.STATE_PENDING)){
			return 4;
		} else if(status.equals(UserModel.STATE_ACCEPTED)){
			return 3;
		} else if(status.equals(UserModel.STATE_SENT)){
			return 2;
		} else{
			return 1;
		}
	}

}