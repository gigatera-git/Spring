package kr.co.gigatera;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gigatera.exception.IdPasswordNotMatchingException;
import kr.co.gigatera.service.MemberService;
import kr.co.gigatera.util.AuthInfo;
import kr.co.gigatera.util.LoginCommand;

@Controller
public class LoginController {
	
	@Resource(name="MemberService")
	private MemberService memeberService;
	
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index() throws Exception {
		return "index";
	}

	@RequestMapping(value="login",method=RequestMethod.GET)
	public ModelAndView login(LoginCommand loginCommand) throws Exception{
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}
	
	@RequestMapping(value="loginok", method=RequestMethod.POST)
	public ModelAndView loginok(@Valid LoginCommand loginCommand, BindingResult bindingResult,
            HttpSession session, HttpServletResponse response) throws Exception {
		
		if(bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("login");
			return mav;
		}
		
		try {
			AuthInfo authInfo = memeberService.authInfo(loginCommand);
			session.setAttribute("authInfo", authInfo);
			session.setAttribute("savedUid", loginCommand.isSavedUid());
		} catch (IdPasswordNotMatchingException e) {
			bindingResult.rejectValue("pwd", "notMatch", "아이디와 비밀번호가 올바르지 않습니다.");
			ModelAndView mav = new ModelAndView("login");
			return mav;
		}

		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
	
	

	@RequestMapping("logout")
    public ModelAndView logout(HttpSession session, HttpServletResponse response) {
		
		Cookie savedUid = new Cookie("savedUid", null);
		savedUid.setMaxAge(0); // 쿠키의 expiration 타임을 0으로 하여 없앤다.
		savedUid.setPath("/"); // 모든 경로에서 삭제 됬음을 알린다.
	    response.addCookie(savedUid);
	    
        session.invalidate();
        ModelAndView mav = new ModelAndView("redirect:/index");
        return mav;
        
    }
	
	@RequestMapping(value="view",method=RequestMethod.GET)
	public String view() throws Exception{
		return "view";
	}
		
}
