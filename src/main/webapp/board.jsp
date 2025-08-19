<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List" %>
<%@ page import="com.kkuk.dao.BoardDao" %>
<%@ page import="com.kkuk.dto.BoardDto" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시판 목록</title>
    <link rel="stylesheet" href="css/boardStyle.css"> <!-- 따로 디자인한 CSS 파일 -->
</head>
<body>
<main class="board-container">
    <h1>게시판 목록</h1>

    <table class="board-table">
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>내용</th>
                <th>작성자</th>
                <th>조회수</th>
                <th>날짜</th>
            </tr>
        </thead>
        <tbody>
            <%
                BoardDao dao = new BoardDao();
                List<BoardDto> list = dao.boardList();
                for(BoardDto dto : list){
            %>
            <tr>
                <td><%= dto.getBnum() %></td>
                <td><a href="view.jsp?bnum=<%= dto.getBnum() %>"><%= dto.getBtitle() %></a></td>
                <td><%= dto.getBcontent() %></td>
                <td><%= dto.getMemberid() %></td>
                <td><%= dto.getBhit() %></td>
                <td><%= dto.getBdate() %></td>
            </tr>
            <% } %>
        </tbody>
    </table>

    <div class="board-buttons">
    <br>
        <a href="write.jsp" class="btn-write">글쓰기</a>
        <a href="index.jsp" class="btn-main">메인화면</a>
    </div>
</main>
</body>
</html>