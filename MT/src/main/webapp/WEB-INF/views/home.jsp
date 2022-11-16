<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>

<%@ include file="./module/nav.jsp"%>
<c:url var="bs5" value="/static/bs5" />
<c:url var="jQuery" value="/static/js" />
<link rel="stylesheet" type="text/css" href="${bs5}/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
<script type="text/javascript" src="${bs5}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${jQuery}/jquery-3.6.0.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js"></script>
	<title>우동-우리들의 동아리</title>
</head>
<body>
<h1>
	우리들의 동아리
</h1>

<div>
<c:if test="${not empty sessionScope.loginData}">
	<h3>가입 클럽</h3>
	<c:if test="${not empty joinClub}">
		<c:forEach items="#{joinClub}" var="club">
		<c:url var="clubUrl" value="./mt/club/detail">
			<c:param name="id">${club.socialNum}</c:param>
		</c:url>
			<p onclick="location.href='${clubUrl}'">${club.title}</p>
		</c:forEach>
	</c:if>
	
	
	<h3>참가한 쇼셜링</h3>
	<c:if test="${not empty joinSocial}">				
		<c:forEach items="#{joinSocial}" var="social">
		<c:url var="SocialUrl" value="/mt/social/detail">
			<c:param name="id">${social.socialNum}</c:param>
		</c:url>
			<p onclick="location.href='${SocialUrl}'">${social.title}</p>
		</c:forEach>
	</c:if>
</c:if>
</div>

</body>
</html>
