package kr.co.gigatera.dao;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.co.gigatera.command.WriteCommand;
import kr.co.gigatera.vo.BoardPreVO;
import kr.co.gigatera.vo.BoardVO;

@Repository("BoardDAO")
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
    private SqlSessionTemplate sqlSession;
	private static String namespace = "kr.co.gigatera.mapper.BoardMapper";
	
	
	@Override
	public Integer ref() throws Exception {
		return sqlSession.selectOne(namespace+".ref");
	}
	@Override
	public Integer write(BoardVO boardVO) throws Exception {
		return sqlSession.selectOne(namespace+".write",boardVO);	
	}
	
	
	@Override
	public Integer listPre(BoardPreVO boardPreVO) throws Exception {
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
	public Integer reply(BoardVO boardVO) throws Exception {
		return sqlSession.insert(namespace+".reply", boardVO);
	}
	@Override
	public String getPwd(Integer idx) throws Exception {
		return sqlSession.selectOne(namespace+".pwd",idx);
	}
	@Override
	public Integer del(Integer idx) throws Exception {
		return sqlSession.delete(namespace+".del",idx);
	}
	@Override
	public Integer modOk(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update(namespace+".modok",boardVO);
	}
	
	
}
