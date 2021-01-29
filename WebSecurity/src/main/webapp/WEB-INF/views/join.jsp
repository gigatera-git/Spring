<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<Form action="/joinRes" method="POST">
 
 ID : <input type="textbox" id="Id" name="Id" value="newbie"><br>
 PASSWORD : <input type="Password" id="Password" name="Password" value="1234"><br>
 NAME : <input type="textbox" id="Name" name="Name" value="홍길동"><br>
 AUTHORITY : <input type="radio" name="Authority" value="ROLE_USER" checked>ROLE_USER
 <input type="radio" name="Authority" value="ROLE_MEMBER">ROLE_MEMBER
 <input type="radio" name="Authority" value="ROLE_ADMIN">ROLE_ADMIN
 <br><br>
 
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <input type="submit">
 
 
 </Form>
 
 
</body>
</html>