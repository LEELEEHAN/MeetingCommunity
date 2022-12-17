<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<%@ include file="../module/head.jsp" %>
<%@ include file="../module/nav.jsp"%>
<c:if test="${not empty admin}">
	<title>우동- 관리자 로그인</title>
</c:if>
<c:if test="${empty admin}">
	<title>우동- 로그인</title>
</c:if>
</head>
<body> 
<section>
	<div class="text-center">
		<c:choose>
			<c:when test="${empty sessionScope.loginData}">
				<form action="./login" method ="post">
					<c:if test="${not empty admin}">
						<input type="hidden" name="admin" id="admin" value="admin">
					</c:if>
					<c:if test="${empty admin}">
						<input type="hidden" name="admin" id="admin" value="user">
					</c:if>
					<br>
					이메일 
					<br>
					<input type="text" name="email">
					<br>
					비밀번호
					<br>
					<input type="password" name="password">
					<br>
					<button  class="btn btn-sm btn-outline-dark" type="submit">로그인</button>
					<div class="text-center">
						<button class="btn btn-sm btn-outline-dark" type="button" onclick="location.href='/mt/login/findId'">ID/PW찾기</button>						
						<c:if test="${empty admin}">
							<button  class="btn btn-sm btn-outline-dark" type="button" onclick="location.href='./sign'">회원가입</button>
						</c:if>				
					</div>
				</form>
				
				<c:if test="${empty admin}">
					<div class="text-center">
						<p>간편 로그인</p>
						<button  class="btn btn-sm btn-outline-dark" type="button" onclick="location.href='https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=fb5709f6ea71c441a41f751304f20053&redirect_uri=http://localhost:8080/mt/login/kakaoLogin'">카카오 로그인</button>			
					</div>
				</c:if>
			</c:when>	
			<c:otherwise>
				<div>
					<p>${sessionScope.loginData.email}</p>
					<p>${sessionScope.loginData.nickName}</p>
					<button class="btn btn-sm btn-outline-dark" type="button" onclick="location.href='/mt/login/logout'">로그아웃</button>
					<button class="btn btn-sm btn-outline-dark" type="button" onclick="location.href='/mt/mypage'">마이페이지</button>
				</div>
			</c:otherwise>
		</c:choose>	
	</div>

</section>
 




</body>
</html>