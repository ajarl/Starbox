package se.starbox.models;

public class User {
	private String name;
	private String email;
	private String ip;
	private String status;
	private String group;

	public User(String ip, String email,String name,String group, String status){
		this.setName(name);
		this.setEmail(email);
		this.setIp(ip);
		this.setStatus(status);
		this.setGroup(group);
	}
	public User(String ip, String email,String name,String status){
		this(ip,email,name,"",status);
	}

	public User(String ip, String email,String name){
		this(ip,email,name,"","");
	}
	public User(String ip,String email){
		this(ip,email,"");
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

}