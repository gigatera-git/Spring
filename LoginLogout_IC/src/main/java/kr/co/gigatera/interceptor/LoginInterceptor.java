package kr.co.gigatera.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.gigatera.util.AuthInfo;
import kr.co.gigatera.util.CookieUtil;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		//return super.preHandle(request, response, handler);
		
		String savedUid = CookieUtil.getCookieValue(request, "savedUid");
		if (savedUid==null || savedUid.length()<=0) {
			Cookie loginCookie = new Cookie("savedUid", null);
			loginCookie.setMaxAge(0); 
			loginCookie.setPath("/"); 
		    response.addCookie(loginCookie);
		    
		    HttpSession session = request.getSession();
		    //session.invalidate(); //watch out to protect session values you use
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		//super.postHandle(request, response, handler, modelAndView);
		
		HttpSession session = request.getSession();
		Object authInfo1 = session.getAttribute("authInfo");
		Object isSavedUid1 = session.getAttribute("savedUid");
		 
		if (authInfo1!=null) {
			AuthInfo authInfo = (AuthInfo)authInfo1;
			Boolean isSavedUid = (Boolean)isSavedUid1;
			
			Cookie savedUid = new Cookie("savedUid", authInfo.getUid());
			if (isSavedUid) {
				savedUid.setMaxAge(60*60*24); //to save for 1 day
			}
			savedUid.setPath("/"); 
		    response.addCookie(savedUid);
		    
		    session.setAttribute("savedUid", authInfo.getUid());
		    
		    Object dest = session.getAttribute("dest");
		    //System.out.println("l-dest : " + (String)dest);
		    //response.sendRedirect("index");
		    response.sendRedirect(dest!=null?(String)dest:"index");
		}
	}
	
	
}
