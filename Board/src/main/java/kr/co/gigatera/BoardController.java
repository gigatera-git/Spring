package kr.co.gigatera;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import kr.co.gigatera.command.ModCommand;
import kr.co.gigatera.command.PwdCommand;
import kr.co.gigatera.command.ReplyCommand;
import kr.co.gigatera.command.WriteCommand;
import kr.co.gigatera.dto.ArguDTO;
import kr.co.gigatera.dto.ArguExtendDTO;
import kr.co.gigatera.dto.PasswordDTO;
import kr.co.gigatera.service.BoardService;
import kr.co.gigatera.util.Proc;
import kr.co.gigatera.vo.BoardPreVO;
import kr.co.gigatera.vo.BoardVO;

@Controller
public class BoardController {
	
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Resource(name="BoardService")
	private BoardService boardService;
	
	@RequestMapping(value="write",method=RequestMethod.GET)
	public ModelAndView write(WriteCommand writeCommand) throws Exception {
		ModelAndView mav = new ModelAndView("write");
		mav.addObject("writeCommand",writeCommand);
		return mav;
	}
	
	@RequestMapping(value="writeok",method=RequestMethod.POST)
	public ModelAndView writeok(@Valid WriteCommand writeCommand, BindingResult bindingResult,
			BoardVO boardVO,
            HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		if(bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("write");
			return mav;
		}
		
		//check password
		boolean chk = writeCommand.isPwdSame();
		//System.out.println("chk : " + chk);
		if(!chk) {
			bindingResult.rejectValue("pwd2", "noMatch", "비밀번호가 일치하지 않습니다.");
			ModelAndView mav = new ModelAndView("write");
			return mav;
		}
		
		//encrypt password
		String encPwd = bcryptPasswordEncoder.encode(boardVO.getPwd());
		boardVO.setPwd(encPwd);
		//bcryptPasswordEncoder.matches(writeCommand.getPwd(), encPwd)
		
		String title = HtmlUtils.htmlEscape(boardVO.getTitle());
		String contents = HtmlUtils.htmlEscape(boardVO.getContents());
		boardVO.setTitle(title);
		boardVO.setContents(contents);
		
		//write ok
		int ref = boardService.ref();
		String reg_ip = Proc.getClientIp(request);
		boardVO.setRef(ref);
		boardVO.setReg_ip(reg_ip);
		boardService.write(boardVO);
		
		//redirect list after writing
		ModelAndView mav = new ModelAndView("redirect:list");
		return mav;
	}
	
	@RequestMapping(value="list")
	public ModelAndView list(BoardPreVO boardPreVO) throws Exception {
		String sv = HtmlUtils.htmlEscape(boardPreVO.getSearchVal());
		boardPreVO.setSearchVal(sv);
		//model.addAttribute("list",boardService.list(boardPreVO));
		ModelAndView mav = new ModelAndView("list");
		mav.addObject("list",boardService.list(boardPreVO));
		return mav;
	}
	
	@RequestMapping(value="view",method=RequestMethod.GET)
	public ModelAndView view(@RequestParam("idx") Integer idx,ArguDTO arguDTO) throws Exception {
		//model.addAttribute("view", boardService.view(idx,count_done));
		ModelAndView mav = new ModelAndView("view");
		mav.addObject("view",boardService.view(idx));
		return mav;
	}
	
	
	@RequestMapping(value="reply",method=RequestMethod.GET)
	public ModelAndView reply(ReplyCommand replyCommand, ArguExtendDTO arguExtendDTO) throws Exception {
		ModelAndView mav = new ModelAndView("reply");
		mav.addObject("replyCommand",replyCommand);
		//mav.addObject("test","TETETT");
		return mav;
	}
	@RequestMapping(value="replyok",method=RequestMethod.POST)
	public ModelAndView replyok(@Valid ReplyCommand replyCommand, BindingResult bindingResult,
			BoardVO boardVO, ArguExtendDTO arguExtendDTO,
			HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		if(bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("reply");
			return mav;
		}
		
		//check password
		boolean chk = replyCommand.isPwdSame();
		//System.out.println("chk : " + chk);
		if(!chk) {
			bindingResult.rejectValue("pwd2", "noMatch", "비밀번호가 일치하지 않습니다.");
			ModelAndView mav = new ModelAndView("reply");
			return mav;
		}
		
		//encrypt password
		String encPwd = bcryptPasswordEncoder.encode(boardVO.getPwd());
		boardVO.setPwd(encPwd);
		
		//reply pre
		int ref = arguExtendDTO.getRef();
		int re_step = arguExtendDTO.getRe_step();
		int re_lvl = arguExtendDTO.getRe_lvl();
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("ref", ref);
		map.put("re_step", re_step);
		boardService.replyPre(map);	
		
		String title = HtmlUtils.htmlEscape(boardVO.getTitle());
		String contents = HtmlUtils.htmlEscape(boardVO.getContents());
		boardVO.setTitle(title);
		boardVO.setContents(contents);
		
		//reply ok		
		String reg_ip = Proc.getClientIp(request);
		boardVO.setRef(ref);
		boardVO.setRe_step(re_step);
		boardVO.setRe_lvl(re_lvl+1);
		boardVO.setReg_ip(reg_ip+1);
		boardService.reply(boardVO);
		
		//System.out.println("boardVO.getIdx() : " + boardVO.getIdx()); //for last_insert_id()
		
		//redirect list after replying
		String qs = Proc.getQueryParam(arguExtendDTO.getIntPage(), arguExtendDTO.getSearchOpt(), arguExtendDTO.getSearchVal());
		ModelAndView mav = new ModelAndView("redirect:list"+qs);

		return mav;
		//return null;
	}

	@RequestMapping(value="pwd",method=RequestMethod.GET)
	public ModelAndView pwd(PwdCommand pwdCommand,PasswordDTO passwordDTO) throws Exception {
		ModelAndView mav = new ModelAndView("pwd");
		mav.addObject("pwdCommand",pwdCommand);
		return mav;
	}
	
	@RequestMapping(value="pwdok",method=RequestMethod.POST)
	public ModelAndView pwdok(@Valid PwdCommand pwdCommand,BindingResult bindingResult,
			PasswordDTO passwordDTO,
			HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		if (request.getHeader("referer")==null) {
			String qs = Proc.getQueryParam(passwordDTO.getIdx(), passwordDTO.getIntPage(), passwordDTO.getSearchOpt(), passwordDTO.getSearchVal());
			ModelAndView mav = new ModelAndView("redirect:view"+qs);
			return mav;
		}
		
		if(bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("pwd");
			return mav;
		}
		
		String encPwd = boardService.getPwd(passwordDTO.getIdx());
		boolean chk = bcryptPasswordEncoder.matches(passwordDTO.getPwd(), encPwd);
		if(!chk) {
			bindingResult.rejectValue("pwd", "noMatch", "비밀번호가 일치하지 않습니다.");
			ModelAndView mav = new ModelAndView("pwd");
			return mav;
		}
		
		if (passwordDTO.getModordel().equals("delok")) {
			int affectedRows = boardService.del(passwordDTO.getIdx());			
			if (affectedRows<1) {
				bindingResult.rejectValue("pwd", "noResult", "삭제중 에러가 발생하였습니다.");
				ModelAndView mav = new ModelAndView("pwd");
				return mav;
			}
			String qs = Proc.getQueryParam(passwordDTO.getIntPage(), passwordDTO.getSearchOpt(), passwordDTO.getSearchVal());
			ModelAndView mav = new ModelAndView("redirect:list"+qs);
			return mav;
		}
		if (passwordDTO.getModordel().equals("mod")) {
			String qs = Proc.getQueryParam(passwordDTO.getIdx(), passwordDTO.getIntPage(), passwordDTO.getSearchOpt(), passwordDTO.getSearchVal());
			ModelAndView mav = new ModelAndView("redirect:mod"+qs);
			return mav;
		}
		
		ModelAndView mav = new ModelAndView("list");
		return mav;
	}
	
	@RequestMapping(value="mod",method=RequestMethod.GET)
	public ModelAndView mod(ModCommand modCommand,ArguDTO arguDTO,HttpServletRequest request) throws Exception {
		
		if (request.getHeader("referer")==null) {
			String qs = Proc.getQueryParam(arguDTO.getIdx(), arguDTO.getIntPage(), arguDTO.getSearchOpt(), arguDTO.getSearchVal());
			ModelAndView mav = new ModelAndView("redirect:view"+qs);
			return mav;
		}
		
		ModelAndView mav = new ModelAndView("mod");		
		BoardVO boardVO = boardService.view(arguDTO.getIdx());
		
		modCommand.setUname(boardVO.getUname());
		modCommand.setTitle(boardVO.getTitle());
		modCommand.setContents(boardVO.getContents());
		mav.addObject("modCommand",modCommand);
		return mav;
	}
	
	@RequestMapping(value="modok",method=RequestMethod.POST)
	public ModelAndView modok(@Valid ModCommand modCommand,BindingResult bindingResult,
			BoardVO boardVO,PasswordDTO passwordDTO,
			HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		if(bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("pwd");
			return mav;
		}
		
		String mod_ip = Proc.getClientIp(request);
		boardVO.setMod_ip(mod_ip);
		int affectedRows = boardService.modOK(boardVO);			
		if (affectedRows<1) {
			bindingResult.rejectValue("title", "noResult", "수정중 에러가 발생하였습니다.");
			ModelAndView mav = new ModelAndView("mod");
			return mav;
		}
		String qs = Proc.getQueryParam(passwordDTO.getIdx(), passwordDTO.getIntPage(), passwordDTO.getSearchOpt(), passwordDTO.getSearchVal());	
		ModelAndView mav = new ModelAndView("redirect:view"+qs);
		return mav;
	}
}
