<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="ko">
 <head>
  <meta charset="UTF-8">
  <title>글쓰기</title>
  <script language="javascript" type="text/javascript" src="/resources/config/js/jquery-3.1.0.js?v=2"></script>
  <script language="javascript" type="text/javascript" src="/resources/config/js/extend.js?v=2"></script>
  <script language="javascript" type="text/javascript" src="/resources/ckeditor/ckeditor.js?v=3"></script>
  <script language="javascript" type="text/javascript">
  $(document).ready(function(){

	CKEDITOR.replace('contents',{
		filebrowserUploadUrl:'uploadCkeditor'
	});

  });
  </script>
 </head>
 <body>

<form:form role="form" commandName="writeCommand" action="writeok" method="post" enctype="multipart/form-data">
		<table border="1">
		<tr>
		<td align="center"><b>글쓴이</b></td>
		<td>
			<form:input type="text" path="uname" value="aaa" size="10" maxlength="10" placeholder="글쓴이" />
			<form:errors path="uname"/>
		</td>
		</tr>
		<tr>
		<td align="center"><b>제목</b></td>
		<td>
			<form:input type="text" path="title" value="aaa" size="30" maxlength="30" placeholder="제목" />
			<form:errors path="title"/>
		</td>
		</tr>
		<tr>
		<td align="center"><b>비밀번호</b></td>
		<td>
			<form:input type="password" path="pwd" value="slalfjf#09@" size="16" minlength="8" maxlength="16" placeholder="비밀번호" />
			<form:errors path="pwd"/>
		</td>
		</tr>
		<tr>
		<td align="center"><b>비번확인</b></td>
		<td>
			<form:input type="password" path="pwd2" value="slalfjf#09@" size="16" minlength="8" maxlength="16" placeholder="비밀번호 확인" />
			<form:errors path="pwd2"/>
		</td>
		</tr>
		<tr>
		<td align="center"><b>내용</b></td>
		<td>
			<form:textarea path="contents" cols="20" rows="10" />
			<form:errors path="contents"/>
		</td>
		</tr>
		<td align="center"><b>첨부</b></td>
		<td>
			<input type="file" name="files" class="file"><br>
			<input type="file" name="files" class="file">
		</td>
		</tr>
		</table>
		
		<table border="0">
		<tr>
		<td>
			<button type="submit">글등록</button>
			<button type="reset">다시쓰기</button>
		</td>
		</tr>
		</table>

	</form:form>
  
 </body>
</html>