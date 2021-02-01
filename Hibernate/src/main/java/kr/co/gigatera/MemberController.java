package kr.co.gigatera;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gigatera.exception.AlreadyExistingEmailException;
import kr.co.gigatera.exception.AlreadyExistingIdException;
import kr.co.gigatera.service.UserService;
import kr.co.gigatera.util.JoinRequest;

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
	public ModelAndView step3(@Valid JoinRequest joinReq, BindingResult bindingResult) throws Exception {
		
		//@Valid 어노테이션으로 인해, 검사작동        
		if(bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("step2");
			return mav;
		}
		
		//비밀번호 검사
		boolean chk = joinReq.isPwdSame();
		if(!chk) {
			bindingResult.rejectValue("pwd2", "noMatch", "비밀번호가 일치하지 않습니다.");
			ModelAndView mav = new ModelAndView("step2");
			return mav;
		}
		
		try {
			userService.join(joinReq);
        } catch (AlreadyExistingEmailException e) {
        	bindingResult.rejectValue("email", "duplicate", "이미 가입된 이메일입니다.");
            ModelAndView mav = new ModelAndView("step2");
            return mav;
        } catch (AlreadyExistingIdException e) {
        	bindingResult.rejectValue("uid", "duplicate", "이미 가입된 아이디입니다.");
            ModelAndView mav = new ModelAndView("step2");
            return mav;
        }
        ModelAndView mav = new ModelAndView("step3");
        return mav;
        
	}
	
}
