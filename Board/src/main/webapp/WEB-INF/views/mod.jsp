<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="ko">
 <head>
  <meta charset="UTF-8">
  <title>글수정</title>
 </head>
 <body>

	<form:form role="form" commandName="modCommand" action="modok" method="post">
		
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