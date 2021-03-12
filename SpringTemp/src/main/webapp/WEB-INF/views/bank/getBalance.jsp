<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<c:forEach items="balance" var="bal">
${bal.rsp_code }
${bal.rsp_message }
${bal.api_trn_id }
${bal.api_tran_dtm }
</c:forEach> 

</body>
</html>