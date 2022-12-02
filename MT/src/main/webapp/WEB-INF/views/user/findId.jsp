<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    
<!DOCTYPE html>

<html>
<head>


	<%@ include file="../module/head.jsp" %>
	<%@ include file="../module/nav.jsp" %>
<title>아이디 찾기</title>
<script type="text/javascript">
		function nickCheck(){

			if($("#name").val()==""){
				alert("이름를 입력해주세요.");
				$("#name").focus();
				return false;
				}
			if($("#phone").val()==""){
				alert("휴대전화번호를 입력해주세요.");
				$("#phone").focus();
				return false;
				}
			if($("#name").val()!=""){
				$.ajax({
					url : "/mt/findId",
					type : "POST",
					data : {
						name : $("#name").val(),
						phone : $("#phone").val()
						},
					dataType: "json",
					success : function(data){
						if(data.code === success){
							alert(data.user);
						}else if(data.code === fail){
							alert("계정이 조회되지않습니다.");
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
			핸드폰번호 
			<input class="form-control" type="text" name="phone"  id="phone"/>
			<br>
			생년월일 
			<input class="form-control" type="date" name="birth"  id="birth"placeholder="birth"/>
			<br>
			<button class="w-100 btn btn-lg btn-warning" style="float:right" type="button" id="idChange" onclick="nickCheck();" value="N">
				아이디바꾸기
			</button>
		</form>
	</div>
</main>
</body>
</html>