<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:url var="mainUrl" value="http://localhost:8080/mt" />
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<div class="container-fluid">
		<div>
			<a class="nav-link" style="float:right" href="${mainUrl}/club" id="navbarDropdownMenuLink" role="button">
				클럽 커뮤니티
			</a>
		</div>
		<div>
			<a class="nav-link" style="float:right" href="${mainUrl}/social" id="navbarDropdownMenuLink" role="button">
				쇼셜
			</a>
		</div>
		<div>
			<a class="nav-link" style="float:right" href="${mainUrl}/notice" id="navbarDropdownMenuLink" role="button">
				공지사항
			</a>
		</div>
		<a class="navbar-brand" href="/mt">우리들의 동호회-우동</a>
		<c:choose>
			<c:when test="${not empty sessionScope.loginData}">
				<div>
					<ul class="navbar-nav me-auto">
						<li class="nav-item dropdown">
							<a class="nav-link dropdown-toggle" style="float:right" href="#" id="navbarDropdownMenuLink" role="button"data-bs-toggle="modal" data-bs-target="#mysocial" aria-expanded="false">
								내 쇼셜
							</a>
						</li>
						
					</ul>		
				</div>
				<div>
					<ul class="navbar-nav me-auto">
						<li class="nav-item dropdown">
							<a class="nav-link" style="float:right" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="modal" data-bs-target="#myclub"aria-expanded="false">
								내 클럽
							</a>
						</li>
					</ul>		
				</div>
				<div>
					<ul class="navbar-nav me-auto">
						<li class="nav-item dropdown">
							<a class="nav-link dropdown-toggle" style="float:right" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
								${sessionScope.loginData.nickName}님 환영합니다
							</a>
							<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							
								<li>
									<a class="dropdown-item" href="${mainUrl}/login/logout">로그아웃</a>
								</li>
								<li>
									<a class="dropdown-item" href="${mainUrl}/mypage">마이페이지</a>
								</li>
							</ul>
						</li>
				
					</ul>
				</div>
			</c:when>
			<c:otherwise>
				<div>
					<a class="nav-link" href="${mainUrl}/login" id="navbarDropdownMenuLink" role="button">				
						LOGIN
					</a>
				</div>
				<div>
					<a class="nav-link" href="${mainUrl}/sign" id="navbarDropdownMenuLink" role="button">								
						회원가입
					</a>				
				</div>
				<div>
					<a class="nav-link" href="${mainUrl}/login/findId" id="navbarDropdownMenuLink" role="button">								
						ID/PW찾기
					</a>				
				</div>
			</c:otherwise>
		</c:choose>
		<!--  div>
			<ul class="navbar-nav me-auto">
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" style="float:right" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
						쇼셜
					</a>
					<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
						<li>
							<a class="dropdown-item" href="${mainUrl}/social">소셜</a>
						</li>
						<li>
							<a class="dropdown-item" href="${mainUrl}/club">클럽</a>
						</li>
						<li>
							<a class="dropdown-item" href="${mainUrl}/login">로그인</a>
						</li>
						<li>
							<a class="dropdown-item" href="${mainUrl}/mypage">마이페이지</a>
						</li>
					</ul>
				</li>
				
			</ul>
			
		</div-->
	</div>
	<!-- 모달:마이클럽 -->
		<div class="modal fade" id="myclub" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title">참여중인 클럽</h6>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<c:choose>
							<c:when test="${not empty sessionScope.joinClub}">				
								<c:forEach items="#{sessionScope.joinClub}" var="joinClub">
								<c:url var="SocialUrl" value="/club/detail">
									<c:param name="id">${joinClub.socialNum}</c:param>
								</c:url>
								<div>
									<a onclick="location.href='${SocialUrl}'">${joinClub.title}</a>
									<a>${joinClub.category}</a>
									<c:if test="${joinClub.auth =='M'}">
										<a>"갤주"</a>				
									</c:if>					
								</div>
								</c:forEach>
							</c:when>
							<c:otherwise>
							  <a>
								  "참여 중인 클럽이 없습니다"
							  </a>
							</c:otherwise>		
						</c:choose>	
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-primary" data-bs-dismiss="modal" >
							확인
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 모달:마이소셜 -->
		<div class="modal fade" id="mysocial" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title">참여중인 소숄</h6>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<c:choose>
							<c:when test="${not empty sessionScope.joinSocial}">				
								<c:forEach items="#{sessionScope.joinSocial}" var="joinClub">
								<c:url var="SocialUrl" value="/social/detail">
									<c:param name="id">${joinClub.socialNum}</c:param>
								</c:url>
								<div>
									<a onclick="location.href='${SocialUrl}'">${joinClub.title}</a>
									<a>${joinClub.category}</a>
									<c:if test="${joinClub.auth =='M'}">
										<a>"방주"</a>				
									</c:if>					
								</div>
								</c:forEach>
								
							</c:when>
							<c:otherwise>
							  <a>
								  "참여 중인 쇼셜이 없습니다"
							  </a>
							</c:otherwise>		
						</c:choose>	
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-primary" data-bs-dismiss="modal" >
							확인
						</button>
					</div>
				</div>
			</div>
		</div>
	
	
</nav>