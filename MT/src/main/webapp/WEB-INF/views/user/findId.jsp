<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    
<!DOCTYPE html>

<html>
<head>
<c:url var="bs5" value="/static/bs5" />
<c:url var="jQuery" value="/static/js" />
<link rel="stylesheet" type="text/css" href="${bs5}/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
<script type="text/javascript" src="${bs5}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${jQuery}/jquery-3.6.0.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
		
		function idChk(){

			if($("#name").val()==""){
				alert("아이디를 입력해주세요.");
				$("#name").focus();
				return false;
				}
			if($("#name").val()!=""){
				$.ajax({
					url : "./idChk",
					type : "POST",
					data : {
						id : $("#name").val()
						},
					success : function(data){
						if(data != 1){
							alert("중복된 아이디입니다.");
						}else if(data == 1){
							alert("사용가능한 아이디입니다.");
						}
					}
				})
			}
		}

	</script>
</head>
<body>
<body class="body">
<main class="form-signin">
	<div class="form-floating">
<h1 class="h3 mb-3 fw-normal">회원가입</h1>
		<form action="./findId" method="post" id="form">
			
			아이디 
			<input class="form-control" type="text" name="name" id="name"/>
			<button class="idChk btn btn-warning" style="float:right" type="button" id="idChk" onclick="fn_idChk();" value="N">
				중복확인
			</button>
			
			핸드폰번호 
			<input class="form-control" type="text" name="phone"  id="phone"/>
			<br>
		</form>
	</div>
</main>
</body>
</html>