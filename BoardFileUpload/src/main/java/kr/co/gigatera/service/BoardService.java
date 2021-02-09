package kr.co.gigatera.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kr.co.gigatera.dto.ArguExtendDTO;
import kr.co.gigatera.dto.PasswordDTO;
import kr.co.gigatera.vo.BoardPreVO;
import kr.co.gigatera.vo.BoardUploadVO;
import kr.co.gigatera.vo.BoardVO;

public interface BoardService {
	
	//write
	public boolean write(BoardVO boardVO,ArrayList<BoardUploadVO> boardUploadVO,HttpServletRequest request) throws Exception;
	
	//list
	public List<BoardVO> list(BoardPreVO boardPreVO) throws Exception;
	
	//view
	public BoardVO view(Integer idx) throws Exception;
	
	//reply
	public boolean reply(BoardVO boardVO,ArrayList<BoardUploadVO> boardUploadVO,ArguExtendDTO arguExtendDTO, HttpServletRequest request) throws Exception;
	
	//password
	public String getPwd(Integer idx) throws Exception;
	
	//del
	public boolean del(String uploadPath, Integer idx) throws Exception;
	
	//modok
	public boolean modOk(BoardVO boardVO,ArrayList<BoardUploadVO> boardUploadVO,PasswordDTO passwordDTO, HttpServletRequest request) throws Exception;
	
	//upload
	public List<BoardUploadVO> uplist(Integer bidx) throws Exception;
	public void delup(String fileSaveName) throws Exception;
}
