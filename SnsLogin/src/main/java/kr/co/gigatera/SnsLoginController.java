package kr.co.gigatera;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;

import kr.co.gigatera.snslogin.NaverLoginUtil;

@Controller
public class SnsLoginController {
	
	@Inject
	NaverLoginUtil naverLoginUtil;
	
	@RequestMapping(value="/naver/login",method=RequestMethod.GET)
	public ModelAndView naverLogin(HttpSession session) throws Exception {
		
		String naverAuthUrl = naverLoginUtil.getAuthorizationUrl(session);
		ModelAndView mav = new ModelAndView("/naver/login");
		mav.addObject("url", naverAuthUrl);
		return mav;
		
	}
	
	@RequestMapping(value="/naver/callback",method=RequestMethod.GET)
	public ModelAndView naverCallback(@RequestParam String state , @RequestParam String code,HttpSession session) throws Exception {
		
		//JSONParser parser = new JSONParser();
		OAuth2AccessToken accessToken = naverLoginUtil.getAccessToken(session, code, state);
		String userProfile = naverLoginUtil.getUserProfile(accessToken);
		//userProfile -> resultcode==00 이면
		//로그인 & db저장 처리

		ModelAndView mav = new ModelAndView("/naver/callback");
		mav.addObject("userProfile",userProfile);
		return mav;
	}
}
