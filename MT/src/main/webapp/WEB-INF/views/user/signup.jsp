<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    
<!DOCTYPE html>

<html>
	<%@ include file="../module/head.jsp" %>
	<%@ include file="../module/nav.jsp" %>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
		$(function(){
			$("#submit").on("click", function(){
				
				if($("#email").val()==""){
					alert("아이디를 입력해주세요.");
					$("#email").focus();
					return false;
				}		
				 var idChkVal = $("#idChk").val();
				if(idChkVal == "N"){
					alert("인증절차 먼저 눌러주세요.");
					return false;
				}else if(idChkVal == "Y"){
					return true;
				}
				
			});
		})
		
		function fn_idChk(){

			if($("#email").val()==""){
				alert("아이디를 입력해주세요.");
				$("#id").focus();
				return false;
				}
			if($("#email").val()!=""){
				$.ajax({
					url : "./idChk",
					type : "POST",
					data : {
						email : $("#email").val(),
						type : "email"
						},
					success : function(data){
						if(data != 1){
							$("#idChk").attr("value", "N");
							alert("가입이력이 존재하는 아이디입니다.");
						}else if(data == 1){
							$.ajax({
								url : "./emailRes",
								type : "POST",
								data : {
									email : $("#email").val(),
									type : "sign"
									},
								success : function(data){
									$("#email").attr("readonly","true");
									$("#idChk").attr("value", "Y");
									$("#idChk").attr("disabled","disabled");
									$("#submit").removeAttr("disabled");
										alert("해당 메일로 인증번호가 전송되었습니다");
									
								}
							})
						}
					}
				})
			}
		}

	</script>
</head>
<body class="body">
<main class="form-signin">
	<div class="form-floating">
<h1 class="h3 mb-3 fw-normal">회원가입</h1>
		<form action="./sign" method="post" id="form">	
			아이디 
			<input class="form-control" type="email" name="email" id="email"/>
			<button class="idChk btn btn-warning" style="right" type="button" id="idChk" onclick="fn_idChk();" value="N">
				인증번호 전송
			</button>
			<br>	
			<div id="emailCheck">
				인증번호 
				<input class="form-control" type="text" name="auth" id="auth"/>
			
				<button class="w-100 btn btn-lg btn-warning" id="submit" name="submit" disabled>가입하기</button>
			</div>
		</form>
	</div>
</main>
	<c:if test="${not empty AuthMsg}">
		<script type="text/javascript">
			alert("${AuthMsg}");
		</script>
	</c:if>
	
	<c:if test="${not empty joinError}">
		<script type="text/javascript">
			alert("${joinError}");
		</script>
	</c:if>
</body>
</html>