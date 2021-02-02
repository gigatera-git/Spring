package kr.co.gigatera.service;

import kr.co.gigatera.util.AuthInfo;
import kr.co.gigatera.util.LoginCommand;

public interface MemberService {
	public AuthInfo authInfo(LoginCommand loginCommand) throws Exception;
}
