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
	public ModelAndView login(LoginCommand loginCommand, @CookieValue(value="savedUid",required=false) Cookie savedUid, HttpServletRequest req) throws Exception{
		if(savedUid!=null) {
            loginCommand.setUid(savedUid.getValue());
            loginCommand.setSavedUid(true);
        }
		
	String dest = req.getHeader("referer");
	req.getSession().setAttribute("dest", null);
        if (dest!=null) {
        	req.getSession().setAttribute("dest", dest);
        }
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("loginCommand", loginCommand);
        return mav;
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public ModelAndView loginOk(@Valid LoginCommand loginCommand, BindingResult bindingResult,
            HttpSession session, HttpServletResponse response) throws Exception {
		
		//System.out.println("dest : " + session.getAttribute("dest"));
		if(bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("login");
			return mav;
		}
		
		try {
		
			AuthInfo authInfo = memeberService.authInfo(loginCommand);
			session.setAttribute("authInfo", authInfo);
		
			Cookie savedUid = new Cookie("savedUid", loginCommand.getUid());
			savedUid.setPath("/");
			if (loginCommand.isSavedUid()) {
				savedUid.setMaxAge(60*60*24); //to save for 1 day
			} else {
				savedUid.setMaxAge(0); //for removing
			}
			response.addCookie(savedUid);
		
			} catch (IdPasswordNotMatchingException e) {
				bindingResult.rejectValue("pwd", "notMatch", "아이디와 비밀번호가 올바르지 않습니다.");
				ModelAndView mav = new ModelAndView("login");
				return mav;
		}
		
		Object dest = session.getAttribute("dest");
		ModelAndView mav = new ModelAndView((dest==null)?"redirect:/index":"redirect:"+(String)dest);
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
		
}
