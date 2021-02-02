package kr.co.gigatera.util;

import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Entity;

@Entity
public class LoginCommand {   // kind of DTO(data transfer object)
	
	@NotEmpty(message="아이디를 입력하세요.")
    private String uid;
 
    @NotEmpty(message="비밀번호를 입력하세요.")
    private String pwd;
    
    private boolean savedUid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public boolean isSavedUid() {
		return savedUid;
	}

	public void setSavedUid(boolean savedUid) {
		this.savedUid = savedUid;
	}
    

}
