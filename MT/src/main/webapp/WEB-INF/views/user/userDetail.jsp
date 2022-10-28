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

function nickNameCheck(){
			$.ajax({
				url : "./nickNameCheck",
				type : "POST",
				dataType : "json",
				data : {
					nickName : $("#nickName").val()
					},
				success : function(data){
					alert("실행",data);
					if(data == 1){
						$("#nickNameCheck").attr("value", "N");
						alert("사용불가능한 닉네임입니다.");
					}else if(data == 0){
						$("#nickNameCheck").attr("value", "Y");
						alert("사용가능한 닉네임입니다.");
					}
				}
			})
		}
	</script>
</head>
<body>
<form action="./userDetail" method="post">
	닉네임 설정 
	<input class="form-control" type="text" name="nickName" id="nickName"/>
	<button class="idChk btn btn-warning" style="float:right" type="button" id="idChk" onclick="nickNameCheck();" value="N">
		중복확인
	</button>
	<button type="submit">
		저장하기
	</button>
</form>
</body>
</html>