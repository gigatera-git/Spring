package kr.co.gigatera.util;

public class AuthInfo {
	
	private String uid;
    private String uname;
    private int grade;
    
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	public AuthInfo(String uid, String uname, int grade) {
        this.uid = uid;
        this.uname = uname;
        this.grade = grade;
    }
}
