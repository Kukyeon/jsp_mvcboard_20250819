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
<jsp:include page="header.jsp" />

  <!-- 로그인 폼 -->
  <main class="login-container">
    <h2>로그인</h2>

    <form action="loginOk.do" method="post" class="login-form">
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
<jsp:include page="footer.jsp" />

</body>
</html>
