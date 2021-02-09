package kr.co.gigatera.vo;

import java.sql.Timestamp;
import java.util.Date;

public class BoardUploadVO {
	
	private Integer bidx;
	private String fileRealName;
	private String fileSaveName;
	private String fileType;
	private String fileSize;
	private String reg_ip;
	private String mod_ip;
	private Timestamp reg_date;
	private Timestamp mod_date;
	private boolean isImage;
	
	public Integer getBidx() {
		return bidx;
	}
	public void setBidx(Integer bidx) {
		this.bidx = bidx;
	}
	public String getFileRealName() {
		return fileRealName;
	}
	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}
	public String getFileSaveName() {
		return fileSaveName;
	}
	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getReg_ip() {
		return reg_ip;
	}
	public void setReg_ip(String reg_ip) {
		this.reg_ip = reg_ip;
	}
	public String getMod_ip() {
		return mod_ip;
	}
	public void setMod_ip(String mod_ip) {
		this.mod_ip = mod_ip;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	public Date getMod_date() {
		return mod_date;
	}
	public void setMod_date(Timestamp mod_date) {
		this.mod_date = mod_date;
	}
	public boolean isImage() {
		return isImage;
	}
	public void setImage(boolean isImage) {
		this.isImage = isImage;
	}
	
}
