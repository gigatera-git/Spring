package kr.co.gigatera.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
	public static String  getCookieValue(HttpServletRequest request, String name) throws Exception
	{ 
		String _value = new String("");
		
		if (name!=null && name.length()>0)
		{
			Cookie[]  cookies = request.getCookies();

			if (cookies != null)
			{
				for (int i=0;i<cookies.length;i++)
				{
					if (cookies[i].getName().equals(name))
					{
						_value = cookies[i].getValue();
						break;
					}
				}
			}
		}

		return _value;
	}
}
