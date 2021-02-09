package kr.co.gigatera.dao;

import java.util.HashMap;
import java.util.List;

import kr.co.gigatera.vo.BoardPreVO;
import kr.co.gigatera.vo.BoardUploadVO;
import kr.co.gigatera.vo.BoardVO;

public interface BoardDAO {
	
	//write
	public Integer ref() throws Exception;
	public void write(BoardVO boardVO) throws Exception;
	
	//list
	public Integer listpre(BoardPreVO boardPreVO) throws Exception;
	public List<BoardVO> list(BoardPreVO boardPreVO) throws Exception;
	
	//view
	public BoardVO view(Integer idx) throws Exception;
	public void viewClick(Integer idx) throws Exception;
	
	//reply
	public void replyPre(HashMap<String,Object> map) throws Exception;
	public void reply(BoardVO boardVO) throws Exception;
	
	//password
	public String getPwd(Integer idx) throws Exception;
	
	//del
	public void del(Integer idx) throws Exception;
	
	
	//modok
	public void modOk(BoardVO boardVO) throws Exception;
	
	//upload
	public void writeup(BoardUploadVO boardUploadVO) throws Exception;
	public List<BoardUploadVO> uplist(Integer bidx) throws Exception;
	public void delup(Integer bidx) throws Exception;
	public void delup(String fileSaveName) throws Exception;
	public void replyup(BoardUploadVO boardUploadVO) throws Exception;	
	public void modokup(BoardUploadVO boardUploadVO) throws Exception;	
}
