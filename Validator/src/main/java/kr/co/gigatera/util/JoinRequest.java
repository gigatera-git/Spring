package kr.co.gigatera.util;

public class JoinRequest {  //kind of DTO(data transfer object)

	private String email;
    private String uid;
    private String uname;
    private String pwd;
    private String pwd2;
    
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
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
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPwd2() {
		return pwd2;
	}
	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}
    
	public boolean isPwdSame() {
	    return pwd.equals(pwd2);
	}

}
	