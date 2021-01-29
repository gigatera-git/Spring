package kr.co.gigatera.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.gigatera.util.JoinRequest;
import kr.co.gigatera.vo.MemberVO;

@Repository("MemberDAO")
public class MemberDAO {
	
	//@Inject
	//private SqlSession session;
	//private static String namespace = "kr.co.gigatera.mapper.MemberMapper";
	
	//@Autowired
	@Inject
    private SqlSessionTemplate sqlSession;
	private static String namespace = "kr.co.gigatera.mapper.MemberMapper";
	
	public MemberVO selectByEmail(String email) {
		//return session.selectOne("MemberMapper.selectByEmail",email);
		MemberVO mV = sqlSession.selectOne(namespace+".selectByEmail",email);
		//MemberVO mV = sqlSession.selectOne("MemberMapper.selectByEmail",email);
		//System.out.println("sqlSession: " + sqlSession);
		//return new MemberVO();
		return mV;
    }
 
    public MemberVO selectById(String uid) {
    	return sqlSession.selectOne(namespace+".selectById",uid);
    }
 
    public void insertUser(JoinRequest joinReq) {
    	sqlSession.insert(namespace+".join",joinReq);
    }

}
