<%@ page language="java" session="true" trimDirectiveWhitespaces="true" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Object principal = auth.getPrincipal();
 
    String name = "";
    if(principal != null) {
        name = auth.getName();
    }
%>

<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<div>

<a href='<c:url value="/page"/>'>GUEST</a>
<a href='<c:url value="/user/page"/>'>USER</a>
<a href='<c:url value="/member/page"/>'>MEMBER</a>
<a href='<c:url value="/admin/page"/>'>ADMIN</a>

<sec:authorize access="isAnonymous()">
    <h5><a href='<c:url value="/loginPage"/>'>LOGIN</a> 로그인 해주세요.</h5>
</sec:authorize>


<p></p>
<sec:authorize access="isAuthenticated()">
	<h5><%=name %>님, 반갑습니다.</h5>
	<!-- <p><secxxxx:authentication property="principal.username"/>님, 반갑습니다.</p>-->
    <form action="/logout" method="POST">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <button type="submit">LOGOUT</button>
    </form>
</sec:authorize>

<p></p>
<a href="#" onclick="document.getElementById('logout-form').submit();">Sign out</a>
<form id="logout-form" action='<c:url value='/logout'/>' method="POST">
   <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
</form>



<br><br>
<a href="/join">JOIN MEMBER</a>

<br><br>
<form action="/joinRes" method="POST">
     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
     <button type="submit">JOIN</button>
 </form>

</div>
</body>
</html>
