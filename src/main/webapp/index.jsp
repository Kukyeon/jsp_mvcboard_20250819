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

  <main class="main-content">
    <section class="intro-section">
      <h1>게시판만 이용가능</h1>
      <p>
        저희 홈페이지에 오신 것을 환영합니다! 다양한 정보와 게시판을 통해 소통하세요.
      </p>
      <a href="boardList.do" class="btn-primary">게시판 바로가기</a>
    </section>

    <section class="feature-section">
      <h2>주요 기능</h2>
      <div class="features">
        <div class="feature-item">
          <h3><a href="boardList.do">게시판</a></h3>
          <p>손쉽게 글을 쓰고 읽을 수 있습니다.</p>
        </div>
        <div class="feature-item">
          <h3>공지사항</h3>
          <p>중요한 소식을 빠르게 확인하세요.</p>
        </div>
        <div class="feature-item">
          <h3>고객 지원</h3>
          <p>문의사항을 남기시면 빠르게 답변해드립니다.</p>
        </div>
        <div class="feature-item">
          <h3>도움말</h3>
          <p>서비스 이용에 필요한 도움말을 제공합니다.</p>
        </div>
      </div>
    </section>
  </main>

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
</body>
</html>
