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
	<div>
		<table>
			<tr>
				<td rowspan="2">썸네일${detail.socialImage}</td>
				<td>${detail.title}</td>
				<td>${real}명</td>
				<td rowspan="2">${detail.category}</td>
			<tr>
				<td colspan="2">master : ${detail.nickName}
					<div>
						<c:if test="${real >1}">
							<c:if test="${sessionScope.nickName eq detail.nickName}">
								<button type="button" data-bs-toggle="modal" data-bs-target="#entrustModal">
									권한넘기기
								</button>
							</c:if>
						</c:if>
					</div>
				</td>
			</tr>
		</table>
	</div>
</header>
<section>
	<div>
		<p>${detail.contents}</p>
	</div>
	<c:if test="${not empty memberList}">
		<c:forEach items="${memberList}" var="memberList">
			<tr>
				<td >${memberList.nickName}				
					<c:if test="${sessionScope.nickName eq detail.nickName}">
						<c:if test="${memberList.nickName ne detail.nickName}">
							<form action="./outcast" method="post">	
								<input type="hidden" name="nickName" value="${memberList.nickName}">
								<input type="hidden" name="socialNum" value="${detail.socialNum}">
								<input type="hidden" name="auth" value="outcast">
								
								<button type="submit">추방</button>
							</form>
							<form action="./entrust" method="post">
								<input type="hidden" name="nickName" value="${memberList.nickName}">
								<input type="hidden" name="socialNum" value="${detail.socialNum}">
								<input type="hidden" name="auth" value="entrust">
								
								<button type="submit"><!-- // data-bs-toggle="modal" data-bs-target="#entrustModal"> -->
									위임</button>
							</form>
						</c:if>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</c:if>
	<div>
		<form action="./delete" method="post">			
			<input name="id" type="hidden" value="${detail.socialNum}">
			<button type="button" data-bs-toggle="modal"
			data-bs-target="#removeModal">삭제</button>
			<button class="btn btn-primary" type="button" onclick="location.href='../social'">
				목록
			</button>
			<button class="btn btn-primary" type="button" onclick="location.href='../social/modify?id=${detail.socialNum}'">
				수정
			</button>
		</form>
	</div>
	<c:if test="${not empty sessionScope.loginData.nickName}">			<div>
		<div>
			<form action="./join" method="post">
				<input type="hidden" name="nickName" value="${sessionScope.loginData.nickName}">
				<input type="hidden" name="id" value="${sessionScope.loginData.id}">
				<input type="hidden" name="socialNum" value="${detail.socialNum}">
				<button type="submit">참가신청</button>
			</form>
		</div>
	</c:if>
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
		
		<div class="modal fade" id="entrustModal2" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title">방장 위임하기</h6>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-danger"
							data-bs-dismiss="modal" onclick="entrustSocial(${detail.socialNum})">
							확인
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="entrustModal" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title">방장 위임하기</h6>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body" id="entrust">
						<c:forEach items="${memberList}" var="memberList">
							<c:if test="${memberList.auth =='U'}">
								<tr>
									<td>
										<input type="radio" name="entrust" value="${memberList.nickName}">${memberList.nickName}
						
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-danger"
							data-bs-dismiss="modal" onclick="entrustSocial(${detail.socialNum},$("[name=entrus]").val();)">
							확인
						</button>
					</div>
				</div>
			</div>
		</div>
</section>
<script type="text/javascript">
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
		

function entrustSocial(socialNum,nickName) {
		var user = $("[name=entrus]").val();
			$.ajax({
				url: "./entrust",
				type: "post",
				data: {
					socialNum: ${detail.socialNum},
					nickName: nickName
				},
				dataType: "json",
				success: function(detail) {
					if(detail.code === "success") {
						alert("위임 완료");						
						location.href = "./mt/social?detail=${detail.socialNum}";
					} else if(social.code === "permissionError") {
						alert("권한이 오류");
					} else if(social.code === "notExists") {
						alert("이미 삭제되었습니다.")
					}
				}
			});
		}
function entrustSocial2(socialNum,nickName) {
	var user = $("[name=entrus]").val();
		$.ajax({
			url: "./entrust",
			type: "post",
			data: {
				socialNum: ${detail.socialNum},
				nickName: 데이비드
			},
			dataType: "json",
			success: function(detail) {
				if(detail.code === "success") {
					alert("위임 완료");						
					location.href = "./mt/social?detail=${detail.socialNum}";
				} else if(social.code === "permissionError") {
					alert("권한이 오류");
				} else if(social.code === "notExists") {
					alert("이미 삭제되었습니다.")
				}
			}
		});
	}
	
$('#notifySendBtn').click(function(e){
    let modal = $('.modal-content').has(e.target);
    let type = '70';
    let target = modal.find('.modal-body input').val();
    let content = modal.find('.modal-body textarea').val();
    let url = '${contextPath}/member/notify.do';
    // 전송한 정보를 db에 저장	
    $.ajax({
        type: 'post',
        url: '${contextPath}/member/saveNotify.do',
        dataType: 'text',
        data: {
            target: target,
            content: content,
            type: type,
            url: url
        },
        success: function(){    // db전송 성공시 실시간 알림 전송
            // 소켓에 전달되는 메시지
            // 위에 기술한 EchoHandler에서 ,(comma)를 이용하여 분리시킨다.
            socket.send("관리자,"+target+","+content+","+url);	
        }
    });
    modal.find('.modal-body textarea').val('');	// textarea 초기화
});
</script>
</body>
</html>