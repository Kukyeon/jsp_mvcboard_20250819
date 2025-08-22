package com.kkuk.command;

import com.kkuk.dao.BoardDao;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BWriteCommand {
	
	public void excute(HttpServletRequest request, HttpServletResponse response) {
		BoardDao boardDao = new BoardDao();
		
		String btitle = request.getParameter("title"); //  유저가 입력한 글 제목
		String bcontent = request.getParameter("content");
		String memberid = request.getParameter("author"); // 작성자
		
		boardDao.boardWrite(btitle, bcontent, memberid);
		
		
	}
}
