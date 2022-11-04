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
<title>소셜 - ${detail.title}</title>

</head>
<body>

    <div id="msgStack"></div>
<header>
	<div><!-- 소셜 정보 -->
		<table>
			<tr>
				<td rowspan="2">썸네일${detail.socialImage}</td>
				<td>${detail.title}</td>
				<td>${real}/${detail.maximum}</td>
				<td rowspan="2">${detail.category}</td>
			<tr>
				<td colspan="2">master : ${detail.nickName}
					<div>
						<form action="./join" method="post">
							<c:if test="${real >1}">
								<button class="btn btn-sm btn-outline-dark" type="button" data-bs-toggle="modal" data-bs-target="#memberlist">
									멤버 리스트
								</button>
								<c:if test="${sessionScope.loginData.id eq detail.id}">
									<button class="btn btn-sm btn-outline-dark" type="button" data-bs-toggle="modal" data-bs-target="#entrustModal">
										권한넘기기
									</button>
								</c:if>
							</c:if>					
							<c:if test="${not empty sessionScope.loginData}">
								<c:if test="${real < detail.maximum}">	
									<c:if test="${empty chk}">
											<input type="hidden" name="nickName" value="${sessionScope.loginData.nickName}">
											<input type="hidden" name="id" value="${sessionScope.loginData.id}">
											<input type="hidden" name="socialNum" value="${detail.socialNum}">
											<button class="btn btn-sm btn-outline-dark"type="submit">참가신청</button>
										
									</c:if>
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
	<div><!-- 컨텐츠 내용 -->
		<p>${detail.contents}</p>
	</div>
	
	<div><!-- 댓글 내용-->
		<c:if test="${not empty comment}">
			<c:forEach items="${comment}" var="comment">
				<div class="d-flex justify-content-between">
					<span><small><strong>${comment.writer}</strong></small></span>
					<span><small>${comment.writeDate}</small></span>
				</div>
				<div class="d-flex justify-content-between">
					<c:set var="newLine" value="<%= \"\n\" %>" />
						<p class="card-text">${fn:replace(comment.content, newLine, '<br>')}</p>
					<c:if test="${not empty sessionScope.loginData}">
						<c:if test="${sessionScope.loginData.id eq comment.writer or sessionScope.loginData.id eq detail.id}">
							<div class="text-end">
								<button class="btn btn-sm btn-outline-dark" type="button" onclick="changeEdit(this);">수정</button>
								<button class="btn btn-sm btn-outline-dark" type="button" onclick="">삭제</button>
							</div>
						</c:if>
					</c:if>
				</div>
			</c:forEach>
		</c:if>
		
		<c:choose>
			<c:when test="${not empty sessionScope.loginData}">
				<div>
					<input type="text" name="commentInput" id="commentInput">
					<input type="hidden" name="loginId" value="${sessionScope.loginData.id}">
					<button class="btn btn-sm btn-outline-dark" type="button" onclick="onComment();">버튼</button>
				</div>
			</c:when>
			
			
			<c:otherwise><!-- 로그인을 안했을시 -->
				<div>
					<input type="text" placeholder="로그인이 필요합니다">		
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<div>
		<form action="./delete" method="post">	
		
			<button class="btn btn-sm btn-outline-dark" type="button" onclick="location.href='../social'">
				목록
			</button>		
			<c:if test="${sessionScope.loginData.id eq detail.id}">	
				<button class="btn btn-sm btn-outline-dark" type="button" onclick="location.href='../social/modify?id=${detail.socialNum}'">
					수정
				</button>	
				<input name="id" type="hidden" value="${detail.socialNum}">
				<button class="btn btn-sm btn-outline-dark" type="button" data-bs-toggle="modal"
				data-bs-target="#removeModal">삭제</button>
			</c:if>
		</form>
	</div>
	
	
	<!--  모달 -->
	<!-- 모달: 삭제 -->
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
											${memberList.nickName}(${memberList.id})	
										</c:if>
										<c:if test="${sessionScope.loginData.id eq detail.id}">
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
								<input type="radio" name="enter" value="${memberList.id}">${memberList.nickName}(${memberList.id})				
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
// 에이잭스 펑션 : 소셜링 삭제
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
						location.href = ".?detail=${detail.socialNum}";
					} else if(social.code === "permissionError") {
						alert("권한이 오류");
						location.href = ".?detail=${detail.socialNum}";

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
					id: id
				},
				success: function(data) {
					if(data) {
						var outcastId = id;
						$('#outcast').modal("show");
					} else if(social.code === "permissionError") {
						alert("권한이 오류");
						$('#outcast').modal("show");
						location.href = ".?detail=${detail.socialNum}";

					}
				}
			});
		}


//에이잭스 펑션 : 댓글 등록
function onComment() {
	var user = $("input[name='loginId']").val();
	var con = $("input[name='commentInput']").val();
	if(con.trim() === "") {
		$("#commentInput").focus();
		alert("댓글 내용을 입력하세요.");
		} else{
			
			$.ajax({
				url: "./onComment",
				type: "post",
				data: {
					socialNum: ${detail.socialNum},
					content : con,
					writer: user
				},
				success: function(data) {
					if(data) {
						location.href = "./detail?id=${detail.socialNum}";
					} else {
						alert("오류");

					}
				}
			});
		}	
		}
function changeText(element) {
	element.innerText = "수정";
	var cid = element.parentElement.parentElement.children[0].value;
	var value = element.parentElement.previousElementSibling.children[0].value;
	element.parentElement.previousElementSibling.children[0].remove();
	element.parentElement.previousElementSibling.innerText = value;
	
	
	var btnDelete = document.createElement("button");
	btnDelete.innerText = "삭제";
	btnDelete.setAttribute("class", "btn btn-sm btn-outline-dark");
	btnDelete.setAttribute("onclick", "commentDelete(this, " + cid + ");");
	
	element.parentElement.append(btnDelete);
	element.setAttribute("onclick", "changeEdit(this);");
}

</script>
</body>
</html>