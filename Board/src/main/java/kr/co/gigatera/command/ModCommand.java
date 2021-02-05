package kr.co.gigatera.command;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Entity;

@Entity
public class ModCommand {
	@Column
    //@NotEmpty(message="이름을 입력해주세요.")
    @Pattern(regexp="\\S{2,8}", message="이름을 입력하세요(2~8자).")
    private String uname;
	
	@Column
    //@NotEmpty(message="제목을 입력해주세요.")
    @Pattern(regexp="\\S{2,50}", message="이름을 입력하세요(2~50자).")
    private String title;
	
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

	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}

}
