package kr.co.gigatera.util;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Entity;

@Entity
public class JoinRequest {  //kind of DTO(data transfer object)
	
	@Column
    @NotEmpty(message="이메일을 입력해주세요.")
    @Email(message="이메일 형식이 올바르지 않습니다.")
	private String email;
	
	@Column
	@Pattern(regexp="^[A-za-z]{6,12}", message="아이디를 입력하세요(영어 6~12자).")  
    private String uid;
	
	@Column
    //@NotEmpty(message="이름을 입력해주세요.")
    @Pattern(regexp="\\S{2,8}", message="이름을 입력하세요(2~8자).")
    private String uname;
	
	@Column
    //@NotEmpty(message="비밀번호를 입력해주세요.")
    //@Size(min=4, max=12, message="비밀번호를  입력하세요(4~12자).")
	@Pattern(regexp="(?=.*\\d{1,50})(?=.*[~`!@#$%\\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$", message="비밀번호를 입력하세요(숫자,특수문자 각1회이상/영문2개이상/총8자리이상).")  
    private String pwd;
	
	@Column
    //@NotEmpty(message="비밀번호를 입력해주세요.")
    //@Size(min=4, max=12, message="비밀번호 확인을  입력하세요(4~12자).")
	@Pattern(regexp="(?=.*\\d{1,50})(?=.*[~`!@#$%\\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$", message="비밀번호를 입력하세요(숫자,특수문자 각1회이상/영문2개이상/총8자리이상).")  
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
	