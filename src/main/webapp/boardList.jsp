
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
      <c:forEach items="${bDtos}" var="bDto">
        <tr>
          <td>${bDto.bnum }</td>
          <td>
          <c:choose>
          <c:when test="${fn:length(boardDto.btitle) > 15}">
          <a href="#" class="post-link">${fn:substring(bDto.btitle, 0, 15)}...</a>
          </c:when>
          <c:otherwise>
          	${fn:substring(bDto.btitle, 0, 15)}
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

    <div class="button-area"><br>
      <a href="write.do" class="write-btn">글쓰기</a>
      <a href="index.jsp" class="write-btn">메인으로</a>
    </div>
  </div>
</body>
</html>
