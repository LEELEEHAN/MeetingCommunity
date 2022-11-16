<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>    

<%@ include file="../module/nav.jsp"%>
<c:url var="bs5" value="/static/bs5" /> 
<c:url var="jQuery" value="/static/js" />
<link rel="stylesheet" type="text/css" href="${bs5}/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
<script type="text/javascript" src="${bs5}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${jQuery}/jquery-3.6.0.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js"></script>
	<title>우동-마이페이지</title>
</head> 
<body>
<div style="center">
	<div>
		<label>아이디</label><br>
		<input type="text" name="email" placeholder="${sessionScope.loginData.email}" readonly><br>
		<label>이름</label><br>
		<input type="text" name="name" placeholder="${sessionScope.loginData.name}"readonly><br>
		<label>별칭</label><br>
		<input type="text" name="nickName" placeholder="${sessionScope.loginData.nickName}"readonly><br>
		<label>전화번호</label><br>
		<input type="text" name="phone" placeholder="${sessionScope.loginData.phone}"readonly><br>
		<label>성별</label><br>
		<input type="text" name="gender" placeholder="${sessionScope.loginData.gender}"readonly><br>
		<label>매너점수</label><br>
		<input type="text" name="mannerPoint" placeholder="${sessionScope.loginData.mannerPoint}"readonly><br>
	</div>
	<div>
		<button>
			수정하기
		</button>	
		<button>
		 	탈퇴
		</button>	
	</div>
	<div>
		<label>참가한 클럽</label>
		<ul>
			<c:if test="${not empty joinClub}">
				<c:forEach items="#{joinClub}" var="club">
				<c:url var="clubUrl" value="../mt/club/detail">
					<c:param name="id">${club.socialNum}</c:param>
				</c:url>
					<li onclick="location.href='${clubUrl}'">${club.title}</li>
				</c:forEach>
			</c:if>
		</ul>
		<label>참가한 쇼셜링</label>
		<ul>
			<c:if test="${not empty joinSocial}">				
				<c:forEach items="#{joinSocial}" var="social">
				<c:url var="SocialUrl" value="../mt/social/detail">
					<c:param name="id">${social.socialNum}</c:param>
				</c:url>
					<li onclick="location.href='${SocialUrl}'">${social.title}</li>
				</c:forEach>
			</c:if>
		</ul>
	</div>
</div>

</body>
</html>