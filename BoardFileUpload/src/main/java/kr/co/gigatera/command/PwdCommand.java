package kr.co.gigatera.command;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Entity;

@Entity
public class PwdCommand {
	@Column
    @NotEmpty(message="비밀번호를 입력해주세요.")
    private String pwd;

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
}
