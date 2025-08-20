<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>  
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>   
    
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>게시판 목록</title>
  <link rel="stylesheet" href="css/boardStyle.css" />
</head>
<body>

  <!-- 헤더 시작 -->
<jsp:include page="header.jsp" />
  <!-- 헤더 끝 -->

  <div class="board-container">

    <!-- 게시판 제목 -->
    <h2 class="board-title">게시판</h2>
    <c:if test="${not empty sessionScope.sessionId }">
	<span style="color: gray;">${sessionScope.sessionId}로그인중</span>
	</c:if>
    <!-- 글쓰기 버튼: 테이블 상단 우측 -->
    <div class="write-btn-top-container">
      <a href="write.do" class="write-btn-top">글쓰기</a>
    </div>

    <!-- 게시판 테이블 -->
    <table class="board-table">
      <thead>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>이메일</th>
          <th>날짜</th>
          <th>조회수</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="${bDtos}" var="bDto" varStatus="status">
        <tr>
          <td>${bDto.bno }</td>
          <td>
            <c:choose>
              <c:when test="${fn:length(bDto.btitle) > 15}">
                <a href="content.do?bnum=${bDto.bnum }" class="post-link">${fn:substring(bDto.btitle, 0, 15)}...</a>
              </c:when>
              <c:otherwise>
                <a href="content.do?bnum=${bDto.bnum }" class="post-link">${bDto.btitle}</a>
              </c:otherwise>
            </c:choose>
          </td>
          <td>${bDto.memberid }</td>
          <td>${bDto.member.memberemail }</td>
          <td>${fn:substring(bDto.bdate, 0, 10)}</td>
          <td>${bDto.bhit }</td>
       </tr>
      </c:forEach>
      </tbody>
    </table>

    <!-- 페이징 -->
    <div class="pagination">
      <c:set var="currentPage" value="${currentPage}" />
      <c:set var="totalPages" value="${totalPages}" />

      <c:if test="${currentPage > 1}">
        <a href="boardList.do?page=${currentPage - 1}" class="page-link">이전</a>
      </c:if>

      <c:forEach begin="1" end="${totalPages}" var="i">
        <c:choose>
          <c:when test="${i == currentPage}">
            <span class="page-link current">${i}</span>
          </c:when>
          <c:otherwise>
            <a href="boardList.do?page=${i}" class="page-link">${i}</a>
          </c:otherwise>
        </c:choose>
      </c:forEach>

      <c:if test="${currentPage < totalPages}">
        <a href="boardList.do?page=${currentPage + 1}" class="page-link">다음</a>
      </c:if>
    </div>

  </div>

  <!-- 푸터 -->
<jsp:include page="footer.jsp" />

</body>
</html>

