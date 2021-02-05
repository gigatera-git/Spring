package kr.co.gigatera.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import kr.co.gigatera.dao.BoardDAO;
import kr.co.gigatera.util.ContextUtil;
import kr.co.gigatera.util.CookieUtil;
import kr.co.gigatera.vo.BoardPreVO;
import kr.co.gigatera.vo.BoardVO;

@Service("BoardService")
public class BoardSerivceImpl implements BoardService {
	
	@Resource(name="BoardDAO")
	private BoardDAO boardDAO;
	
	@Override
	public Integer ref() throws Exception {
		return boardDAO.ref();
	}

	@Override
	public Integer write(BoardVO boardVO) throws Exception {
		
		return boardDAO.write(boardVO);
	}

	@Override
	public List<BoardVO> list(BoardPreVO boardPreVO) throws Exception {
		if (boardPreVO.getIntPage()==null || boardPreVO.getIntPage()<1) {
			boardPreVO.setIntPage(1);
		}
		
		boardPreVO.setIntPageSize(10);
		boardPreVO.setIntBlockPage(10);
		
		boardPreVO.setIntStartPage((boardPreVO.getIntPage()-1)*boardPreVO.getIntPageSize());
		boardPreVO.setIntTotalCount(boardDAO.listPre(boardPreVO));
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
	
	@Override
	public void replyPre(HashMap<String,Object> map) throws Exception {
		boardDAO.replyPre(map);
	}
	@Override
	public Integer reply(BoardVO boardVO) throws Exception {
		
		return boardDAO.reply(boardVO);
	}

	@Override
	public String getPwd(Integer idx) throws Exception {
		
		return boardDAO.getPwd(idx);
	}

	@Override
	public Integer del(Integer idx) throws Exception {
		return boardDAO.del(idx);
	}

	@Override
	public Integer modOK(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		return boardDAO.modOk(boardVO);
	}
	
	
}
