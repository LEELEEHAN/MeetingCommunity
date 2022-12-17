<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<nav>
	<div>
			<ul class="pagination justify-content-center">
				<c:if test="${pageData.hasPrevPage()}">
					<c:if test="${empty keyword and empty search}">
						<c:url var="boardPagingUrl" value="${type}"/>
						<li class="page-item">
							<a class="page-link" href="${boardPagingUrl}?page=1"><<</a>
						</li>
						<li class="page-item btn btn-sm btn-outline-dark"><a class="page-link"
							href="${boardPagingUrl}?page=${pageData.prevPageNumber}">Prev</a>
						</li>
					</c:if>
					<c:if test="${not empty keyword}">
						<c:url var="boardPagingUrl" value="${type}">
							<c:param name="category">${keyword}</c:param>
						</c:url>
							<li class="page-item btn btn-sm btn-outline-dark">
							<a class="page-link" href="${boardPagingUrl}&page=1"><<</a>
						</li>
						<li class="page-item btn btn-sm btn-outline-dark"><a class="page-link"
							href="${boardPagingUrl}$page=${pageData.prevPageNumber}">Prev</a>
						</li>
					</c:if>
					<c:if test="${not empty search}">
						<c:url var="boardPagingUrl" value="${type}">
							<c:param name="search">${search}</c:param>
						</c:url>
							<li class="page-item">
							<a class="page-link" href="${boardPagingUrl}&page=1"><<</a>
						</li>
						<li class="page-item"><a class="page-link"
							href="${boardPagingUrl}$page=${pageData.prevPageNumber}">Prev</a>
						</li>
					</c:if>
				</c:if>
				<c:choose>
					<c:when test="${pageData.currentPageNumber eq 1}">
						<c:forEach
							items="${pageData.getPageNumberList(pageData.currentPageNumber - 3, pageData.currentPageNumber + 4)}"
							var="num">
							<c:if test="${not empty keyword}">
								<c:url var="boardPagingUrl" value="${type}">
									<c:param name="category">${keyword}</c:param>
								</c:url>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
									</li>
								</c:if>
								<c:if test="${not empty search}">
									<c:url var="boardPagingUrl" value="${type}">
										<c:param name="search">${search}</c:param>
									</c:url>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
									</li>
								</c:if>
								<c:if test="${empty keyword and empty search}">
									<c:url var="boardPagingUrl" value="${type}"/>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}?page=${num}">${num}</a>
									</li>
								</c:if>
						</c:forEach>
					</c:when>
					
					
					<c:when test="${pageData.currentPageNumber eq 2}">
						<c:forEach
							items="${pageData.getPageNumberList(pageData.currentPageNumber - 3, pageData.currentPageNumber + 3)}"
							var="num">
							<c:if test="${not empty keyword}">
								<c:url var="boardPagingUrl" value="${type}">
									<c:param name="category">${keyword}</c:param>
								</c:url>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
									</li>
								</c:if>
								<c:if test="${not empty search}">
									<c:url var="boardPagingUrl" value="${type}">
										<c:param name="search">${search}</c:param>
									</c:url>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
									</li>
								</c:if>
								<c:if test="${empty keyword and empty search}">
									<c:url var="boardPagingUrl" value="${type}"/>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}?page=${num}">${num}</a>
									</li>
								</c:if>
						</c:forEach>
					</c:when>
					
					<c:when test="${pageData.currentPageNumber eq pageData.lastPage}">
							<c:forEach
								items="${pageData.getPageNumberList(pageData.currentPageNumber - 4, pageData.currentPageNumber + 2)}"
								var="num">								
								<c:if test="${not empty keyword}">
								<c:url var="boardPagingUrl" value="${type}">
									<c:param name="category">${keyword}</c:param>
								</c:url>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
									</li>
								</c:if>
								<c:if test="${not empty search}">
									<c:url var="boardPagingUrl" value="${type}">
										<c:param name="search">${search}</c:param>
									</c:url>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
									</li>
								</c:if>
								<c:if test="${empty keyword and empty search}">
									<c:url var="boardPagingUrl" value="${type}"/>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}?page=${num}">${num}</a>
									</li>
								</c:if>
							</c:forEach>
						</c:when>
					
						<c:when test="${pageData.currentPageNumber eq pageData.lastPage-1}">
							<c:forEach
								items="${pageData.getPageNumberList(pageData.currentPageNumber - 3, pageData.currentPageNumber + 2)}"
								var="num">			
								<c:if test="${not empty keyword}">
								<c:url var="boardPagingUrl" value="${type}">
									<c:param name="category">${keyword}</c:param>
								</c:url>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
									</li>
								</c:if>
								<c:if test="${not empty search}">
									<c:url var="boardPagingUrl" value="${type}">
										<c:param name="search">${search}</c:param>
									</c:url>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
									</li>
								</c:if>
								<c:if test="${empty keyword and empty search}">
									<c:url var="boardPagingUrl" value="${type}"/>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}?page=${num}">${num}</a>
									</li>
								</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach
								items="${pageData.getPageNumberList(pageData.currentPageNumber - 2, pageData.currentPageNumber + 2)}"
								var="num">
								<c:if test="${not empty keyword}">
									<c:url var="boardPagingUrl" value="${type}">
										<c:param name="category">${keyword}</c:param>
									</c:url>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
									</li>
								</c:if>
								<c:if test="${not empty search}">
									<c:url var="boardPagingUrl" value="${type}">
										<c:param name="search">${search}</c:param>
									</c:url>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}&page=${num}">${num}</a>
									</li>
								</c:if>
								<c:if test="${empty keyword and empty search}">
									<c:url var="boardPagingUrl" value="${type}"/>
									<li
										class="page-item ${pageData.currentPageNumber eq num ? 'active' : ''}">
										<a class="page-link" href="${boardPagingUrl}?page=${num}">${num}</a>
									</li>
								</c:if>
							</c:forEach>
						</c:otherwise>			
					</c:choose>
				<c:if test="${pageData.hasNextPage()}">
				
					<c:if test="${empty keyword and empty search}">
						<c:url var="boardPagingUrl" value="${type}"/>
						<li class="page-item">
						<li class="page-item"><a class="page-link"
							href="${boardPagingUrl}?page=${pageData.nextPageNumber}">Next</a>
						</li>
						<li class="page-item"><a class="page-link"
							href="${boardPagingUrl}?page=${pageData.lastPage}">>>></a>
						</li>
					</c:if>
					<c:if test="${not empty keyword}">
						<c:url var="boardPagingUrl" value="${type}">
							<c:param name="category">${keyword}</c:param>
						</c:url>
						<li class="page-item"><a class="page-link"
							href="${boardPagingUrl}&page=${pageData.nextPageNumber}">Next</a>
						</li>
						<li class="page-item"><a class="page-link"
							href="${boardPagingUrl}&page=${pageData.lastPage}">>>></a>
						</li>
					</c:if>
					<c:if test="${not empty search}">
						<c:url var="boardPagingUrl" value="${type}">
							<c:param name="search">${search}</c:param>
						</c:url>
						<li class="page-item"><a class="page-link"
							href="${boardPagingUrl}&page=${pageData.nextPageNumber}">Next</a>
						</li>
						<li class="page-item"><a class="page-link"
							href="${boardPagingUrl}&page=${pageData.lastPage}">>>></a>
						</li>
					</c:if>
				</c:if>
			</ul>
		</div>	
</nav>