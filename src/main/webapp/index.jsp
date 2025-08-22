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
      <h1>그냥저냥 어두컴컴한 홈페이지</h1>
      <p>
        저희 홈페이지에 오신 것을 환영합니다! 이용 전 공지사항을 확인해주세세요.
      </p>
      <a href="boardList.do" class="btn-primary">공지사항 바로가기</a>
    </section>
    <br><br><br><br><br><br>
    <section class="intro-section">
      <h3>원하시는 기능이 있으시다면 게시판에 요청해주세요</h3>
      
      <a href="boardList.do" class="btn-primary">게시판 바로가기</a>
    </section>
    

    
  </main>

 <jsp:include page="footer.jsp" />
</body>
</html>
