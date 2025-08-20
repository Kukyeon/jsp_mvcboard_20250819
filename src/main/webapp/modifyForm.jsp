<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>게시글 수정</title>
  <link rel="stylesheet" href="css/modifyformStyle.css" />
</head>
<body>
<jsp:include page="header.jsp" />
  <div class="edit-container">
    <h1 class="edit-title">게시글 수정</h1>
    <form action="modifyOk.do" method="post">
      <!-- 게시글 고유 ID 숨김 -->
      <input type="hidden" name="bnum" value="${bDto.bnum }" />

      <label for="title">제목</label>
      <input type="text" id="title" name="title" value="${bDto.btitle} " required />

      <label for="content">내용</label>
      <textarea id="content" name="content" rows="10" required>${bDto.bcontent }</textarea>

      <label for="author">작성자</label>
      <input type="text" id="author" name="author" value="${bDto.memberid }" required />

      <div class="button-group">
        <button type="submit" class="save-btn">저장</button>
        <a href="javascript:history.go(-1)" class="cancel-btn">취소</a>
      </div>
    </form>
    
  </div>
  <jsp:include page="footer.jsp" />
</body>
</html>
