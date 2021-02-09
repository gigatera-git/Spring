<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html lang="ko">
 <head>
  <meta charset="UTF-8">
  <title>글수정</title>
  <script language="javascript" type="text/javascript" src="/resources/config/js/jquery-3.1.0.js?v=2"></script>
  <script language="javascript" type="text/javascript" src="/resources/config/js/extend.js?v=2"></script>
  <script language="javascript" type="text/javascript" src="/resources/ckeditor/ckeditor.js?v=2"></script>
  <script language="javascript" type="text/javascript">
  $(document).ready(function(){
	
	CKEDITOR.replace('contents',{
		filebrowserUploadUrl:'uploadCkeditor'
	});

  });
  </script>
 </head>
 <body>

	<form:form role="form" commandName="modCommand" action="modok" method="post" enctype="multipart/form-data">
		
		<input type="hidden" name="idx" id="idx" value="${arguDTO.idx }">
		<input type="hidden" name="intPage" id="intPage" value="${arguDTO.intPage }">
		<input type="hidden" name="SearchOpt" id="SearchOpt" value="${arguDTO.searchOpt }">
		<input type="hidden" name="SearchVal" id="SearchVal" value="${arguDTO.searchVal }">

		<table border="1">
		<tr>
		<td align="center"><b>글쓴이</b></td>
		<td>
			<form:input type="text" path="uname" value="" readonly="true" />
			<form:errors path="uname"/>
			
		</td>
		</tr>
		<tr>
		<td align="center"><b>제목</b></td>
		<td>
			<form:input type="text" path="title" value="" size="30" maxlength="30" placeholder="제목" />
			<form:errors path="title"/>
		</td>
		</tr>

		<tr>
		<td align="center"><b>내용</b></td>
		<td>
			<form:textarea path="contents" cols="20" rows="10"/>
			<form:errors path="contents"/>
		</td>
		</tr>
		
		<tr>
		<td align="center"><b>첨부</b></td>
		<td>
		
			
			<c:forEach items="${uplist}" var="aup" varStatus="status">
			<div class='attach'>
				(${status.count})
				
				<c:if test="${aup.image}">
				<a href='download?filePath=<fmt:formatDate pattern="yyyy" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="MM" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="dd" value="${aup.reg_date }"></fmt:formatDate>&fileName=${aup.fileSaveName }'><img src='/upload/<fmt:formatDate pattern="yyyy" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="MM" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="dd" value="${aup.reg_date }"></fmt:formatDate>/${aup.fileSaveName }' title='${aup.fileRealName }' align='absmiddle' /></a>
				</c:if>
				
				<c:if test="${not aup.image}">
				<a href='download?filePath=<fmt:formatDate pattern="yyyy" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="MM" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="dd" value="${aup.reg_date }"></fmt:formatDate>&fileName=${aup.fileSaveName }' title='${aup.fileRealName }'/> ${aup.fileRealName } </a>
				</c:if>
	
				<input type='hidden' name='files' class='files' value=<fmt:formatDate pattern="yyyy" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="MM" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="dd" value="${aup.reg_date }"></fmt:formatDate>/${aup.fileSaveName } />
				
				<input type='checkbox' name='attachDels' class='attachDels' value='<fmt:formatDate pattern="yyyy" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="MM" value="${aup.reg_date }"></fmt:formatDate>/<fmt:formatDate pattern="dd" value="${aup.reg_date }"></fmt:formatDate>/${aup.fileSaveName }' />삭제)
			</div>
		</c:forEach>
		
		<br>
		<input type='file' name='files' class='file'><br>
		<input type='file' name='files' class='file'><br>
			
		</td>
		</tr>
		
		</table>
		
		<table border="0">
		<tr>
		<td>
			<input type="submit" value="수정">
			<input type="reset" value="다시쓰기">
		</td>
		</tr>
		</table>

	</form:form>
  
 </body>
</html>