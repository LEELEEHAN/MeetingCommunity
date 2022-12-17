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
	if($("#birth").val()==""){
		alert("휴대전화번호를 입력해주세요.");
		$("#birth").focus();
		return false;
	}
	$.ajax({
		url : "/mt/findId",
		type : "POST",
		data : {
			name : $("#name").val(),
			phone : $("#phone").val(),
			birth : $("#birth").val()
			},
			dataType: "json",
		success : function(data){
			if(data.code === "success"){
				alert("조회결과입니다"+"\n"+data.user);
			}else if(data.code === "fail"){
				alert("계정이 조회되지않습니다.");
			}
		}
	})
}

//---------------------------------------------------------------------
function passwordSend(){

	if($("#email2").val()==""){
		alert("아이디를 입력해주세요.");
		$("#email2").focus();
		return false;
		}
	if($("#name2").val()==""){
		alert("이름을 입력해주세요.");
		$("#name2").focus();
		return false;
		}
	if($("#phone2").val()==""){
		alert("핸드폰 번호를 입력해주세요.");
		$("#phone2").focus();
		return false;
		}
		$.ajax({
			url : "/mt/idChk",
			type : "POST",
			data : {
				email : $("#email2").val(),
				name : $("#name2").val(),
				phone : $("#phone2").val(),
				type : "pass"
				},
			success : function(data){
				if(data == 1){
					alert("해당 조회사항이 없습니다");
				}else if(data != 1){
					$.ajax({
						url : "/mt/passwordSend",
						type : "POST",
						data : {
							email : $("#email2").val(),
							name : $("#name2").val(),
							phone : $("#phone2").val(),
							type : "pass"							
							},
						success : function(data){
							location.href = "/mt/login";
							alert("해당 메일로 인증번호가 전송되었습니다");
							
						}
					})
				}
			}
		})
	
}




</script>
</head>
<body>
<body class="body">
<main class="form-signin">
	<div class="form-floating">
		<h1 class="h3 mb-3 fw-normal">아이디 찾기</h1>
		<form action="./findId" method="post" id="form">
			
			이름 
			<input class="form-control" type="text" name="name" id="name" placeholder="이름"/>	
			핸드폰번호 
			<input class="form-control" type="text" name="phone"  id="phone" placeholder="-뺴고 입력해주세요"/>
			생년월일 
			<input class="form-control" type="date" name="birth"  id="birth"/>
			<br>
			<button class="btn btn-sm btn-outline-dark" style="float:right" type="button" id="idChange" onclick="nickCheck();">
				아이디 조회
			</button>
			<br>
			
		</form>
	</div>
	<div class="form-floating">
		<h1 class="h3 mb-3 fw-normal">비밀 번호 찾기</h1>
			
		<form action="./reId" method="post" id="form">
			아이디 
			<input class="form-control" type="text" name="email" id="email2" placeholder="example@example.com"/>	
			이름 
			<input class="form-control" type="text" name="name" id="name2" placeholder="이름"/>	
			핸드폰번호 
			<input class="form-control" type="text" name="phone"  id="phone2" placeholder="-뺴고 입력해주세요"/>
			<br>
			<button class="btn btn-sm btn-outline-dark"  style="float:right" type="button" id="passChange" onclick="passwordSend();">
				비밀번호 메일전송
			</button>
		</form>
			
	</div>
	
</main>
</body>
</html>