<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION }">
	<font color="red">
		${SPRING_SECURITY_LAST_EXCEPTION.message }
		<c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope="session"></c:remove>
	</font>
</c:if>
<form action="login" method="post">
	아이디 <input name="id">
	패스워드<input name="password">
	<button>로그인</button>
</form>
<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=0333a5381d562d3bb9ae06134c014141&redirect_uri=http://localhost/bank/callback">카카오로그인</a>
</body>
</html>