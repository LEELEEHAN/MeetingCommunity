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
<title>관심 설정</title>
</head>
<body>

<div class ="category-list text-center">
	<form action="/mt/mypage/favorite" method="post" id="form">
		<table class="text-center">
			<c:if test="${not empty field}">
				<c:forEach items="${field}" var="field">
					<tr>
						<td><input type="checkbox" name="category" value="${field.category}"><label>${field.category}</label></td>                    
					</tr>    
				</c:forEach>
			</c:if>
		</table>
		<button type="submit"class="btn btn-sm btn-outline-dark"  >
		 	저장하기
		</button>
	</form>
</div>  

</body>
</html>