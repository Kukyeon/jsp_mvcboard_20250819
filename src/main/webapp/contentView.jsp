<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>  
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %> 

<%
	
	if(request.getAttribute("msg") != null){ // 웹 서블릿에서 넘겨준 값을 뺄때는 getattribute 사용
		String msginfo = request.getAttribute("msg").toString();
		out.println("<script>alert('"+msginfo+"');window.location.href='boardList.do';</script>");
	}
	//if(request.getParameter("msg") != null){
	//	out.println("<script>alert('"+msginfo+"');window.location.href='boardList.do';</script>");
	//}
	

%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>게시글 상세보기</title>
  <link rel="stylesheet" href="css/detail_style.css" />
</head>
<body>

  <div class="detail-container">
    <h2 class="detail-title">${bDto.btitle }</h2>

    <div class="detail-info">
      <span>작성자: <strong>${bDto.memberid }</strong></span>
      <span>조회수: <strong>${bDto.bhit }</strong></span>
      <span>작성일: <strong>${fn:substring(bDto.bdate, 0, 10)}</strong></span>
    </div>

    <div class="detail-content">
      <p>
        ${bDto.bcontent }
      </p>
    </div>

    <div class="detail-buttons">
  <a href="boardList.do" class="btn btn-list">목록으로</a>
  <a href="modify.do?bnum=${bDto.bnum}" class="btn btn-edit">수정하기</a>

  <form action="delete.do" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');" style="display:inline;">
    <input type="hidden" name="bnum" value="${bDto.bnum}" />
    <button type="submit" class="btn btn-delete">삭제하기</button>
  </form>
</div>
  </div>
</body>
</html>

