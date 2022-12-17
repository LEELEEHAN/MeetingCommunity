<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>    


	<%@ include file="../module/head.jsp" %>
	<%@ include file="../module/nav.jsp" %>
	<title>우동-마이페이지</title>


<script type="text/javascript">		
function nickCheck(){
			
	if($("#nickName").val()==""){
		alert("닉네임을 입력해주세요.");
		$("#nickName").focus();
		return false;
		}
	if($("#nickName").val()!=""){
			$.ajax({
				url : "./idChk",
				type : "POST",
				data : {
					nickName : $("#nickName").val(),
					email : $("#nickName").val(),
					type : "nickName"
					
					},
				success : function(data){
					if(data != 1){
						$.ajax({
							url : "./nickChk",
							type : "POST",
							data : {
								email : $("#email").val(),
								nickName : $("#nickName").val(),
								
								},
							success : function(data){
								if(data != 1){
									alert("사용불가능한 닉네임입니다.");
								}else if(data == 1){
									alert("닉네임을 변경하지 않으셨습니다");
								}
							}
						})
					}else if(data == 1){
						$.ajax({
							url : "./nickSave",
							type : "POST",
							data : {
								nickName : $("#nickName").val(),
								email : $("#email").val()
								
								},
								success : function(data){
									alert("닉네임 변경완료");
									location.href="./modify"
								}
								})
					}else if(data == 2){		
						alert("닉네임 변경 오류");
						$("#nickNameCheck").attr("value", "Y");
					}
				}
			})
		}
	}

function uploadCheck(element) {
	var files = element.files;
	
	var modal = new bootstrap.Modal(document.getElementById("errorModal"), {
		keyboard: false
	});
	var title = modal._element.querySelector(".modal-title");
	var body = modal._element.querySelector(".modal-body");
	
	if(files.length > 1) {
		title.innerText = "파일 업로드 오류";
		body.innerText = "파일 업로드는 최대 1개로 제한되어 있습니다.";
		modal.show();
		element.value = "";
		return;
	}
	
	for(file of files) {
		if(file.size / 1000 / 1000 > 5.0) {
			title.innerText = "파일 업로드 오류";
			body.innerText = "파일당 최대 5MB 까지만 업로드 할 수 있습니다. 5MB 초과 용량에 대해서는 관리자에게 문의하세요.";
			modal.show();
			element.value = "";
			return;
		}
	}
}
</script>
</head> 
<body>
<div style="center">
	<div>
		<p><img src="/mt${sessionScope.image.url}/${sessionScope.image.uuidName}" style="height:160px; width:160px" /></p>	
		<c:if test="${not empty mypage}">		
			<form action="/mt/mypage/modify" method="post" enctype="multipart/form-data">
			<div class="mb-3">
			<label>프로필사진설정</label>
			<input class="form-control" type="file" onchange="uploadCheck(this);" name="fileUpload" multiple>
		</div>
		</c:if>
				<label>아이디</label>
				<br>
				<input class="form-control"  type="text" name="email" id="email" value="${sessionScope.loginData.email}" readonly>
				<br>
				<c:if test="${empty mypage}">
					<label>닉네임</label>
					<br>
					<input class="form-control"  type="text" name="nickName" value="${sessionScope.loginData.nickName}"readonly>
					<br>
					<label>이름</label>
					<br>
					<input class="form-control"  type="text" name="name" value="${sessionScope.loginData.name}" readonly>
					<br>
				</c:if>
				
				<c:if test="${not empty mypage}">
					<label>닉네임</label>
					<br>
					<input class="form-control"  type="text" id="nickName" name="nickName" value="${sessionScope.loginData.nickName}">					
					<button class="btn btn-outline-dark" style="float:right" type="button" id="idChange" onclick="nickCheck();" value="N">
						아이디바꾸기
					</button>
					<br>
					<label>이름</label>
					<br>
					<input class="form-control"  type="text" name="name" value="${sessionScope.loginData.name}" >
					<br>
					<label>전화번호</label>
					<br>
					<input class="form-control"  type="text" name="phone" value="${sessionScope.loginData.phone}">
									<br>
					
				</c:if>
				<c:if test="${empty mypage}">				
				<label>전화번호</label>
				<br>
				<input class="form-control"  type="text" name="phone" value="${sessionScope.loginData.phone}"readonly>
				<br>
				</c:if>
				<label>성별</label>
				<br>
				<input class="form-control"  type="text" name="gender" value="${sessionScope.loginData.gender}"readonly>
				<br>
				<label>매너점수</label>
				<br>
				<input class="form-control"  type="text" name="mannerPoint" value="${sessionScope.loginData.mannerPoint}"readonly>
				<br>			
		
		<c:if test="${empty mypage}">	
			<button class="btn btn-outline-dark" type="button" onclick="location.href='/mt/mypage/modify'">
				수정하기
			</button>		
			<button class="btn btn-outline-dark" type="button" onclick="location.href='/mt/mypage/passChange'">
				비밀번호 변경
			</button>	
		</c:if>
		<c:if test="${not empty mypage}">	
			<button class="btn btn-outline-dark" type="submit">
				저장하기
			</button>	
		</c:if>
		<input type="hidden" name="email" value="${sessionScope.loginData.email}">	
		<button class="btn btn-sm btn-outline-dark" type="button" data-bs-toggle="modal"
		data-bs-target="#removeModal">
		 	탈퇴
		</button>
		<c:if test="${not empty mypage}">
			</form>
		</c:if>	
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
<!-- 모달: 삭제 -->
	<div class="modal fade" id="removeModal" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title">탈퇴 확인</h6>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">탈퇴하시겠습니까?</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-danger" data-bs-dismiss="modal" onclick="deleteUser();">
							확인
						</button>
						<button type="button" class="btn btn-sm btn-primary" data-bs-dismiss="modal" >
							취소
						</button>
					</div>
				</div>
			</div>
		</div>
<script type="text/javascript">
// 에이잭스 펑션 : 소셜링 삭제
function deleteUser() {	
			$.ajax({
				url: "/mt/mypage/delete",
				type: "post",
				data: {
					email : $("#email").val()
				},
				dataType: "json",
				success: function(data) {
					if(data.code === "success") {
						alert("탈퇴 완료");						
							location.href = "/mt";
					} else if(data.code === "permissionError") {
						alert("권한이 오류");
					} else if(data.code === "notExists") {
						alert("이미 삭제되었습니다.")
					}
				}
			});
		}

</script>
</body>
</html>