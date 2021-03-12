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
<table border="1">
<c:forEach items="${list.res_list }" var="bank">
<tr>
<td>${bank.account_alias }</td>
<td>${bank.bank_name }</td>
<td>${bank.account_num_masked }</td>
<td><a href="#" onclick="getBalance1('${bank.fintech_use_num }')">${bank.fintech_use_num }</a></td>
<td><a href="#" onclick="getBalance2('${bank.fintech_use_num }')">${bank.fintech_use_num }</a></td>
</tr>
</c:forEach>
</table>
<div id ="result">
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">

function getBalance1(fin){
	//ajax호출
	$.ajax({
		url:"ajaxGetBalance",
		data: {fintech_use_num : fin },
		dataType: "json",		//생략하면 default : html	{	
		success: function(response){	
			console.log(response)
			
		
			$("#result").empty();
			$("#result").append("은행명: " + response.bank_name + "<br>")
						.append("가입적급" + response.product_name + "<br>")
						.append("잔액" + response.balance_amt +"<br>")
						.append("송금가능액 :" + response.available_amt) 
						
						
		}
		
		
	});

function getBalance2(fin){
	//ajax호출
	$.ajax({
		url:"getBalance",
		data: {fintech_use_num : fin },
		success: function(response){ $("#result").html(response); 	}
	});
	
	}
	//ajaxGetBalance
	//fintechUseNum = +fin;
	
	
}

</script>

</body>
</html>