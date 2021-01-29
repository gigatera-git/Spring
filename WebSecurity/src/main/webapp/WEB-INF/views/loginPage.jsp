<%@ page language="java" session="true" trimDirectiveWhitespaces="true" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>

<body onload="document.f.username.focus();">

    <h3>Login with Username and Password!!</h3>
    <form class="px-4 py-3" action="/login" method="post">
		<p></p>
	    <input type="text" class="form-control" name="loginId" placeholder="loginId">
		<p></p>
	    <input type="password" class="form-control" name="loginPwd" placeholder="Password">
	 	<p>
	 	<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
		    <font color="red">
		        <p>Your login attempt was not successful due to1 <br/>
		            ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p>
		        <c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session"/>
		    </font>
		</c:if>
		<c:if test="${not empty ERRORMSG}">
	        <font color="red">
	        <p>Your login attempt was not successful due to2 <br/>
	        ${ERRORMSG }</p>
	        </font>
	    </c:if>

	 	</p>
	 	
	 	<p><label for="remember-me">Remember Me</label>
    	<input type="checkbox" id="remember-me" name="remember-me"/></p>


	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	    <button type="submit" class="btn btn-primary">Sign in</button>
	</form>

</body>

</html>