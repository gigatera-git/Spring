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

<form:form role="form" commandName="loginCommand" action="login" method="post">
        <div>
            <form:input type="text" placeholder="uid" path="uid"/>
            <form:errors path="uid"/>
        </div>
        <div>
             <form:password placeholder="Password" path="pwd"/>
             <form:errors path="pwd"/>
        </div>
        <div>
            <label>
                <form:checkbox path="savedUid" value="true" />아이디 기억
            </label>
        </div>

        
        <button type="submit">로그인</button>
</form:form>


</body>
</html>