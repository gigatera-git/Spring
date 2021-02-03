package kr.co.gigatera.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.gigatera.util.CookieUtil;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		//return super.preHandle(request, response, handler);
		
		String savedUid = CookieUtil.getCookieValue(request, "savedUid");
		if (savedUid==null || savedUid.length()<=0) {
			
			saveDest(request);
			
			response.sendRedirect("login");
			return false; //** important! not to return to controller
		}
		
		return true;
	}

	private void saveDest(HttpServletRequest req) throws Exception {
		
		String uri = req.getRequestURI();
		String query = req.getQueryString();
		if (query==null || query.equals("null")) {
			query = "";
		} else {
			query = "?" + query;
		}
		if (req.getMethod().equals("GET")) {
			req.getSession().setAttribute("dest", uri+query);
		} else if (req.getMethod().equals("POST")) {
			req.getSession().setAttribute("dest", req.getHeader("referer"));
		}
		
		Object dest = req.getSession().getAttribute("dest");
		//System.out.println("a-dest : " + (String)dest);
		
	}
	
	
}	
