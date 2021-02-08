package kr.co.gigatera.command;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Entity;

@Entity
public class WriteCommand {
	
	@Column
    //@NotEmpty(message="이름을 입력해주세요.")
    @Pattern(regexp="\\S{2,8}", message="이름을 입력하세요(2~8자).")
    private String uname;
	
	@Column
    //@NotEmpty(message="제목을 입력해주세요.")
    @Pattern(regexp="\\S{2,50}", message="이름을 입력하세요(2~50자).")
    private String title;

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
	
	@Column
	@NotEmpty(message="내용을 입력해주세요.")
	private String contents;
	
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public boolean isPwdSame() {
	    return pwd.equals(pwd2);
	}
}
