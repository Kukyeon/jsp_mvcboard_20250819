<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>메인 페이지</title>
  <link rel="stylesheet" href="css/index_style.css" />
</head>
<body>
<jsp:include page="header.jsp" />

  <main class="main-content">
    <section class="intro-section">
      <h1>게시판만 이용가능</h1>
      <p>
        저희 홈페이지에 오신 것을 환영합니다! 다양한 정보와 게시판을 통해 소통하세요.
      </p>
      <a href="boardList.do" class="btn-primary">게시판 바로가기</a>
    </section>

    
  </main>

 <jsp:include page="footer.jsp" />
</body>
</html>
