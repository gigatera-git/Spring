package kr.co.gigatera.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.gigatera.vo.CustomUserDetails;

@Repository("userAuthDAO")
public class UserAuthDAO {
	
	@Autowired
    private SqlSessionTemplate sqlSession;
 
	public CustomUserDetails getUserById(String username) {
		return sqlSession.selectOne("user.selectUserById", username);
	}

	public void updateFailureCount(String username) {
		sqlSession.update("user.updateFailureCount", username);
	}
	
	public int checkFailureCount(String username) {
		return sqlSession.selectOne("user.checkFailureCount", username);
	}
	
	public void updateDisabled(String username) {
		sqlSession.update("user.updateUnenabled", username);
	}

	public void updateFailureCountReset(String username) {
		sqlSession.update("user.updateFailureCountReset", username);
	}

	public void updateNewAccessDate(String username) {
		sqlSession.update("user.updateAccessDate", username);
	}

}
