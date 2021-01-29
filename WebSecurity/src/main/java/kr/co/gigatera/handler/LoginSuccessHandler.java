package kr.co.gigatera.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import kr.co.gigatera.service.UserService;
import kr.co.gigatera.vo.CustomUserDetails;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Resource(name="userSer")
	private UserService userSer;

	
	private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();


	private String loginidname;
	private String defaultUrl;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	        Authentication authentication) throws IOException, ServletException {
		
		String username = request.getParameter(loginidname);
	    userSer.resetFailureCnt(username);

		clearAuthenticationAttributes(request);
		resultRedirectStrategy(request, response, authentication);

	}
	
	public String getLoginidname() {
	    return loginidname;
	}
	
	public void setLoginidname(String loginidname) {
	    this.loginidname = loginidname;
	}
	
	public String getDefaultUrl() {
	    return defaultUrl;
	}
	
	public void setDefaultUrl(String defaultUrl) {
	    this.defaultUrl = defaultUrl;
	}

	
	protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	    Object principal = auth.getPrincipal();
//	    CustomUserDetails userDetails = (CustomUserDetails)principal;
//
//	    String name = "";
//	    List<GrantedAuthority> authorities = new ArrayList<>();
//	    if(principal != null) {
//	        name = auth.getName();
//	        authorities = (List<GrantedAuthority>) auth.getAuthorities(); //권한별 분기할떄 필요
//	    }
        
        SavedRequest savedRequest = requestCache.getRequest(request, response);        
        if(savedRequest!=null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStratgy.sendRedirect(request, response, targetUrl);
        } else {
            redirectStratgy.sendRedirect(request, response, defaultUrl);
        }
        
    }
	
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null) return;
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
