<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>우동- LOGIN</title>
</head>
<body>
<section>
	<div>
		<c:if test="${empty sessionScope.loginData}">
			<form action="./login" method ="post">
				<input type="text" name="email">
				<br>
				<input type="password" name="password">
				<br>
				<button type="submit">로그인</button>
				<button type="button" onclick="location.href='./sign'">회원가입</button>
			</form>
				<button type="button" onclick="location.href='/mt/login/findId'">ID/PW찾기</button>
		</c:if>	
		<c:if test="${not empty sessionScope.loginData}">
			<div>
				<p>${sessionScope.loginData.email}</p>
				<p>${sessionScope.loginData.nickName}</p>
				<button type="button" onclick="location.href='/mt/login/logout'">로그아웃</button>
				<button type="button" onclick="location.href='/mt/mypage'">마이페이지</button>
			</div>
		</c:if>	
	</div>

</section>	

</body>
</html>