package com.kkuk.command;

import com.kkuk.dao.BoardDao;
import com.kkuk.dto.BoardDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BModifyCommand {

	public void excute(HttpServletRequest request, HttpServletResponse response) {
		BoardDao boardDao = new BoardDao();
		
		String bnum = request.getParameter("bnum");
		String btitle = request.getParameter("title"); //  유저가 수정한 글 제목
		String bcontent = request.getParameter("content"); // 수정하려는 글 내용
		String memberid = request.getParameter("author"); // 수정 작성자
		
		boardDao.boardUpdate(bnum, btitle, bcontent);
		BoardDto bDto = boardDao.contentView(bnum); // 수정한 글 번호로 수정한 글 다시 가져오기
		request.setAttribute("bDto", bDto);
		
		
	}
	
}
