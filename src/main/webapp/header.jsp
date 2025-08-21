<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<header class="header">
  <div class="header-container">
    <div class="logo">미완성 페이지</div>
    <nav class="nav-menu">
      <ul>
        <li><a href="index.do">HOME</a></li>
        <li><a href="boardList.do">게시판</a></li>
        <li><a href="contact.html">고객센터</a></li>
        
        <c:if test="${empty sessionScope.sessionId}">
        <li><a href="login.do">로그인</a></li>
        </c:if>
        <c:if test="${not empty sessionScope.sessionId }">
        <li><a href="logout.do">로그아웃</a></li>
        </c:if>
      </ul>
    </nav>
  </div>
</header>
