<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


</head>
<body>
<div>
	<form action="./findId" method="post">
		<input type="text" name="name" value="${sessionScope.id}" placeholder ="이름">
		<br>
		<input type="text" name="phone" placeholder ="번호">
		<br>
		<button type="submit">아이디 찾기</button>
	</form>
</div>


</body>
</html>