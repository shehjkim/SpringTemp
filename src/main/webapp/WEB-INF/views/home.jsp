<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ page session="false" %> <!-- 이거있으면 세션을 계속 초기화 하기떄문에 지워야함 -->
<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>
<P>  The time on the server is ${serverTime}. </P>
<c:if test="${empty access_token }">
	<a href="loginForm">로그인</a>
</c:if>
<c:if test="${not empty access_token }">
	<a href="logout">로그아웃</a>
</c:if>
</body>
</html>
