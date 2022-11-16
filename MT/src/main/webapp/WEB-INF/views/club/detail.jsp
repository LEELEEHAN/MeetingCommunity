<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
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
<title>우리들의 동아리 우동 - ${detail.title}</title>

</head>
<body>

    <div id="msgStack"></div>
<header>
	<div class="text-center">
		<table>
			<tr>
				<td rowspan="2">썸네일${detail.socialImage}</td>
				<td>${detail.title}</td>
				<td>${real}명</td>
				<td rowspan="2">${detail.category}</td>
			<tr>
				<td colspan="2">master : ${detail.nickName}
					<div>
						<form action="./join" method="post">
							<c:if test="${real >1}">
								<button class="btn btn-sm btn-outline-dark" type="button" data-bs-toggle="modal" data-bs-target="#memberlist">
									멤버 리스트
								</button>
								<c:if test="${sessionScope.loginData.email eq detail.email}">
									<button class="btn btn-sm btn-outline-dark" type="button" data-bs-toggle="modal" data-bs-target="#entrustModal">
										권한넘기기
									</button>
								</c:if>
							</c:if>					
							<c:if test="${not empty sessionScope.loginData}">
								<c:if test="${empty chk}">
									<input type="hidden" name="nickName" value="${sessionScope.loginData.nickName}">
									<input type="hidden" name="id" value="${sessionScope.loginData.email}">
									<input type="hidden" name="socialNum" value="${detail.socialNum}">
									<button class="btn btn-sm btn-outline-dark"type="submit">참가신청</button>										
								</c:if>
							</c:if>
						</form>
					</div>
				</td>
			</tr>
		</table>
	</div>
</header>
<section>
	<div class="text-center" form="">
		<p>${detail.contents}</p>
	</div>
	
	
	<c:if test="${not empty sessionScope.loginData}">
		<c:if test="${not empty chk}">
			<div>
				    <input type="hidden" name="${detail.socialNum}">
					<button type="submit" name="자유">자유게시판</button>
					<button type="submit" name="질문">질문게시판</button>
					<button type="submit" name="정보">정보게피산</button>
					<button name="category" id="info" onclick="boardOpen('I');">인증게시판</button>
			</div>
		</c:if>
	</c:if>
	<div class="text-center">
		<form action="./delete" method="post">			
			<input name="id" type="hidden" value="${detail.socialNum}">
			<button type="button" data-bs-toggle="modal"
			data-bs-target="#removeModal">삭제</button>
			<button class="btn btn-primary" type="button" onclick="location.href='../club'">
				목록
			</button>
			<button class="btn btn-primary" type="button" onclick="location.href='../club/modify?id=${detail.socialNum}'">
				수정
			</button>
		</form>
	</div>
	
	
	<!-- 모달:게시판 -->
		<div class="modal fade" id="infoBoradOpen" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title">추방완료</h6>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
					<table>
						<tr>
							<th>제목</th>
							<th>글쓴이</th>
							<th>날짜</th>
						</tr>
						<c:if test="${not empty board}">
							<c:forEach items="${board}" var="board">					
								<tr>
									<td>${board.title}</td>
									<td>${board.nickName}</td>
									<td>${board.writerDate}</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-primary" data-bs-dismiss="modal" >
							확인
						</button>
					</div>
				</div>
			</div>
		</div>
	
	
	
	<!-- 모달 삭제 -->
	<div class="modal fade" id="removeModal" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title">삭제 확인</h6>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">해당 데이터를 삭제하겠습니까?</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-danger"
							data-bs-dismiss="modal" onclick="deleteSocial(${detail.socialNum})">확인</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 모달: 멤버 리스트 -->
	<div class="modal fade" id="memberlist" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title">쇼셜링 참가자</h6>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<c:if test="${not empty memberList}">
								<div>
									<th colspan ="6"><a style="color: black;border:10px;font-size:20px ">master : <strong>${detail.nickName}</strong></a></th>
								</div>
								<c:forEach items="${memberList}" var="memberList">
									<div>
										<c:if test="${memberList.nickName ne detail.nickName}">
											${memberList.nickName}	
										</c:if>
										<c:if test="${sessionScope.loginData.email eq detail.email}"> 
											<c:if test="${memberList.nickName ne detail.nickName}">												
												<!-- 강퇴,추방 버튼 -->
													<button type="hidden" class="btn btn-danger" name="outcast" value="${memberList.id}" onclick="outcastSocial('${memberList.id}')">추방</button>
													<!--data-bs-toggle="modal" data-id="${memberList.id}" data-nickName="${memberList.nickName}" name="outcastButton" data-bs-target="#outcast"  -->
												
											</c:if>
										</c:if>
									</div>	
								</c:forEach>
						</c:if>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-primary" data-bs-dismiss="modal" >
							닫기
						</button>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- 모달 :방장 위임-->
		<div class="modal fade" id="entrustModal" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title">방장 위임하기</h6>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<c:forEach items="${memberList}" var="memberList">
							<c:if test="${memberList.auth =='U'}">
							<div>
								<input type="radio" name="enter" value="${memberList.id}">${memberList.nickName}				
							</div>
							</c:if>
						</c:forEach>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-danger"
							data-bs-dismiss="modal" onclick="entrustSocial()">
							확인
						</button>
						<button type="button" class="btn btn-sm btn-primary"
							data-bs-dismiss="modal" >
							취소
						</button>
					</div>
				</div>
			</div>
		</div>
		
		
		
		<!-- 모달:강퇴 -->
		<div class="modal fade" id="outcast" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title">삭제 확인</h6>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">를 추방하시겠습니까?</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-danger" data-bs-dismiss="modal" onclick="deleteSocial(${detail.socialNum})">
							확인
						</button>
						<button type="button" class="btn btn-sm btn-primary" data-bs-dismiss="modal" >
							취소
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 모달:강퇴 확인 -->
		<div class="modal fade" id="outcastResult" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title">추방완료</h6>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">추방하시겠습니까?</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-primary" data-bs-dismiss="modal" >
							확인
						</button>
					</div>
				</div>
			</div>
		</div>
</section>
<script type="text/javascript">
//에이잭스 펑션 : 소셜링 삭제
function deleteSocial(id) {	
			$.ajax({
				url: "./delete",
				type: "post",
				data: {
					id: ${detail.socialNum}
				},
				dataType: "json",
				success: function(data) {
					if(data.code === "success") {
						alert("삭제 완료");						
							location.href = "./mt/social";
					} else if(data.code === "permissionError") {
						alert("권한이 오류");
					} else if(data.code === "notExists") {
						alert("이미 삭제되었습니다.")
					}
				}
			});
		}
		

//에이잭스 펑션 : 방장 위임
function entrustSocial(id) {
	var user = $("input[name='enter']:checked").val();
			$.ajax({
				url: "./entrust",
				type: "post",
				data: {
					socialNum: ${detail.socialNum},
					id: user
				},
				success: function(data) {
					if(data) {
						alert("위임 완료");						
						location.href = "./detail?id=${detail.socialNum}";
					} else {
						alert("권한이 오류");
						location.href = "./detail?id=${detail.socialNum}";

					}
				}
			});
		}

//에이잭스 펑션 : 강퇴
function outcastSocial(id) {
			$.ajax({
				url: "./outcast",
				type: "post",
				data: {
					socialNum: ${detail.socialNum},
					email: id
				},
				success: function(data) {
					if(data) {
						var outcastId = id;
						$('#outcast').modal("show");
						location.href = ".?detail=${detail.socialNum}";
					} else {
						alert("권한이 오류");
						location.href = ".?detail=${detail.socialNum}";

					}
				}
			});
		}
function boardOpen(category) {
		$.ajax({
			url: "./getBoard",
			type: "post",
			data: {
				socialNum: ${detail.socialNum},
				category: category
			},
			success: function(category) {
					$('#infoBoradOpen').modal("show");

			}
		});
	}

</script>
</body>
</html>