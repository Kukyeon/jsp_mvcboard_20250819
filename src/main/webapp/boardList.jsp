
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
  <header class="header">
    <div class="header-container">
      <div class="logo">미완성 페이지</div>
      <nav class="nav-menu">
        <ul>
          <li><a href="index.do">HOME</a></li>
          <li class="board-menu-item">
            <a href="boardList.do">게시판</a>
            <!-- 글쓰기 버튼을 게시판 메뉴 옆에 배치 -->
            <a href="write.do" class="write-btn-inline">글쓰기</a>
          </li>
          <li><a href="contact.html">고객센터</a></li>
          <li><a href="help.html">도움말</a></li>
        </ul>
      </nav>
    </div>
  </header>
  <!-- 헤더 끝 -->

  <div class="board-container">

    <h2 class="board-title">게시판</h2>

    <table class="board-table">
      <thead>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>날짜</th>
          <th>조회수</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="${bDtos}" var="bDto" varStatus="status">
        <tr>
          <td>${bDto.bnum }</td>
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
          <td>${fn:substring(bDto.bdate, 0, 10)}</td>
          <td>${bDto.bhit }</td>
       </tr>
      </c:forEach>
      </tbody>
    </table>

    <!-- 페이징 영역 -->
    <div class="pagination">
      <!-- 현재 페이지 및 총 페이지는 서버에서 받아오신다고 가정 -->
      <c:set var="currentPage" value="${currentPage}" />
      <c:set var="totalPages" value="${totalPages}" />

      <!-- 이전 페이지 링크 -->
      <c:if test="${currentPage > 1}">
        <a href="boardList.do?page=${currentPage - 1}" class="page-link">이전</a>
      </c:if>

      <!-- 페이지 번호 출력 (예: 1 2 3 4 5 ...) -->
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

      <!-- 다음 페이지 링크 -->
      <c:if test="${currentPage < totalPages}">
        <a href="boardList.do?page=${currentPage + 1}" class="page-link">다음</a>
      </c:if>
    </div>

  </div>

  <!-- 푸터 시작 -->
  <footer class="footer">
    <div class="footer-container">
      <div class="footer-info">
        <p>© 2025 MySite. All Rights Reserved.</p>
        <p>주소: 서울특별시 강남구 테헤란로 123 | 전화: 02-1234-5678 | 이메일: contact@mysite.com</p>
      </div>
      <div class="footer-links">
        <a href="privacy.html">개인정보처리방침</a> | 
        <a href="terms.html">이용약관</a> | 
        <a href="sitemap.html">사이트맵</a>
      </div>
    </div>
  </footer>
  <!-- 푸터 끝 -->

</body>
</html>
