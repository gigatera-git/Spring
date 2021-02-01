package kr.co.gigatera.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.co.gigatera.dao.MemberDAO;
import kr.co.gigatera.exception.AlreadyExistingEmailException;
import kr.co.gigatera.exception.AlreadyExistingIdException;
import kr.co.gigatera.util.JoinRequest;
import kr.co.gigatera.vo.MemberVO;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource(name="MemberDAO")
	private MemberDAO memberDAO;

	@Override
	public void join(JoinRequest joinReq) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("joinReq.getEmail() : " + joinReq.getEmail());
		MemberVO email  = memberDAO.selectByEmail(joinReq.getEmail());

        if(email!=null) {
            throw new AlreadyExistingEmailException(joinReq.getEmail()+" is duplicate email.");
        }
        MemberVO id = memberDAO.selectById(joinReq.getUid());
        if(id!=null) {
            throw new AlreadyExistingIdException(joinReq.getUid()+" is duplicate id.");
        }
        memberDAO.insertUser(joinReq);
	}
	
}
