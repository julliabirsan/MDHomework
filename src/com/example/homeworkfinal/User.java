package com.example.homeworkfinal;

public class User {
	private int id;
	private String name;
	private String username;
	private String email;
	private String password;
	private String facebookProf;
	private String sex;
	private int Varsta;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getVarsta() {
		return Varsta;
	}
	public void setVarsta(int varsta) {
		Varsta = varsta;
	}
	
	
	public String getFacebookProf() {
		return facebookProf;
	}
	public void setFacebookProf(String facebookProf) {
		this.facebookProf = facebookProf;
	}
	public User(){
		
	}
}
