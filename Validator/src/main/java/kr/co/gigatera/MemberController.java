package kr.co.gigatera;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gigatera.exception.AlreadyExistingEmailException;
import kr.co.gigatera.exception.AlreadyExistingIdException;
import kr.co.gigatera.service.UserService;
import kr.co.gigatera.util.JoinRequest;
import kr.co.gigatera.util.JoinRequestValidator;

@Controller
public class MemberController {
	
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	public void index() throws Exception {
		
	}
	
	@RequestMapping(value="step1",method=RequestMethod.GET)
	public String step1() throws Exception {
		return "step1";
	}
	
	@RequestMapping(value="step2",method=RequestMethod.POST)
	public ModelAndView step2(@RequestParam(value="agree",defaultValue="false") Boolean agree) throws Exception {
		if (agree) {
			ModelAndView mav = new ModelAndView("step2");
			mav.addObject("joinRequest", new JoinRequest());
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("step1");
			return mav;
		}
	}
	
	@RequestMapping(value="step3",method=RequestMethod.POST)
	public ModelAndView step3(JoinRequest joinReq,Errors errors) throws Exception {
		
		new JoinRequestValidator().validate(joinReq, errors);
		
		if (errors.hasErrors()) {
			ModelAndView mav = new ModelAndView("step2");
			return mav;
		} else {
			try {
				userService.join(joinReq);
	        } catch (AlreadyExistingEmailException e) {
	            errors.rejectValue("email", "duplicate", "이미 가입된 이메일입니다.");
	            ModelAndView mav = new ModelAndView("step2");
	            return mav;
	        } catch (AlreadyExistingIdException e) {
	            errors.rejectValue("uid", "duplicate", "이미 가입된 아이디입니다.");
	            ModelAndView mav = new ModelAndView("step2");
	            return mav;
	        }
	        ModelAndView mav = new ModelAndView("step3");
	        return mav;
		}
	}
	
}
