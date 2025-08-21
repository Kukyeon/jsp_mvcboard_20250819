<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>게시글 상세보기</title>
  <link rel="stylesheet" href="css/detail_style.css" />
</head>
<body>



<%
  if (request.getAttribute("msg") != null) {
    String msginfo = request.getAttribute("msg").toString();
    out.println("<script>alert('" + msginfo + "'); window.location.href='boardList.do';</script>");
  }
%>

<div class="detail-container">
  <h2 class="detail-title">${bDto.btitle}</h2>

  <div class="detail-info">
    <span>작성자: <strong>${bDto.memberid}</strong></span>
    <span>이메일: <strong>${bDto.member.memberemail}</strong></span>
    <span>조회수: <strong>${bDto.bhit}</strong></span>
    <span>작성일: <strong>${fn:substring(bDto.bdate, 0, 10)}</strong></span>
  </div>

  <div class="detail-content">
    <p>${bDto.bcontent}</p>
  </div>

  <div class="detail-buttons">
    <a href="boardList.do" class="btn btn-list">목록으로</a>
    
    <c:if test="${sessionScope.sessionId == bDto.memberid }"> <!-- 단 한명만 수정가능하게 하려면 아이디입력 -->
    <a href="modify.do?bnum=${bDto.bnum}" class="btn btn-edit">수정하기</a>
    <form action="delete.do" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');" style="display:inline;">
      <input type="hidden" name="bnum" value="${bDto.bnum}" />
      <button type="submit" class="btn btn-delete">삭제하기</button>
      </c:if>
      
    </form>
  </div>
</div>



</body>
</html>


