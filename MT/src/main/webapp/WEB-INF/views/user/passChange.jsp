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
<meta charset="UTF-8">
<title>비밀 번호 변경</title>
<script type="text/javascript">
$(function(){
	$("#submit").on("click", function(){
		if($("#password").val()==""){
			alert("비밀번호를 입력해주세요.");
			$("#password").focus();
			return false;
		}
		if($("#password").val() != $("#pw_chk").val()){
            alert("비밀번호가 일치하지 않습니다.");
            $("#pw_chk").focus();
            return false;
        }
		$.ajax({
			url : "./passCheck",
			type : "POST",
			data : {
				name : $("#name").val(),
				email : $("#email").val()
				},
			success : function(data){
				if(data == 1){
					alert("변경되었습니다."+"\n"+"다시 로그인해주세요");
					location.href = "/mt/login";
				}else if(data != 1){
					alert("아작스통과.");
					return true;
				}
			}
		});
	});
	})
	
	
	function passwordSend(){

		if($("#password").val()==""){
			alert("비밀번호를 입력해주세요.");
			$("#password").focus();
			return false;
		}
		if($("#password").val() != $("#pw_chk").val()){
            alert("비밀번호가 일치하지 않습니다.");
            $("#pw_chk").focus();
            return false;
        }
		$.ajax({
			url : "/mt/passCheck",
			type : "POST",
			data : {
				email : $("#email").val(),
				password : $("#password").val()
				},
			success : function(data){
					if(data>=1){
			            alert("현재 사용중인 비밀번호입ㄴ디ㅏ");
					} else {
						$.ajax({
							url : "/mt/setPassword",
							type : "POST",
							data : {
								email : $("#email").val(),
								password : $("#password").val(),
								name : $("#name").val()
								},
							success : function(data){
									if(data==1){
										alert("변경되었습니다."+"\n"+"다시 로그인해주세요");
										location.href = "/mt/login";
									} else {
										alert("오루가 발생하였습니다."+"\n"+"다시 시도해주세요");											
									}
								}
							
						})	
					}
				}
			
		})
	}
</script>





</head>
<body> 

	<c:if test="${not empty loginMsg}">
		<script type="text/javascript">
			alert("${loginMsg}");
		</script>
	</c:if>
	<div class="form-floating">
	<form method="post" id="form">
		<input type="hidden" name="email" id="email" value="${sessionScope.loginData.email}"/>
		<input type="hidden" name="email" id="name" value="${sessionScope.loginData.name}"/>	
		비밀번호 
		<input class="form-control" type="password" name="password"  id="password" placeholder="password" />
		비밀번호 확인 
		<input class="form-control" type="password" name="pw_chk"  id="pw_chk"placeholder="password check"/>		
		<br>	
		<button class="btn btn-sm btn-outline-dark"  style="float:right" type="button" id="passChange" onclick="passwordSend();">
			변경하기
		</button>
	</form>
<br>
	</div>
</body>
</html>