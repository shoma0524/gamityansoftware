package bean;

import java.io.Serializable;

public class Student implements Serializable {
	
	private String no;	//学生番号
	private String name;	//学生名
	private int entYear;	//入学年度
	private String classNum;	//クラス番号
	private boolean isAttend;	//在学フラグ
	private School school;	//学校情報
	
	public String getNo() {
		return no;
	}
	
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name =name;
	}
	
	public int getEntYear() {
		return entYear;
	}
	
	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}
	
	public String getClassNum() {
		return classNum;
	}
	
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	
	public boolean getIsAttend() {
		return isAttend;
	}
	
	public void setIsAttend(boolean isAttend)  {
		this.isAttend = isAttend;
	}
	
	public School getSchool() {
		return school;
	}
	
	public void setSchool(School school) {
		this.school = school;
	}
}