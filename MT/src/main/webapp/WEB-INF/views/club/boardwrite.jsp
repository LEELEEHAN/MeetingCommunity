<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<%@ include file="../module/head.jsp" %>
<%@ include file="../module/nav.jsp" %>
<script type="text/javascript" src="/mt/static/ckeditor/ckeditor.js"></script>
<title>공지사항 작성</title>

</head>
</script>
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
	function uploadCheck(element) {
		var files = element.files;
		
		var modal = new bootstrap.Modal(document.getElementById("errorModal"), {
			keyboard: false
		});
		var title = modal._element.querySelector(".modal-title");
		var body = modal._element.querySelector(".modal-body");
		
		if(files.length > 3) {
			title.innerText = "파일 업로드 오류";
			body.innerText = "파일 업로드는 최대 3개로 제한되어 있습니다.";
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
<body>
	<header></header>
		<div class="mt-3 container">
		
			<c:url var="boardAddUrl" value="/club/board/write" />
			<form ${empty data? 'action="./write"':'action="./modify"'} method="post" enctype="multipart/form-data">
				<div>
					<h3>글쓰기 - ${category == 'qna'? 'QnA' : category == 'info'? '정보' : '자유' }</h3>
				</div>
				<div class="mb-3">
					<input class="form-control" type="text" name="title" value="${data.title}" placeholder="제목을 입력하세요.">
				</div>				
				
				<div class="mb-3">
					<textarea class="form-control"id="contents" name="contents" rows="8" placeholder="내용을 입력하세요.">${data.contents}</textarea>		
				</div>
				<div class="mb-3">
					<input class="form-control" type="file" onchange="uploadCheck(this);" name="fileUpload" multiple>
				</div>
				<div class="mb-3 text-end">
					<c:if test="${not empty data}">
						<input type="hidden"  name="boardNum" value="${data.boardNum}">
					<input type="hidden"  name="category" value="${data.category}">
					</c:if>
					<c:if test="${empty data}">
						<input type="hidden"  name="category" value="${category}">
					</c:if>
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
		<div class="modal fade" id="errorModal2" tabindex="-1" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h6 class="modal-title">입력 오류</h6>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body">
						내용은 공란을 입력할 수 없습니다.<br>
						반드시 내용을 입력하세요.
						
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-danger" data-bs-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	<footer></footer>
	<c:url var="upload" value="/upload/image" />
	<script type="text/javascript">
		CKEDITOR.replace("contents", {
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
