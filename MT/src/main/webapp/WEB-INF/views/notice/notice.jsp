<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<%@ include file="../module/head.jsp" %>
	<%@ include file="../module/nav.jsp" %>
</head>
<body>
		<div>
			<form action="./board/search" method="get">
				<div>			
					전체 게시판 검색
					<input type="text" name="keyword">
					<button type="submit">전송</button>
				</div>
			</form>
		
		</div>
		<div>
			<c:url value="/notice?category=" var="boardsURL" />
			<table>
				<tr>
					<td onclick="location.href='${boardsURL}info'">문의</td>
					<td onclick="location.href='${boardsURL}event'">공지</td>
					<td onclick="location.href='${boardsURL}notice'">정보</td>				
				</tr>
			</table>
		</div>
	<section>
			<article>
		<table>
			<colgroup>
				<col class="col-1">
				<col class="col-4">
				<col class="col-2">
				<col class="col-1">				
				<col class="col-3">
			</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>제목</th>
					<th>작성자</th>
					<th>조회수</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody>
			
			<c:forEach items="${list}" var="Rdata">
				<c:url var="boardListUrl" value="/board/detail">
					<c:param name="category">${category}</c:param>
					<c:param name="id">${Rdata.noticeNum}</c:param>
				</c:url>
				<tr>
					<td>${Rdata.noticeNum}</td>
					<td onclick="location.href='${boardListUrl}'">
						${Rdata.title}
					</td>
					<td>${Rdata.writeDate}</td>
				</tr>
			</c:forEach>
				
				<tr>
					<td>
						<c:if test="${sessionScope.adminAcc eq 'admin'}">
							<form action="/mt/notice/write" method="get">
								<div>
									<c:if test="${not empty sessionScope.loginData.email}">	
										<button class="btn btn-secondary" type="submit"><input type="hidden" name="category" value="${category}">추가</button>
									</c:if>
								</div>
							</form>
						</c:if>
					</td> 
				</tr>
			</article>
			</tbody>
		</table>
	</section>
	<nav>
		<div>
			<ul class="pagination justify-content-center">
				<c:if test="${pageData.hasPrevPage()}">
					<c:url var="boardPagingUrl" value="/board">
						<c:param name="category">${category}</c:param>
						<c:if test="${not empty usersCode}">
							<c:param name="usersCode">${usersCode}</c:param>
						</c:if>							
					</c:url>
					<li class="page-item">
						<a class="page-link" href="${boardPagingUrl}&page=1"><<</a>
					</li>
					<li class="page-item"><a class="page-link"
						href="${boardPagingUrl}&page=${pageData.prevPageNumber}">Prev</a>
					</li>
				</c:if>
				<c:choose>
					<c:when test="${pageData.currentPageNumber eq 1}">
						<c:forEach
							items="${pageData.getPageNumberList(pageData.currentPageNumber - 3, pageData.currentPageNumber + 4)}"
							var="num">
							<c:url var="boardPagingUrl" value="/board">
								<c:param name="category">${category}</c:param>
								<c:if test="${not empty usersCode}">
									<c:param name="usersCode">${usersCode}</c:param>
								</c:if>						
							</c:url>
		
							<li
								class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
								<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
							</li>
						</c:forEach>
					</c:when>
					
					
					<c:when test="${pageData.currentPageNumber eq 2}">
						<c:forEach
							items="${pageData.getPageNumberList(pageData.currentPageNumber - 3, pageData.currentPageNumber + 3)}"
							var="num">
							<c:url var="boardPagingUrl" value="/board">
								<c:param name="category">${category}</c:param>
								<c:if test="${not empty usersCode}">
									<c:param name="usersCode">${usersCode}</c:param>
								</c:if>						
							</c:url>
							<li
								class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
								<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
							</li>
						</c:forEach>
					</c:when>
					
					<c:when test="${pageData.currentPageNumber eq pageData.lastPage}">
							<c:forEach
								items="${pageData.getPageNumberList(pageData.currentPageNumber - 4, pageData.currentPageNumber + 2)}"
								var="num">								
								<c:url var="boardPagingUrl" value="/board">
									<c:param name="category">${category}</c:param>
									<c:if test="${not empty usersCode}">
										<c:param name="usersCode">${usersCode}</c:param>
									</c:if>	
								</c:url>
								<li
									class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
									<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
								</li>
							</c:forEach>
						</c:when>
					
						<c:when test="${pageData.currentPageNumber eq pageData.lastPage-1}">
							<c:forEach
								items="${pageData.getPageNumberList(pageData.currentPageNumber - 3, pageData.currentPageNumber + 2)}"
								var="num">			
								<c:url var="boardPagingUrl" value="/board">
									<c:param name="category">${category}</c:param>
									<c:if test="${not empty usersCode}">
										<c:param name="usersCode">${usersCode}</c:param>
									</c:if>	
								</c:url>
								<li
									class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
									<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
								</li>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach
								items="${pageData.getPageNumberList(pageData.currentPageNumber - 2, pageData.currentPageNumber + 2)}"
								var="num">
								<c:url var="boardPagingUrl" value="/board">
									<c:param name="category">${category}</c:param>
									<c:if test="${not empty usersCode}">
										<c:param name="usersCode">${usersCode}</c:param>
									</c:if>						
								</c:url>
								<li
									class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
									<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
								</li>
							</c:forEach>
						</c:otherwise>			
					</c:choose>
				<c:if test="${pageData.hasNextPage()}">
					<li class="page-item"><a class="page-link"
						href="${boardPagingUrl}&page=${pageData.nextPageNumber}">Next</a>
					</li>
					<li class="page-item"><a class="page-link"
						href="${boardPagingUrl}&page=${pageData.lastPage}">>>></a>
					</li>
				</c:if>
			</ul>
		</div>
	</nav>
</body>
</html>