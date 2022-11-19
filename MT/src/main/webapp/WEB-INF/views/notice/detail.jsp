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
<title>공지 사항</title>
</head>
<body>

<div class="text-center">
	<div class="mb-1">
		<h1>${data.title}
		</h1>

	</div>
	<div>
		 <label>작성일자 ${data.writeDate}</label>
	</div>
	<div>
		<p>${data.content}</p>
	</div>
</div>
<div class="text-center">
		<form action="./delete" method="post">	
		
			<button class="btn btn-sm btn-outline-dark" type="button" onclick="location.href='../social'">
				목록
			</button>		
			<c:if test="${sessionScope.loginData.email eq detail.email}">	
				<button class="btn btn-sm btn-outline-dark" type="button" onclick="location.href='../notice/modify?id=${detail.socialNum}'">
					수정
				</button>	
				<input name="id" type="hidden" value="${detail.socialNum}">
				<button class="btn btn-sm btn-outline-dark" type="button" data-bs-toggle="modal"
				data-bs-target="#removeModal">삭제</button>
			</c:if>
		</form>
	</div>
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
						<button type="button" class="btn btn-sm btn-danger" data-bs-dismiss="modal" onclick="deleteNotice()">
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
function deleteNotice() {	
			$.ajax({
				url: "./delete",
				type: "post",
				data: {
					id: ${data.noticeNum}
				},
				dataType: "json",
				success: function(data) {
					if(data.code === "success") {
						alert("삭제 완료");						
							location.href = "./mt/notice?category=${data.category}";
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