package kr.co.gigatera.dto;

public class ArguDTO {
	private Integer idx;
	private Integer intPage;
	private String SearchOpt;
	private String SearchVal;
	
	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public Integer getIntPage() {
		return intPage;
	}

	public void setIntPage(Integer intPage) {
		this.intPage = intPage;
	}

	public String getSearchOpt() {
		return SearchOpt;
	}

	public void setSearchOpt(String searchOpt) {
		SearchOpt = searchOpt;
	}

	public String getSearchVal() {
		return SearchVal;
	}

	public void setSearchVal(String searchVal) {
		SearchVal = searchVal;
	}
}
