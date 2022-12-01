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
</head>
<body>
    <div id="msgStack"></div>
    <header>
   	 	<div class ="category-list text-center">
            <table class="text-center">
                <tr id="category" name="category">
                    <td onclick="location.href='./club'">전체보기</td>
                    <c:if test="${not empty field}">
                        <c:forEach items="${field}" var="field">
                            <td value="${field.category}" onclick="location.href='./club?category='+'${field.category}'">${field.category}</td>
                        </c:forEach>
                    </c:if>
                </tr>
            </table>
       </div>  
    </header>
    <section>
        <div class="socialSearchBar text-center">
	        <form method="get">
	            <div class="col-xs-8">
		            <input type="text" id="social-search" class="form-searchBar" name="search">
		            <button type="submit" id="btnSearch" class="btn btn=primary">찾기</button>
	    		</div>        
	        </form>
			<c:if test="${not empty sessionScope.loginData}">   
		        <form action="./club/create" method="get">
		    		<div class="col-xs-2">
						<input type="hidden" name="nickName" value="${sessionScope.nickName}">
						<input type="hidden" name="id" value="${sessionScope.id}">
			           	<button type="submit" id="create-button" class="createButton" 
			           	onclick="location.href='./club/create'">  
			        	 	만들기
			        	 </button>           
		    		</div>     
		        </form>
	        </c:if>
        </div>
        <div>
            <table style="text-align: center;border:1px solid #dddddd">
				<c:if test="${not empty list}">
					<c:forEach items="${list}" var="list">
						<c:url var="listUrl" value="${type}/detail">
							<c:param name="id">${list.socialNum}</c:param>
						</c:url>
                          <tr style="text-align: center;border:1px solid #dddddd"data-bs-toggle="modal"
								data-bs-target="#removeModal" value ="${list.socialNum}">
                              <td style="text-align: center;border:1px solid #dddddd" rowspan="3"> 클럽이미지</td>
                              <th style="text-align: center;border:1px solid #dddddd">${list.title}</th>
                              <th style="text-align: center;border:1px solid #dddddd" rowspan="2">
								<c:forEach items="${real}" var="real">  
										<c:if test="${real.socialNum eq list.socialNum}">
											${real.real} 명
										</c:if>
								</c:forEach>
							  </th>
                              <td style="text-align: center;border:1px solid #dddddd" rowspan="3"> ${list.category}</td>
                          </tr>
                          <tr style="text-align: center;border:1px solid #dddddd" onclick="location.href='${listUrl}'">
                              <td style="text-align: center;border:1px solid #dddddd">${list.nickName}</td>
                          </tr>
                          <tr  style="text-align: center;border:1px solid #dddddd" onclick="location.href='${listUrl}'">
                              <td style="text-align: center;border:1px solid #dddddd" colspan="2">${list.preview}</td>
                          </tr>
                    </c:forEach>
                </c:if>
            </table>
        </div>
 <%@ include file="../module/paging.jsp"%>
    </section>  
	<script>
    function searchFunction() {
		var searchVal = $("[name=search]").val();
		
		$j.ajax({
		    url : "/social?search",
		    dataType: "json",
		    type: "POST",
		    data : searchVal  	
    		},
    		success: function(){ 
    			
    		}
    		});
		}
	
 // 전역변수 설정
 var socket  = null;
 $(document).ready(function(){
     // 웹소켓 연결
     sock = new SockJS("<c:url value="/echo-ws"/>");
     socket = sock;

     // 데이터를 전달 받았을때 
     sock.onmessage = onMessage; // toast 생성
     ...
 });

 // toast생성 및 추가
 function onMessage(evt){
     var data = evt.data;
     // toast
     let toast = "<div class='toast' role='alert' aria-live='assertive' aria-atomic='true'>";
     toast += "<div class='toast-header'><i class='fas fa-bell mr-2'></i><strong class='mr-auto'>알림</strong>";
     toast += "<small class='text-muted'>just now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'>";
     toast += "<span aria-hidden='true'>&times;</span></button>";
     toast += "</div> <div class='toast-body'>" + data + "</div></div>";
     $("#msgStack").append(toast);   // msgStack div에 생성한 toast 추가
     $(".toast").toast({"animation": true, "autohide": false});
     $('.toast').toast('show');
 };	
 
 </script>
<!--  모달 게시판 디테일 -->
	<div class="modal fade" id="removeModal" tabindex="-1"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title">삭제 확인</h6>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-sm btn-danger"
						data-bs-dismiss="modal" onclick="deleteSocial(list.socialNum})">확인</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
