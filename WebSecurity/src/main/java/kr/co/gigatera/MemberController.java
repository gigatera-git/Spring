package kr.co.gigatera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.gigatera.dao.MemberDAO;
import kr.co.gigatera.vo.MemberVO;

@Controller
public class MemberController {
	
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;

	@Autowired
	MemberDAO memberDAO;
	
	@RequestMapping(value="/join",method=RequestMethod.GET) 
	public void join() {
		//
		System.out.println("join.jsp");
		
//		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder(10);
//		String returnValue1 = bcryptPasswordEncoder.encode("password");
//		String returnValue2 = bcryptPasswordEncoder.encode("password");
//		String returnValue3 = bcryptPasswordEncoder.encode("password");
//		 
//		System.out.println("returnValue1 : " + returnValue1);
//		System.out.println("returnValue2 : " + returnValue2);
//		System.out.println("returnValue3 : " + returnValue3);
//		 
//		System.out.println("============================================================================");
//		 
//		System.out.println("matches() result : " + bcryptPasswordEncoder.matches("password", returnValue1));
//		System.out.println("matches() result : " + bcryptPasswordEncoder.matches("password", returnValue2));
//		System.out.println("matches() result : " + bcryptPasswordEncoder.matches("password", returnValue3));

	}
	
	@RequestMapping(value="/joinRes",method=RequestMethod.POST) 
	public String joinOk(MemberVO memberVO, Model model) {
		
		System.out.println("joinRes.jsp");
		
		//BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder(10);
		String encPwd = bcryptPasswordEncoder.encode(memberVO.getPassword());
		
		System.out.println("pwd : "+  memberVO.getPassword());
		System.out.println("encPwd : "+  encPwd);
		System.out.println("matches() result : " + bcryptPasswordEncoder.matches(memberVO.getPassword(), encPwd));
		memberVO.setPassword(encPwd);
		memberDAO.insertNewbie(memberVO);
//		
//		System.out.println(memberVO);
		
		return "joinRes";
	}
	
}
