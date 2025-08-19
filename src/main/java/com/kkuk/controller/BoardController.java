package com.kkuk.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kkuk.dao.BoardDao;
import com.kkuk.dto.BoardDto;


@WebServlet("*.do")
public class BoardController extends HttpServlet {

       

	
   
    public BoardController() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String uri = request.getRequestURI();
		//System.out.println("uri = "+uri);
		
		String conPath = request.getContextPath();
		//System.out.println("con = "+conPath);
		
		String comm = uri.substring(conPath.length());
		System.out.println("comm = "+comm);
		
		String viewPage = ""; // 실제 클라이언트에게 전송 될 jsp파일의 이름이 저장될 변수
		HttpSession session = null;
		
		BoardDao boardDao = new BoardDao();
		List<BoardDto> bDtos = new ArrayList<BoardDto>();
		
		if(comm.equals("/boardList.do")) { // 게시판 모든 글 목록 보기 요청
			bDtos = boardDao.boardList(); // 게시판에 모든 글이 포함된 arraylist 가 반환
			request.setAttribute("bDtos", bDtos);
			viewPage = "boardList.jsp";
		}else if(comm.equals("/write.do")) { // 글쓰기
			viewPage = "write.jsp";	
		}else if(comm.equals("/modify.do")) { // 글 수정
			viewPage = "modifyForm.jsp";	
		}else if(comm.equals("/delete.do")) { // 글삭제 후 글목록으로
			viewPage = "boardList.do";
		}else if(comm.equals("/content.do")) { // 게시판에서 게시글 페이지로 이동
			String bnum = request.getParameter("bnum");
			BoardDto bDto = boardDao.contentView(bnum);
			request.setAttribute("bDto", bDto);
			viewPage = "contentView.jsp";
		}else if(comm.equals("/writeOk.do")) {
			request.setCharacterEncoding("utf-8");
			String btitle = request.getParameter("title"); //  유저가 입력한 글 제목
			String bcontent = request.getParameter("content");
			String memberid = request.getParameter("author"); // 작성자
			boardDao.boardWrite(btitle, bcontent, memberid);
			viewPage = "boardList.do";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		// viewPage 에게 request 객체를 전달하고 해당하는 viewPage 로 이동해라
		
	}
}
