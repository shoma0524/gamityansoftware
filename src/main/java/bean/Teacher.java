package bean;

import java.io.Serializable;

public class Teacher extends User implements Serializable {
	
	private String id;	//ログインid
	private String password;	//ログインパスワード
	private String name;	//名前
	private School school;	//学校情報
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name =name;
	}
	
	public School getSchool() {
		return school;
	}
	
	public void setSchool(School school) {
		this.school = school;
	}
}