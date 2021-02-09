<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page session="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!doctype html>
<html lang="ko">
 <head>
  <meta charset="UTF-8">
  <title>상세보기</title>
  <script language="javascript" type="text/javascript" src="/resources/config/js/jquery-3.1.0.js"></script>
  <script language="javascript" type="text/javascript" src="/resources/config/js/jquery.bpopup.min.js"></script>
  <script language="javascript" type="text/javascript" src="/resources/config/js/extend.js?v=2020-07-23-001"></script>
  <script language="javascript" type="text/javascript">
  $(document).ready(function(){
		
		var $idx = $("#idx").val();
		var $intPage = $("#intPage").val();
		var $SearchOpt = $("#SearchOpt").val();
		var $SearchVal = $("#SearchVal").val();
		var $ref = $("#ref").val();
		var $re_step = $("#re_step").val();
		var $re_lvl = $("#re_lvl").val();

		$("#btnList").on("click",function(){
			location.href = "list?intPage="+$intPage+"&SearchOpt="+$SearchOpt+"&SearchVal="+$SearchVal;
		});

		$("#btnReply").on("click",function(){
			location.href = "reply?idx="+$idx+"&intPage="+$intPage+"&SearchOpt="+$SearchOpt+"&SearchVal="+$SearchVal+"&ref="+$ref+"&re_step="+$re_step+"&re_lvl="+$re_lvl;
		});
		
		$("#btnMod").on("click",function(){
			location.href = "pwd?modordel=mod&idx="+$idx+"&intPage="+$intPage+"&SearchOpt="+$SearchOpt+"&SearchVal="+$SearchVal;
		});

		$("#btnDel").on("click",function(){
			location.href = "pwd?modordel=delok&idx="+$idx+"&intPage="+$intPage+"&SearchOpt="+$SearchOpt+"&SearchVal="+$SearchVal;
		});
		
   });
  </script>
 
 </head>
 <body>

	<form name="frmBoard" id="frmBoard">
		<input type="hidden" name="idx" id="idx" value="${view.idx }">
		<input type="hidden" name="intPage" id="intPage" value="${arguDTO.intPage }">
		<input type="hidden" name="SearchOpt" id="SearchOpt" value="${arguDTO.searchOpt }">
		<input type="hidden" name="SearchVal" id="SearchVal" value="${arguDTO.searchVal }">
		<input type="hidden" name="ref" id="ref" value="${view.ref }">
		<input type="hidden" name="re_step" id="re_step" value="${view.re_step }">
		<input type="hidden" name="re_lvl" id="re_lvl" value="${view.re_lvl }">
		<input type="hidden" name="pwd" id="pwd" value="">

		<table border="1">
		<tr>
		<td align="center"><b>작성자</b></td><td>${view.uname }</td>
		</tr>
		<tr>
		<td align="center"><b>제목</b></td><td>${view.title }</td>
		</tr>
		<tr>
		<td align="center"><b>내용</b></td><td>${view.contents }</td>
		</tr>
		<tr>
		<td align="center"><b>클릭수</b></td><td>${view.click }</td>
		</tr>
		<tr>
		<td align="center"><b>아이피</b></td><td>${view.reg_ip }</td>
		</tr>
		<tr>
		<td align="center"><b>아이피(m)</b></td><td>${view.mod_ip }</td>
		</tr>
		<tr>
		<td align="center"><b>등록일</b></td><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${view.reg_date }"></fmt:formatDate></td>
		</tr>
		<tr>
		<td align="center"><b>수정일</b></td><td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${view.mod_date }"></fmt:formatDate></td>
		</tr>
		
		<tr>
		<td align="center"><b>첨부파일</b></td><td>
		
		<c:forEach items="${uplist}" var="aup" varStatus="status">
			<div class='attach'>
				(${status.count})
				
				<c:if test="${aup.image}">
				<a href='download?filePath=<fmt:formatDate pattern="yyyy" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="MM" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="dd" value="${aup.reg_date }"></fmt:formatDate>&fileName=${aup.fileSaveName }'><img src='/upload/<fmt:formatDate pattern="yyyy" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="MM" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="dd" value="${aup.reg_date }"></fmt:formatDate>/${aup.fileSaveName }' title='${aup.fileRealName }' align='absmiddle' /></a>
				</c:if>
				
				<c:if test="${not aup.image}">
				<a href='download?filePath=<fmt:formatDate pattern="yyyy" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="MM" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="dd" value="${aup.reg_date }"></fmt:formatDate>&fileName=${aup.fileSaveName }' title='${aup.fileRealName }'/> ${aup.fileRealName } </a>
				</c:if>
	
				

				
				<input type='hidden' name='files' class='files' value=<fmt:formatDate pattern="yyyy-MM-dd" value="${aup.reg_date }"></fmt:formatDate>/${aup.fileSaveName } />
			</div>
		</c:forEach>
			
		</td>
		</tr>
		
		</table>
		<div>
			<input type="button" value="리스트" id="btnList" />
			<input type="button" value="답글" id="btnReply" />
			<input type="button" value="수정" id="btnMod" alt="수정" />
			<input type="button" value="삭제" id="btnDel" alt="삭제" />
		</div>


	</form>
  
 </body>
</html>
