<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>로그인</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="css/loginStyle.css">
</head>
<body>

  <!-- 헤더 -->
  <header class="header">
    <div class="header-container">
      <div class="logo">미완성 페이지</div>
      <nav class="nav-menu">
        <ul>
          <li><a href="index.do">HOME</a></li>
          <li><a href="boardList.do">게시판</a></li>
          <li><a href="contact.html">고객센터</a></li>
          <li><a href="login.do">로그인</a></li>
        </ul>
      </nav>
    </div>
  </header>

  <!-- 로그인 폼 -->
  <main class="login-container">
    <h2>로그인</h2>

    <form action="login.do" method="post" class="login-form">
      <label for="memberid">아이디</label>
      <input type="text" id="memberid" name="memberid" required>

      <label for="password">비밀번호</label>
      <input type="password" id="password" name="password" required>

      <button type="submit">로그인</button>
    </form>

    <div class="login-links">
      <a href="register.jsp">회원가입</a> | 
      <a href="forgotPassword.jsp">비밀번호 찾기</a>
    </div>
  </main>

  <!-- 푸터 -->
  <footer class="footer">
    <div class="footer-container">
      <div class="footer-info">
        <p>© 2025 MySite. All Rights Reserved.</p>
        <p>서울특별시 강남구 테헤란로 123 | 02-1234-5678 | contact@mysite.com</p>
      </div>
      <div class="footer-links">
        <a href="privacy.html">개인정보처리방침</a> | 
        <a href="terms.html">이용약관</a> | 
        <a href="sitemap.html">사이트맵</a>
      </div>
    </div>
  </footer>

</body>
</html>
