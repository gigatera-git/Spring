<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page session="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html lang="ko">
 <head>
  <meta charset="UTF-8">
  <title>리스트</title>
  <script language="javascript" type="text/javascript" src="/resources/config/js/jquery-3.1.0.js?v=2"></script>
  <script language="javascript" type="text/javascript" src="/resources/config/js/extend.js?v=2"></script>
  <script language="javascript" type="text/javascript">
  $(document).ready(function(){
		
		$("#frmBoard").submit(function(e){
			//e.preventDefault(); //This can interrupt html5 form check system.
			//if (confirm("저장할까요?")) {
				$("#frmBoard").attr({'action':'list'});
			//}
		});

		$(document).on("click","#btnInit",function(){ //for dynamic event...
			location.href = 'list';
			//alert('test');
		});

   });
  </script>
 </head>
 <body>

	<form name="frmBoard" id="frmBoard" method="post">
	
	<div id="write">
		[<a href="write">글등록</a>] 
	</div>
	<table border="1">
	<tr>
	<td align="center"><b>번호</b></td>
	<td align="center"><b>제목</b></td>
	<td align="center"><b>작성자</b></td>
	<td align="center"><b>클릭수</b></td>
	<td align="center"><b>작성일</b></td>
	</tr>
	
	
	
	<c:choose>
        <c:when test="${fn:length(list) > 0 }">
            <c:forEach items="${list}" var="boardVO" varStatus="status">
                <tr>
					<td align="center">${boardPreVO.getArticleNum(status.count-1)}</td>
					<td>
						<img src="/resources/images/common/level.gif" border="0" align="absmiddle" width="${boardVO.re_lvl*7 }">
						<c:if test="${boardVO.re_lvl > 0}">
						<img src="/resources/images/common/ico_reply.gif" border="0" align="absmiddle" >
						</c:if>
						<a href="view?idx=${boardVO.idx }&intPage=${boardPreVO.intPage}&SearchOpt=${boardPreVO.searchOpt}&SearchVal=${boardPreVO.searchVal}">${boardVO.title }</a>
						<c:if test="${boardVO.newbie.equals('1') }">
			    			<img src="/resources/images/common/ico_new.gif" border="0" align="absmiddle" >
						</c:if>
					</td>
					<td align="center">${boardVO.uname }</td>
					<td align="center">${boardVO.click }</td>
					<td align="center"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${boardVO.reg_date }"></fmt:formatDate></td>
				</tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="5">해당 내용이 없습니다</td>
            </tr>
        </c:otherwise>
    </c:choose>
	

	</table>

	<div id="search">
		<select name="SearchOpt" id="SearchOpt" required oninvalid="this.setCustomValidity('검색옵션을 선택하세요')" oninput="setCustomValidity('')">
			<option value=""></option>
			<option value="uname" <c:out value="${boardPreVO.searchOpt eq 'uname'?'selected':'' }" /> >이름</option>
			<option value="title" <c:out value="${boardPreVO.searchOpt eq 'title'?'selected':'' }" /> >제목</option>
			<option value="contents" <c:out value="${boardPreVO.searchOpt eq 'contents'?'selected':'' }" /> >내용</option>
		</select>	
		<input type="text" name="SearchVal" id="SearchVal" maxlength="10" minlength="2" required oninvalid="this.setCustomValidity('검색어를 입력하세요')" oninput="setCustomValidity('')" value="<c:out value="${boardPreVO.searchVal!=null?boardPreVO.searchVal:'' }" />">
		<input type="submit" value="검색" id="btnSearch">
		<c:if test="${boardPreVO.searchOpt!='' and  boardPreVO.searchVal!=''}" >
			<input type='button' value='처음' id='btnInit'>
		</c:if>
	</div>


	<div id="paging">
		${boardPreVO.Paging() }
	</div>


	</form>

 </body>
</html>
