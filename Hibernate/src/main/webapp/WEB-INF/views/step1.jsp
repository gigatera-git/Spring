<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form role="form" action="step2" method="post"> <!-- html role form -->
<div>
회원가입조건에 동의합니다
</div>
<input type="checkbox" name="agree" value="true">동의

<br><br>

<button type="submit">다음</button>

</form>


</body>
</html>