package kr.co.gigatera.dao;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.co.gigatera.vo.BoardPreVO;
import kr.co.gigatera.vo.BoardUploadVO;
import kr.co.gigatera.vo.BoardVO;

@Repository("BoardDAO")
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
    private SqlSessionTemplate sqlSession;
	private static String namespace = "kr.co.gigatera.mapper.FileUploadBoardMapper";
	
	
	@Override
	public Integer ref() throws Exception {
		return sqlSession.selectOne(namespace+".ref");
	}
	@Override
	public void write(BoardVO boardVO) throws Exception {
		sqlSession.insert(namespace+".write",boardVO);	
	}
	
	
	@Override
	public Integer listpre(BoardPreVO boardPreVO) throws Exception {
		return sqlSession.selectOne(namespace+".listpre",boardPreVO);
	}
	@Override
	public List<BoardVO> list(BoardPreVO boardPreVO) throws Exception {
		return sqlSession.selectList(namespace+".list",boardPreVO);
	}
	
	@Override
	public BoardVO view(Integer idx) throws Exception {
		return sqlSession.selectOne(namespace+".view",idx);
	}
	@Override
	public void viewClick(Integer idx) throws Exception {
		sqlSession.update(namespace+".viewClick",idx);
	}
	
	@Override
	public void replyPre(HashMap<String,Object> map) throws Exception {
		sqlSession.update(namespace+".replyPre", map);
	}
	@Override
	public void reply(BoardVO boardVO) throws Exception {
		sqlSession.insert(namespace+".reply", boardVO);
	}
	@Override
	public String getPwd(Integer idx) throws Exception {
		return sqlSession.selectOne(namespace+".pwd",idx);
	}
	@Override
	public void del(Integer idx) throws Exception {
		sqlSession.delete(namespace+".del",idx);
	}
	@Override
	public void modOk(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(namespace+".modok",boardVO);
	}

	
	//upload
	@Override
	public void writeup(BoardUploadVO boardUploadVO) throws Exception {
		sqlSession.insert(namespace+".writeup",boardUploadVO);
	}
	@Override
	public List<BoardUploadVO> uplist(Integer bidx) throws Exception {
		return sqlSession.selectList(namespace+".uplist",bidx);
	}
	@Override
	public void delup(Integer bidx) throws Exception {
		sqlSession.delete(namespace+".delup", bidx);
	}
	@Override
	public void delup(String fileSaveName) throws Exception {
		sqlSession.delete(namespace+".delup_fsn", fileSaveName);
	}
	@Override
	public void replyup(BoardUploadVO boardUploadVO) throws Exception {
		sqlSession.insert(namespace+".replyup", boardUploadVO);
	}
	@Override
	public void modokup(BoardUploadVO boardUploadVO) throws Exception {
		sqlSession.insert(namespace+".modokup", boardUploadVO);
	}
	
}
