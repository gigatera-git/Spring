package kr.co.gigatera.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.co.gigatera.dao.MemberDAO;
import kr.co.gigatera.exception.IdPasswordNotMatchingException;
import kr.co.gigatera.util.AuthInfo;
import kr.co.gigatera.util.LoginCommand;
import kr.co.gigatera.vo.MemberVO;

@Service("MemberService")
public class MemberServiceImpl implements MemberService {
	
	@Resource(name="MemberDAO")
	private MemberDAO memberDAO;
	
	@Override
	public AuthInfo authInfo(LoginCommand loginCommand) throws Exception {
		// TODO Auto-generated method stub
		//return null;
		
		MemberVO member = memberDAO.selectById(loginCommand.getUid());
        if(member == null) {
            throw new IdPasswordNotMatchingException();
        }
        if(!member.isPwdSame(loginCommand.getPwd())) {
            throw new IdPasswordNotMatchingException();
        }
        System.out.println("member.getGrade() : " + member.getGrade());
        return new AuthInfo(member.getUid(), member.getUname(), member.getGrade());
	}

}
