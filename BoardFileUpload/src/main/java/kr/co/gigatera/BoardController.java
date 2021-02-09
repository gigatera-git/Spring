package kr.co.gigatera;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
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
import kr.co.gigatera.util.Upload;
import kr.co.gigatera.util.UploadCK;
import kr.co.gigatera.vo.BoardPreVO;
import kr.co.gigatera.vo.BoardUploadVO;
import kr.co.gigatera.vo.BoardVO;

@Controller
public class BoardController {

	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;

	@Resource(name = "BoardService")
	private BoardService boardService;

	@Resource(name = "uploadPath")
	private String uploadPath;

	@RequestMapping(value = "write", method = RequestMethod.GET)
	public ModelAndView write(WriteCommand writeCommand) throws Exception {
		ModelAndView mav = new ModelAndView("write");
		mav.addObject("writeCommand", writeCommand);
		return mav;
	}

	@RequestMapping(value = "writeok", method = RequestMethod.POST)
	public ModelAndView writeok(@Valid WriteCommand writeCommand, BindingResult bindingResult, 
			BoardVO boardVO, MultipartRequest multi,
			HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {

		if (bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("write");
			return mav;
		}

		// check password
		boolean chk = writeCommand.isPwdSame();
		// System.out.println("chk : " + chk);
		if (!chk) {
			bindingResult.rejectValue("pwd2", "noMatch", "비밀번호가 일치하지 않습니다.");
			ModelAndView mav = new ModelAndView("write");
			return mav;
		}
		
		List<MultipartFile> files = multi.getFiles("files");
		ArrayList<BoardUploadVO> boardUploadVO = Upload.uploadFiles(uploadPath, files);

		boardService.write(boardVO,boardUploadVO,request);

		// redirect list after writing
		ModelAndView mav = new ModelAndView("redirect:list");
		return mav;
	}

	@RequestMapping(value = "list")
	public ModelAndView list(BoardPreVO boardPreVO) throws Exception {
		String sv = HtmlUtils.htmlEscape(boardPreVO.getSearchVal());
		boardPreVO.setSearchVal(sv);
		// model.addAttribute("list",boardService.list(boardPreVO));
		ModelAndView mav = new ModelAndView("list");
		mav.addObject("list", boardService.list(boardPreVO));
		return mav;
	}

	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam("idx") Integer idx, ArguDTO arguDTO) throws Exception {
		// model.addAttribute("view", boardService.view(idx,count_done));
		ModelAndView mav = new ModelAndView("view");
		BoardVO boardVO = boardService.view(idx);
		boardVO.setContents(HtmlUtils.htmlUnescape(boardVO.getContents()));
		mav.addObject("view", boardVO);
		
		List<BoardUploadVO> uplist = boardService.uplist(idx);
		mav.addObject("uplist",uplist);
	
		return mav;
	}

	@RequestMapping(value = "reply", method = RequestMethod.GET)
	public ModelAndView reply(ReplyCommand replyCommand, ArguExtendDTO arguExtendDTO) throws Exception {
		ModelAndView mav = new ModelAndView("reply");
		mav.addObject("replyCommand", replyCommand);
		// mav.addObject("test","TETETT");
		return mav;
	}

	@RequestMapping(value = "replyok", method = RequestMethod.POST)
	public ModelAndView replyok(@Valid ReplyCommand replyCommand, BindingResult bindingResult, 
			MultipartRequest multi, BoardVO boardVO, ArguExtendDTO arguExtendDTO,
			HttpSession session, HttpServletResponse response, HttpServletRequest request)
			throws Exception {

		if (bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("reply");
			return mav;
		}

		// check password
		boolean chk = replyCommand.isPwdSame();
		// System.out.println("chk : " + chk);
		if (!chk) {
			bindingResult.rejectValue("pwd2", "noMatch", "비밀번호가 일치하지 않습니다.");
			ModelAndView mav = new ModelAndView("reply");
			return mav;
		}
		
		List<MultipartFile> files = multi.getFiles("files");
		ArrayList<BoardUploadVO> boardUploadVO = Upload.uploadFiles(uploadPath, files);
		
		boolean res=boardService.reply(boardVO,boardUploadVO,arguExtendDTO,request);

		// redirect list after replying
		String qs = Proc.getQueryParam(arguExtendDTO.getIntPage(), arguExtendDTO.getSearchOpt(),
				arguExtendDTO.getSearchVal());
		ModelAndView mav = new ModelAndView("redirect:list" + qs);

		return mav;
		// return null;
	}

	@RequestMapping(value = "pwd", method = RequestMethod.GET)
	public ModelAndView pwd(PwdCommand pwdCommand, PasswordDTO passwordDTO) throws Exception {
		ModelAndView mav = new ModelAndView("pwd");
		mav.addObject("pwdCommand", pwdCommand);
		return mav;
	}

	@RequestMapping(value = "pwdok", method = RequestMethod.POST)
	public ModelAndView pwdok(@Valid PwdCommand pwdCommand, BindingResult bindingResult, PasswordDTO passwordDTO,
			HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {

		if (request.getHeader("referer") == null) {
			String qs = Proc.getQueryParam(passwordDTO.getIdx(), passwordDTO.getIntPage(), passwordDTO.getSearchOpt(),
					passwordDTO.getSearchVal());
			ModelAndView mav = new ModelAndView("redirect:view" + qs);
			return mav;
		}

		if (bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("pwd");
			return mav;
		}

		String encPwd = boardService.getPwd(passwordDTO.getIdx());
		boolean chk = bcryptPasswordEncoder.matches(passwordDTO.getPwd(), encPwd);
		if (!chk) {
			bindingResult.rejectValue("pwd", "noMatch", "비밀번호가 일치하지 않습니다.");
			ModelAndView mav = new ModelAndView("pwd");
			return mav;
		}

		if (passwordDTO.getModordel().equals("delok")) {
			
			boolean affectedRows = boardService.del(uploadPath, passwordDTO.getIdx());
			if (!affectedRows) {
				bindingResult.rejectValue("pwd", "noResult", "삭제중 에러가 발생하였습니다.");
				ModelAndView mav = new ModelAndView("pwd");
				return mav;
			}
			String qs = Proc.getQueryParam(passwordDTO.getIntPage(), passwordDTO.getSearchOpt(),
					passwordDTO.getSearchVal());
			ModelAndView mav = new ModelAndView("redirect:list" + qs);
			return mav;
		}
		
		
		if (passwordDTO.getModordel().equals("mod")) {
			String qs = Proc.getQueryParam(passwordDTO.getIdx(), passwordDTO.getIntPage(), passwordDTO.getSearchOpt(),
					passwordDTO.getSearchVal());
			ModelAndView mav = new ModelAndView("redirect:mod" + qs);
			return mav;
		}

		ModelAndView mav = new ModelAndView("list");
		return mav;
	}

	@RequestMapping(value = "mod", method = RequestMethod.GET)
	public ModelAndView mod(ModCommand modCommand, ArguDTO arguDTO, HttpServletRequest request) throws Exception {

		if (request.getHeader("referer") == null) {
			String qs = Proc.getQueryParam(arguDTO.getIdx(), arguDTO.getIntPage(), arguDTO.getSearchOpt(),
					arguDTO.getSearchVal());
			ModelAndView mav = new ModelAndView("redirect:view" + qs);
			return mav;
		}

		ModelAndView mav = new ModelAndView("mod");
		BoardVO boardVO = boardService.view(arguDTO.getIdx());

		modCommand.setUname(boardVO.getUname());
		modCommand.setTitle(boardVO.getTitle());
		modCommand.setContents(HtmlUtils.htmlUnescape(boardVO.getContents()));
		mav.addObject("modCommand", modCommand);
		
		List<BoardUploadVO> uplist = boardService.uplist(arguDTO.getIdx());
		mav.addObject("uplist", uplist);
		
		return mav;
	}

	@RequestMapping(value = "modok", method = RequestMethod.POST)
	public ModelAndView modok(@Valid ModCommand modCommand, BindingResult bindingResult, 
			MultipartRequest multi, BoardVO boardVO,PasswordDTO passwordDTO, 
			HttpSession session, HttpServletResponse response, HttpServletRequest request)
			throws Exception {

		if (bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("pwd");
			return mav;
		}
		
		
		//delete previous files if there is new one
		try {
			if (request.getParameterValues("attachDels")!=null) {
				for(int i=0; i < request.getParameterValues("attachDels").length; i++){
					String delFilePath = (uploadPath + "/" + request.getParameterValues("attachDels")[i]);
					String [] savedName = delFilePath.split("/");
					String fileSaveName = savedName[savedName.length-1];
					
					delFilePath = delFilePath.replace("/", File.separator);
					File file = new File(delFilePath);
					if (file.exists()) {
						file.delete();
						boardService.delup(fileSaveName);	
					}
					file = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//upload , save for new files
		List<MultipartFile> files = multi.getFiles("files");
		ArrayList<BoardUploadVO> boardUploadVO = Upload.uploadFiles(uploadPath, files);
		boardService.modOk(boardVO, boardUploadVO,passwordDTO, request);
		
		//redirect after working
		String qs = Proc.getQueryParam(passwordDTO.getIdx(), passwordDTO.getIntPage(), passwordDTO.getSearchOpt(),
				passwordDTO.getSearchVal());
		ModelAndView mav = new ModelAndView("redirect:view" + qs);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "uploadCkeditor", method = RequestMethod.POST)
	public Map<String, Object> uploadCkeditor(MultipartFile upload) throws Exception { // ** argument must be upload ***

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1 = UploadCK.uploadFiles(uploadPath, upload);

		return map1;
	}
	
	@RequestMapping(value="download",method=RequestMethod.GET) 
	public ModelAndView  download(@RequestParam HashMap<Object, Object> params, ModelAndView mv) throws Exception {
		String savedFile = uploadPath + File.separator + params.get("filePath").toString().replace("/",File.separator) + File.separator + params.get("fileName").toString();
		File file = new File(savedFile);

		mv.setViewName("downloadView");
		mv.addObject("downloadFile", file);
		return mv;
	}
}
