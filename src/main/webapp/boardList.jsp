<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>게시판 목록</title>
  <link rel="stylesheet" href="css/boardStyle.css" />
</head>
<body>
  <div class="board-container">

    <!-- 메인 화면 가기 버튼 -->
    <div class="top-nav">
      <a href="index.jsp" class="btn-main">메인 화면으로</a>
    </div>

    <h2 class="board-title">게시판</h2>

    <table class="board-table">
      <thead>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>내용</th>
          <th>작성자</th>
          <th>조회수</th>
          <th>날짜</th>
          <th>수정</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>1</td>
          <td><a href="contentView.jsp?postId=1" class="post-link">첫 번째 게시글 제목</a></td>
          <td>게시글 내용 일부 예시입니다.</td>
          <td>관리자</td>
          <td>45</td>
          <td>2025-08-19</td>
          <td><a href="modifyForm.jsp?postId=1" class="edit-btn">수정</a></td>
        </tr>
        <tr>
          <td>2</td>
          <td><a href="contentView.jsp?postId=2" class="post-link">두 번째 게시글 제목</a></td>
          <td>내용이 길어도 적절히 잘려서 표시됩니다.</td>
          <td>사용자1</td>
          <td>32</td>
          <td>2025-08-18</td>
          <td><a href="modifyForm.jsp?postId=2" class="edit-btn">수정</a></td>
        </tr>
        <!-- 게시글이 더 있으면 반복 출력 -->
      </tbody>
    </table>

    <div class="button-area"><br>
      <a href="write.jsp" class="write-btn">글쓰기</a>
    </div>
  </div>
</body>
</html>
