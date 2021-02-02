package kr.co.gigatera.dao;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.co.gigatera.vo.MemberVO;

@Repository("MemberDAO")
public class MemberDAO {
	@Inject
    private SqlSessionTemplate sqlSession;
	private static String namespace = "kr.co.gigatera.mapper.MemberMapper";
	
	public MemberVO selectById(String uid) {
    	return sqlSession.selectOne(namespace+".selectById",uid);
    }
}
