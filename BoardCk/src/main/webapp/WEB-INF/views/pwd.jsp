<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="ko">
 <head>
  <meta charset="UTF-8">
  <title>글쓰기</title>
 </head>
 <body>

	<form:form role="form" commandName="pwdCommand" action="pwdok" method="post">
		<input type="hidden" name="modordel" id="modordel" value="${passwordDTO.modordel }">
		<input type="hidden" name="idx" id="idx" value="${passwordDTO.idx }">
		<input type="hidden" name="intPage" id="intPage" value="${passwordDTO.intPage }">
		<input type="hidden" name="SearchOpt" id="SearchOpt" value="${passwordDTO.searchOpt }">
		<input type="hidden" name="SearchVal" id="SearchVal" value="${passwordDTO.searchVal }">
		<table border="1">
		<tr>
		<td align="center"><b>비밀번호</b></td>
		<td>
			<form:input type="password" path="pwd" value="" placeholder="비밀번호" />
			<form:errors path="pwd"/>
		</td>
		</tr>

		</table>
		
		<table border="0">
		<tr>
		<td>
			<button type="submit">확인</button>
			<button type="reset">다시쓰기</button>
		</td>
		</tr>
		</table>

	</form:form>
  
 </body>
</html>