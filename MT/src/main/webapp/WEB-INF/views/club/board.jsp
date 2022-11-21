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
<body class="text-center container">
<section  class="text-center container">
<c:if test="${not empty sessionScope.loginData}">
		<div>
			<form action="./board" method="get">
			    <input type="hidden" name="socialNum" value="${socialNum}">
				<a class="btn btn-sm btn-outline-dark" onclick="location.href='/mt/club/detail?id=${socialNum}'">메인화면으로</a>
				<button class="btn btn-sm btn-outline-dark" name="category" id="free"value="free">자유게시판</button>
				<button class="btn btn-sm btn-outline-dark" name="category" id="qna" value="qna">질문게시판</button>
				<button class="btn btn-sm btn-outline-dark" name="category" id="info" value="info">정보게피산</button>
			</form>
		</div>
	</c:if>
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
			
			<c:forEach items="${board}" var="Rdata">
				<c:url var="boardListUrl" value="/club/board/detail">
					<c:param name="category">${category}</c:param>
					<c:param name="id">${Rdata.boardNum}</c:param>
				</c:url>
				<tr>
					<td></td>
					<td onclick="location.href='${boardListUrl}'">
						<c:if test="${not empty search}">
							<a style="font-siae:3px">[${search}]</a>${Rdata.title}
						</c:if>
							${Rdata.title}
					</td>
					<td>${Rdata.nickName}</td>	
					<td>${Rdata.viewed}</td>					
					<td>${Rdata.writeDate}</td>
				</tr>
			</c:forEach>
				
				<tr>
					<td>
						<form action="/mt/club/board/write" method="get">
							<div>
								<c:if test="${not empty sessionScope.loginData.email}">	
									<input type="hidden" name="category" value="${category}">
			    					<input type="hidden" name="socialNum" value="${socialNum}">
									<button class="btn btn-secondary" type="submit">
										추가
									</button>
								</c:if>
							</div>
						</form>
					</td> 
				</tr>
			</article>
			</tbody>
		</table>
	</section>
</body>
</html>