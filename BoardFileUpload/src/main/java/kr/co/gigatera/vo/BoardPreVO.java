package kr.co.gigatera.vo;

//for tbl_board -> paging , searching

public class BoardPreVO 
{

	private Integer intPage;
	private Integer intStartPage;
	private Integer intPageSize;
	private Integer intBlockPage;
	private Integer intTotalCount;
	private Integer intTotalPage;

	private String SearchOpt;
	private String SearchVal;
	
	private String argv;
	
	public Integer getIntPage() {
		return intPage;
	}
	public void setIntPage(Integer intPage) {
		this.intPage = intPage;
	}
	public Integer getIntPageSize() {
		return intPageSize;
	}
	public void setIntPageSize(Integer intPageSize) {  
		this.intPageSize = intPageSize;
	}
	public Integer getIntStartPage() {
		return intStartPage;
	}
	public void setIntStartPage(Integer intStartPage) {
		this.intStartPage = intStartPage;
	}
	public Integer getIntBlockPage() {
		return intBlockPage;
	}
	public void setIntBlockPage(Integer intBlockPage) {
		this.intBlockPage = intBlockPage;
	}
	public Integer getIntTotalCount() {
		return intTotalCount;
	}
	public void setIntTotalCount(Integer intTotalCount) {
		this.intTotalCount = intTotalCount;
	}
	public Integer getIntTotalPage() {
		return intTotalPage;
	}
	public void setIntTotalPage(Integer intTotalPage) {
		this.intTotalPage = intTotalPage;
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
	
	
	public String getArgv() {
		return argv;
	}
	public void setArgv(String argv) {
		this.argv = argv;
	}
	
	
	public BoardPreVO() throws Exception {
	}
	
	public String Paging() throws Exception {
		String paging = "";
		if (this.intPage>1) {
			paging += "<a href='list?intPage=1&"+argv+"'><img src='/resources/images/common/btn_page01.gif' align='absmiddle'></a>";
		} else {
			paging += "<img src='/resources/images/common/btn_page01.gif' align='absmiddle'>";
		}
		paging +="&nbsp;";

		Integer intTemp = ((this.intPage - 1) / this.intBlockPage) * this.intBlockPage + 1;

		if (intTemp==1) {
			paging += "<img src='/resources/images/common/btn_page02.gif' align='absmiddle'>";
		} else {
			paging += "<a href='/list?intPage=" + String.valueOf(intTemp - this.intBlockPage) + "&"+argv+"'><img src='/resources/images/common/btn_page02.gif' align='absmiddle'></a>";
		}
		paging +="&nbsp;";


		Integer intLoop = 1;
		while (intLoop <= this.intBlockPage && intTemp <= this.intTotalPage) {
			if (intTemp==this.intPage) {
				paging +="<b>" + String.valueOf(intTemp) +"</b>";
			} else {
				paging +="<span><a href='list?intPage=" + String.valueOf(intTemp) + "&"+argv+"'>"+ String.valueOf(intTemp) +"</a></span>";
			}
			paging +="&nbsp;";

			intTemp++;
			intLoop++;
		}
		paging +="&nbsp;";

		if (intTemp>this.intTotalPage) {
			paging += "<img src='/resources/images/common/btn_page03.gif' align='absmiddle'>";
		} else {
			paging += "<a href='list?intPage=" + String.valueOf(intTemp) + "&"+argv+"'><img src='/resources/images/common/btn_page03.gif' align='absmiddle'></a>";
		}
		paging +="&nbsp;";

		if (this.intPage<this.intTotalPage) {
			paging += "<a href='list?intPage=" + String.valueOf(intTotalPage) + "&"+argv+"'><img src='/resources/images/common/btn_page04.gif' align='absmiddle'></a> ";
		} else {
			paging += "<img src='/resources/images/common/btn_page04.gif' align='absmiddle'>";
		}

		return paging;
	}
	
	
	public Integer getArticleNum(Integer cnt) {
		return (getIntTotalCount()-((getIntPage() - 1) * getIntPageSize()))-cnt;
	}
}
