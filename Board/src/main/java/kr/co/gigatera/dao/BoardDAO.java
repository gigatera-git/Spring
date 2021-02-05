package kr.co.gigatera.dao;

import java.util.HashMap;
import java.util.List;

import kr.co.gigatera.command.WriteCommand;
import kr.co.gigatera.vo.BoardPreVO;
import kr.co.gigatera.vo.BoardVO;

public interface BoardDAO {
	
	//write
	public Integer ref() throws Exception;
	public Integer write(BoardVO boardVO) throws Exception;
	
	//list
	public Integer listPre(BoardPreVO boardPreVO) throws Exception;
	public List<BoardVO> list(BoardPreVO boardPreVO) throws Exception;
	
	//view
	public BoardVO view(Integer idx) throws Exception;
	public void viewClick(Integer idx) throws Exception;
	
	//reply
	public void replyPre(HashMap<String,Object> map) throws Exception;
	public Integer reply(BoardVO boardVO) throws Exception;
	
	//password
	public String getPwd(Integer idx) throws Exception;
	
	//del
	public Integer del(Integer idx) throws Exception;
	
	
	//modok
	public Integer modOk(BoardVO boardVO) throws Exception;
}
