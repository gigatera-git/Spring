package kr.co.gigatera.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.gigatera.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAO {
	
	@Autowired
    private SqlSessionTemplate sqlSession;
	
	public void insertNewbie(MemberVO memberVo) {
		sqlSession.insert("member.insertNewbie", memberVo);
	}
}
