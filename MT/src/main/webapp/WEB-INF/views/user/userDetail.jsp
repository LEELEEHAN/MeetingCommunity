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
<title>Insert title here</title>
<script type="text/javascript">
$(function(){
	$("#submit").on("click", function(){
		if($("#name").val()==""){
			alert("이름을 입력해주세요.");
			$("#name").focus();
			return false;
		}
		if($("#nickName").val()==""){
			alert("닉네임을 입력해주세요.");
			$("#nickName").focus();
			return false;
		}
		 var idChkVal = $("#nickNameCheck").val();
			if(idChkVal == "N"){
				alert("중복확인 버튼을 눌러주세요.");
				return false;
			}else if(idChkVal == "Y"){
				return true;
		}
		
		if($("#password").val()==""){
			alert("비밀번호를 입력해주세요.");
			$("#pw").focus();
			return false;
		}
		if($("#password").val() != $("#pw_chk").val()){
            alert("비밀번호가 일치하지 않습니다.");
            $("#pw_chk").focus();
            return false;
          }
		
		if($("#phone").val()==""){
			alert("핸드폰번호를 입력해주세요.");
			$("#phone").focus();
			return false;
		}
		
		if($("#birth").val()==""){
			alert("생년월일을 입력해주세요.");
			$("#bitrh").focus();
			return false;
		}
		var bCheck = RegExp(/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/);
		if(!bCheck.test($('#birth').val())){
			alert("생년월일은 \'yyyy-mm-dd\' 형식으로 입력해주세요");
		}
		if($("input[name=gender]:radio:checked").length < 1){
			alert("성별을 선택해주세요.");
			return false;
		}
		if($("#nickName").val()!=""){
			$.ajax({
				url : "./login/nickNameCheck",
				type : "POST",
				data : {
					nickName : $("#nickName").val()
					},
				success : function(data){
					if(data != 1){
						$("#nickNameCheck").attr("value", "N");
						alert("사용불가능한 닉네임입니다.");
					}else if(data == 1){
						$("#nickNameCheck").attr("value", "Y");
						alert("사용가능한 닉네임입니다.");
					}
				}
			})
		}
		
	});
})
		
function nickCheck(){
			
	if($("#nickName").val()==""){
		alert("닉네임을 입력해주세요.");
		$("#nickName").focus();
		return false;
		}
	if($("#nickName").val()!=""){
			$.ajax({
				url : "/mt/idChk",
				type : "POST",
				data : {
					email : $("#nickName").val(),
					type : "nickName"
					
					},
				success : function(data){
					if(data != 1){
						$("#nickNameCheck").attr("value", "N");
						alert("사용불가능한 닉네임입니다.");
					}else if(data == 1){
						$("#nickNameCheck").attr("value", "Y");
						alert("사용가능한 닉네임입니다.");
					}
				}
			})
		}
	}
		
		


		
		
		
	</script>
</head>
<body>

	<main class="form-signin">
	<div class="form-floating">
		<h1 class="h3 mb-3 fw-normal">회원가입</h1>
			<form action="/mt/login/userDetail" method="post" id="form">
			이메일 
			<input class="form-control" type="text" name="email" id="email" value="${sessionScope.email}" readonly/>
			<br>
			<c:choose>
				<c:when test="${not empty kakao}">
					<input type="hidden" name="password" id="password" value="kakao"/>	
					<input type="hidden" name="kakao" id="kakao" value="kakao"/>		 
					이름 
					<input class="form-control" type="text" name="name" id="name"  value="${sessionScope.name}" readonly/>
				</c:when>
				<c:otherwise>
					이름 
					<input type="hidden" name="kakao" id="kakao" value="mo"/>		 
					<input class="form-control" type="text" name="name" id="name"/>				
				</c:otherwise>
			</c:choose>						
			<br>
			닉네임 설정 
			<input class="form-control" type="text" name="nickName" id="nickName"/>
			<button class="nickNameCheck btn btn-warning" style="float:right" type="button" id="nickNameCheck" onclick="nickCheck();" value="N">
				중복확인
			</button>
				
			<br>
		
			<c:if test="${empty kakao}">
				휴대전화 
				<input class="form-control" type="text" name="phone" id="phone"/>			
				<br>
				비밀번호 
				<input class="form-control" type="password" name="password"  id="password"placeholder="password" />
				<br>
				비밀번호 확인 
				<input class="form-control" type="password" name="pw_chk"  id="pw_chk"placeholder="password check"/>
				<br>
				
				생년월일 
				<input class="form-control" type="date" name="birth"  id="birth"placeholder="birth"/>
				<br>
				<div class="gender1">
					성별 
					<input type="radio" name="gender" value="M"  id="gender"/>남 
					<input type="radio" name="gender" value="F"  id="gender"/>여<br>
				</div>
			</c:if>
			<br>
			<button class="w-100 btn btn-lg btn-warning" id="submit">가입하기</button>
		</form>
	</div>
	
	
	<c:if test="${not empty kakaoLogin}">
		<script type="text/javascript">
			alert("${kakaoLogin}");
		</script>
	</c:if>
</main>
</form>
</body>
</html>