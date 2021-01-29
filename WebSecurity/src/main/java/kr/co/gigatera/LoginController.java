package kr.co.gigatera;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	

	@RequestMapping(value="/page",method=RequestMethod.GET)
	public void page() {
		//
	}
	
	@RequestMapping(value="/user/page",method=RequestMethod.GET)
	public void user_page() {
		//
	}
	
	@RequestMapping(value="/member/page",method=RequestMethod.GET)
	public void member_page() {
		//
	}
	
	@RequestMapping(value="/admin/page",method=RequestMethod.GET)
	public void admin_page() {
		//
	}
	
	
	@RequestMapping(value="/loginPage") //method 미작성시 get/post 자동으로 모두 가능
	public void loginPage() {
		//
	}

	
	
	@RequestMapping(value="/access_denied_page")
    public String accessDeniedPage() throws Exception {
        return "/access_denied_page";
    }

}
