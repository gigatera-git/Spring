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

<form:form role="form" commandName="joinRequest" action="step3" method="post">
   <div>
       <form:input type="text" placeholder="user id" path="uid"/>
       <form:errors path="uid"/>
   </div>
   <div>
       <form:input type="email" placeholder="user email" path="email"/>
       <form:errors path="email"/>
   </div>
   <div>
       <form:input type="text" placeholder="user name" path="uname"/>
       <form:errors path="uname"/>
   </div>
   <div>
       <form:password placeholder="user password" path="pwd"/>
       <form:errors path="pwd"/>
   </div>
   <div>
       <form:password placeholder="user password check" path="pwd2"/>
       <form:errors path="pwd2"/>
   </div>
   <button type="submit">가입</button>
   <button type="reset">리셋</button>
   
</form:form>


</body>
</html>