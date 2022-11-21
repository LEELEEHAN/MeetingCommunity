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
<title>공지사항 작성</title>

</head>
<script type="text/javascript">
	function formCheck(form) {
		if(form.title.value === undefined || form.title.value.trim() === "") {
			var modal = new bootstrap.Modal(document.getElementById("errorModal"), {
				keyboard: false
			});
			modal.show();
			return;
		}
		form.submit();
	}
</script>
<body>
	<header></header>
	<section class="container">
		<div class="mt-3">
		
			<c:url var="boardAddUrl" value="/club/board/write" />
			<form ${empty data? 'action="./write"':'action="./modify"'} method="post">
				<div class="mb-3">
					<input class="form-control" type="text" name="title" value="${data.title}" placeholder="제목을 입력하세요.">
				</div>				
				
				<div class="mb-3">
					<textarea class="form-control" name="contents" rows="8" value="${data.content}" placeholder="내용을 입력하세요.">${data.content}</textarea>
				</div>
				<div class="mb-3 text-end">
					<c:if test="${not empty data}">
						<input type="hidden"  name="boardNum" value="${id}">
					</c:if>
					<input type="hidden"  name="category" value="${category}">
					<input type="hidden"  name="socialNum" value="${socialNum}">
					<button class="btn btn-primary" type="button" onclick="formCheck(this.form);">
					저장
					</button>
				</div>
			</form>
		</div>
		
		<div class="modal fade" id="errorModal" tabindex="-1" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title">입력 오류</h6>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body">
						제목은 공란을 입력할 수 없습니다.<br>
						반드시 제목을 입력하세요.
						
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-danger" data-bs-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	<footer></footer>
	<c:url var="upload" value="/upload/image" />
	<script type="text/javascript">
		CKEDITOR.replace("content", {
			filebrowserUploadUrl: "${upload}?type=image"
		})
	</script>
	<c:if test="${not empty error}">
		<script type="text/javascript">
			alert("${error}");
		</script>
	</c:if>
</body>
</html>
