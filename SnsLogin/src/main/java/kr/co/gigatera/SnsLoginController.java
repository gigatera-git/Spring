package kr.co.gigatera;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.scribejava.core.model.OAuth2AccessToken;

import kr.co.gigatera.snslogin.GoogleLoginUtil;
import kr.co.gigatera.snslogin.GoogleOAuthRequest;
import kr.co.gigatera.snslogin.GoogleOAuthResponse;
import kr.co.gigatera.snslogin.KakaoLoginUtil;
import kr.co.gigatera.snslogin.NaverLoginUtil;

@Controller
public class SnsLoginController {
	
	//for naver
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

		ModelAndView mav = new ModelAndView("/naver/callback");
		mav.addObject("userProfile",userProfile);
		return mav;
	}
	
	//for google
	@Inject
	GoogleLoginUtil googleLoginUtil;
	

	@RequestMapping(value="/google/login",method=RequestMethod.GET)
	public ModelAndView googleLogin(HttpSession session) throws Exception {
		
		String url = googleLoginUtil.getAuthorizationUrl();

	  	ModelAndView mav = new ModelAndView("/google/login");
		mav.addObject("url", url);
		return mav;
	}
	
	@RequestMapping(value="/google/callback",method=RequestMethod.GET)
	public ModelAndView googleCallback(@RequestParam String code) throws Exception {
		
		Map<String,String> userInfo = googleLoginUtil.getGoogleUserInfo(code);
		
		ModelAndView mav = new ModelAndView("/google/callback");
		mav.addObject("body",userInfo);
		return mav;
	}
	
	// for kakao
	@Inject
	KakaoLoginUtil kakaoLoginUtil;
	
	@RequestMapping(value="/kakao/login",method=RequestMethod.GET)
	public ModelAndView kakaoLogin(HttpSession session) throws Exception {
		
		String url = kakaoLoginUtil.getAuthorizationUrl();

	  	ModelAndView mav = new ModelAndView("/kakao/login");
		mav.addObject("url", url);
		return mav;
	}
	
	@RequestMapping(value="/kakao/callback",method=RequestMethod.GET)
	public ModelAndView kakaoCallback(@RequestParam String code) throws Exception {
		JsonNode userInfo = kakaoLoginUtil.getKakaoUserInfo(code);
		
		//String id = userInfo.get("id").toString();
		//String email = userInfo.get("kaccount_email").toString();
		//String image = userInfo.get("properties").get("profile_image").toString();
		//String nickname = userInfo.get("properties").get("nickname").toString();
		//userInfo 정보로 로그인 및 db저장 처리

		ModelAndView mav = new ModelAndView("/kakao/callback");
		mav.addObject("body", userInfo);
		return mav;
	}
}
