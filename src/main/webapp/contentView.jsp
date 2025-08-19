<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>게시글 상세보기</title>
  <link rel="stylesheet" href="css/detail_style.css" />
</head>
<body>
  <div class="detail-container">
    <h2 class="detail-title">게시글 제목 예시</h2>

    <div class="detail-info">
      <span>작성자: <strong>관리자</strong></span>
      <span>조회수: <strong>45</strong></span>
      <span>작성일: <strong>2025-08-19</strong></span>
    </div>

    <div class="detail-content">
      <p>
        여기에 게시글 내용이 들어갑니다. 여러 줄의 텍스트도 문제 없이 표시됩니다.
      </p>
    </div>

    <div class="detail-buttons">
      <a href="boardList.jsp" class="btn btn-list">목록으로</a>
      <a href="modifyForm.jsp?postId=123" class="btn btn-edit">수정하기</a>
      <form action="deletePost.jsp" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');" style="display:inline;">
        <input type="hidden" name="postId" value="123" />
        <button type="submit" class="btn btn-delete">삭제하기</button>
      </form>
    </div>
  </div>
</body>
</html>

