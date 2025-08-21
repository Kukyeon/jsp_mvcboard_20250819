<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>글 작성</title>
  <link rel="stylesheet" href="css/common.css" />
  <link rel="stylesheet" href="css/write_style.css" />
  <link rel="stylesheet" href="css/header.css" />
  <link rel="stylesheet" href="css/footer.css" />
</head>
<body>

  <!-- 헤더 -->
  <jsp:include page="header.jsp" />

  <!-- 글쓰기 본문 -->
  <main class="main-content">
    <div class="write-container">
      <h1 class="write-title">글 작성하기</h1>
      <form action="writeOk.do" method="post">
        <label for="title">제목</label>
        <input type="text" id="title" name="title" placeholder="제목을 입력하세요" required />

        <label for="content">내용</label>
        <textarea id="content" name="content" rows="10" placeholder="내용을 입력하세요" required></textarea>

        <label for="author">작성자</label>
        <input type="text" id="author" name="author" value="${sessionScope.sessionId}" readonly />

        <div class="button-group">
          <button type="submit" class="submit-btn">작성 완료</button>
          <a href="boardList.do" class="cancel-btn">취소</a>
        </div>
      </form>
    </div>
  </main>

  <!-- 푸터 -->
  <jsp:include page="footer.jsp" />

</body>
</html>

