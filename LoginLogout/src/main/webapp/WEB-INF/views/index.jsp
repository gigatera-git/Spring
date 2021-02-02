<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<c:catch>
    <c:choose>
    
        <c:when test="${empty authInfo }">
            <li>
                 <a href="login">로그인</a>
             </li>
        </c:when>
        
        <c:otherwise>
            <c:choose>
                <c:when test="${authInfo.grade eq '1' }">
                    <li>
                       	관리자 ${authInfo.uname }( ${authInfo.grade })님, 환영합니다!.
                   </li>
                   <li>
                       <a href="logout"> 로그아웃</a>
                   </li>
                </c:when>
                <c:otherwise>
                    <li>
                       ${authInfo.uname }( ${authInfo.grade })님, 환영합니다!!. 
                   </li>
                   <li>
                       <a href="logout"> 로그아웃</a>
                   </li>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
        
    </c:choose>
</c:catch>


</body>
</html>