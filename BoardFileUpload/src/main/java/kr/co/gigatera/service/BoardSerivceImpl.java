package kr.co.gigatera.service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import kr.co.gigatera.dao.BoardDAO;
import kr.co.gigatera.dto.ArguExtendDTO;
import kr.co.gigatera.dto.PasswordDTO;
import kr.co.gigatera.util.ContextUtil;
import kr.co.gigatera.util.CookieUtil;
import kr.co.gigatera.util.Proc;
import kr.co.gigatera.vo.BoardPreVO;
import kr.co.gigatera.vo.BoardUploadVO;
import kr.co.gigatera.vo.BoardVO;

@Service("BoardService")
public class BoardSerivceImpl implements BoardService {
	
	@Resource(name="BoardDAO")
	private BoardDAO boardDAO;
	
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public boolean write(BoardVO boardVO, ArrayList<BoardUploadVO> boardUploadVO, HttpServletRequest request) throws Exception {
		
		boolean re = true;
		
		try {
			int ref = boardDAO.ref();
			String reg_ip = Proc.getClientIp(request);
			
			String title = HtmlUtils.htmlEscape(boardVO.getTitle());
			String contents = HtmlUtils.htmlEscape(boardVO.getContents());
			boardVO.setTitle(title);
			boardVO.setContents(contents);
			
			String encPwd = bcryptPasswordEncoder.encode(boardVO.getPwd());
			boardVO.setPwd(encPwd);
			boardVO.setRef(ref);
			boardVO.setReg_ip(reg_ip);
			boardDAO.write(boardVO);
			
			System.out.println("boardVO.getIdx : " + boardVO.getIdx());
			
			for (BoardUploadVO upvo:boardUploadVO) {
				upvo.setReg_ip(reg_ip);
				upvo.setBidx(boardVO.getIdx());
				boardDAO.writeup(upvo);
			}
		} catch (Exception e) {
			re = false;
		}
		return re;

	}

	@Override
	public List<BoardVO> list(BoardPreVO boardPreVO) throws Exception {
		if (boardPreVO.getIntPage()==null || boardPreVO.getIntPage()<1) {
			boardPreVO.setIntPage(1);
		}
		
		boardPreVO.setIntPageSize(10);
		boardPreVO.setIntBlockPage(10);
		
		boardPreVO.setIntStartPage((boardPreVO.getIntPage()-1)*boardPreVO.getIntPageSize());
		
		System.out.println(boardPreVO.getIntPage());
		
		boardPreVO.setIntTotalCount(boardDAO.listpre(boardPreVO));
		boardPreVO.setIntTotalPage((int)Math.ceil((boardPreVO.getIntTotalCount()+0.0)/boardPreVO.getIntPageSize()));
		
		if (boardPreVO.getSearchOpt()==null || boardPreVO.getSearchOpt().length()<1) {
			boardPreVO.setSearchOpt("");
		}
		if (boardPreVO.getSearchVal()==null || boardPreVO.getSearchVal().length()<1) {
			boardPreVO.setSearchVal("");
		}
		
		String argv = "SearchOpt="+boardPreVO.getSearchOpt()+"&SearchVal="+boardPreVO.getSearchVal();
		boardPreVO.setArgv(argv);

		return boardDAO.list(boardPreVO);
	}
	
	
	@Override
	public BoardVO view(Integer idx) throws Exception {
		HttpServletRequest req = ContextUtil.getRequest();
		String countDoneVal = CookieUtil.getCookieValue(req, "count_done");
		
		if (!(String.valueOf(idx).equals(countDoneVal))) {
			boardDAO.viewClick(idx);
			Cookie count_done = new Cookie("count_done",idx.toString());
			HttpServletResponse res = ContextUtil.getResponse();
			res.addCookie(count_done);
		}
		BoardVO boardVO = boardDAO.view(idx);
		//System.out.println("title : " + boardVO.getTitle());
		//System.out.println("contents : " + boardVO.getContents());
		return boardVO;
	}
	

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public boolean reply(BoardVO boardVO, ArrayList<BoardUploadVO> boardUploadVO,ArguExtendDTO arguExtendDTO, HttpServletRequest request) throws Exception {
		
		boolean re = true;
		
		try {
			int ref = arguExtendDTO.getRef();
			int re_step = arguExtendDTO.getRe_step();
			int re_lvl = arguExtendDTO.getRe_lvl();
			
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("ref", ref);
			map.put("re_step", re_step);
			boardDAO.replyPre(map);	
			
			String reg_ip = Proc.getClientIp(request);
			
			// encrypt password
			String encPwd = bcryptPasswordEncoder.encode(boardVO.getPwd());
			boardVO.setPwd(encPwd);
			
			String title = HtmlUtils.htmlEscape(boardVO.getTitle());
			String contents = HtmlUtils.htmlEscape(boardVO.getContents());
			boardVO.setTitle(title);
			boardVO.setContents(contents);
			
			boardVO.setRef(ref);
			boardVO.setRe_step(re_step+1);
			boardVO.setRe_lvl(re_lvl+1);
			boardVO.setReg_ip(reg_ip);
			boardDAO.reply(boardVO);
			
			//----------
			for (BoardUploadVO upvo:boardUploadVO) {
				upvo.setReg_ip(reg_ip);
				upvo.setReg_date(Proc.getDateTime());
				upvo.setBidx(boardVO.getIdx());
				boardDAO.replyup(upvo);
			}
		} catch (Exception e) {
			re = false;
		}
		return re;

	}

	@Override
	public String getPwd(Integer idx) throws Exception {
		
		return boardDAO.getPwd(idx);
	}

	@Transactional
	@Override
	public boolean del(String uploadPath, Integer idx) throws Exception {

		boolean res = true;
		try {
			
			List<BoardUploadVO> boardUploadVO = boardDAO.uplist(idx); //sequence 0 ***
			for (BoardUploadVO buvo:boardUploadVO) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String ymd = df.format(buvo.getReg_date());
				ymd = ymd.replace("-", File.separator);
				
				String delFilePath = uploadPath + File.separator + ymd + File.separator + buvo.getFileSaveName();	
				File file = new File(delFilePath);
				if (file.exists()) {
					file.delete();
					System.out.println("delFilePath : " + delFilePath + "...exist");	
				}
				file = null;
				
				String delThumbFilePath = uploadPath + File.separator + ymd + File.separator + "thumb" + File.separator + "s_" + buvo.getFileSaveName();	
				file = new File(delThumbFilePath);
				if (file.exists()) {
					file.delete();
					System.out.println("delThumbFilePath : " + delThumbFilePath + "...exist");	
				}
				file = null;
			} 
			
			boardDAO.delup(idx); //sequence 1 ***
			boardDAO.del(idx); //sequence 2 ***
			
		}catch (Exception e) {
			res = false;
			e.printStackTrace();
		}
		
		return res;
		
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor={RuntimeException.class, Exception.class})
	@Override
	public boolean modOk(BoardVO boardVO, ArrayList<BoardUploadVO> boardUploadVO,PasswordDTO passwordDTO, HttpServletRequest request) throws Exception {
		
		boolean re = true;
		
		try {
			String mod_ip = Proc.getClientIp(request);
			boardVO.setMod_ip(mod_ip);

			boardDAO.modOk(boardVO);
			
			//----------
			for (BoardUploadVO upvo:boardUploadVO) {
				upvo.setReg_ip(mod_ip);
				upvo.setBidx(passwordDTO.getIdx());
				boardDAO.modokup(upvo);
			}
			
			
		} catch (Exception e) {
			re = false;
		}
		return re;

	}
	
	@Override
	public List<BoardUploadVO> uplist(Integer bidx) throws Exception {
		List<BoardUploadVO> aup = boardDAO.uplist(bidx);
		
		for (BoardUploadVO au:aup) {
			if (au.getFileType().split("/")[0].equals("image")) {
				au.setImage(true);
			} else {
				au.setImage(false);
			}
		}

		return aup;
	}
	
	@Override
	public void delup(String fileSaveName) throws Exception {
		boardDAO.delup(fileSaveName);
	}
}
