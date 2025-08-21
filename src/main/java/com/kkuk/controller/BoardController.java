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
import com.kkuk.dao.MemberDao;
import com.kkuk.dto.BoardDto;
import com.kkuk.dto.BoardMemberDto;


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
		
		String viewPage = ""; // 실제 클라이언트에게 전송 될 jsp파일의 이름이 저장될 변thu
		HttpSession session = null;
		BoardDao boardDao = new BoardDao();
		List<BoardMemberDto> bmDtos = new ArrayList<BoardMemberDto>(); 
		List<BoardDto> bDtos = new ArrayList<BoardDto>();
		MemberDao memberDao = new MemberDao();
		
		
		
		if(comm.equals("/boardList.do")) { // 게시판 모든 글 목록 보기 요청
			String searchType = request.getParameter("searchType");
			String searchKeyword = request.getParameter("searchKeyword");
			if(searchType != null && searchKeyword != null && !searchKeyword.strip().isEmpty()) {// 유저가 검색 결과 리스트를 원하는 경우
				bDtos = boardDao.searchBoardList(searchKeyword, searchType);
			}else { // 모든 게시판 글 리스트를 원하는 경우
				bDtos = boardDao.boardList(); // 게시판에 모든 글이 포함된 arraylist 가 반환
			}
			
			System.out.println("searchType = " + searchType);
			System.out.println("searchKeyword = " + searchKeyword);
			
			
			// bDtos = boardDao.boardList(); // 게시판에 모든 글이 포함된 arraylist 가 반환
			request.setAttribute("bDtos", bDtos);
			viewPage = "boardList.jsp";
		}else if(comm.equals("/write.do")) { // 글쓰기
			session = request.getSession();
			String sid = (String)session.getAttribute("sessionId");
			if(sid != null) {
				viewPage = "write.jsp";
			}else {
				response.sendRedirect("login.do?msg=2");
				return;
			}
			
		}else if(comm.equals("/modify.do")) { // 글 수정
			session = request.getSession();
			String sid = (String)session.getAttribute("sessionId");
			String bnum = request.getParameter("bnum"); // 수정하려고하는 글의 번호
			BoardDto bDto = boardDao.contentView(bnum); // 수정하려고 하는 글의 레코드 가져오기
			
			if(bDto.getMemberid().equals(sid)) {
				request.setAttribute("bDto", bDto); // 싣고 보내주기
				viewPage = "modifyForm.jsp";
			}else {
				response.sendRedirect("modifyForm.jsp?error=1");
				return;
			}
			
			
		}else if(comm.equals("/modifyOk.do")) { // 글 수정후 글 내용보기로 이동
			
			request.setCharacterEncoding("utf-8");
			
			String bnum = request.getParameter("bnum");
			String btitle = request.getParameter("title"); //  유저가 수정한 글 제목
			String bcontent = request.getParameter("content"); // 수정하려는 글 내용
			String memberid = request.getParameter("author"); // 수정 작성자
			
			boardDao.boardUpdate(bnum, btitle, bcontent);
			BoardDto bDto = boardDao.contentView(bnum); // 수정한 글 번호로 수정한 글 다시 가져오기
			request.setAttribute("bDto", bDto);
			viewPage = "contentView.jsp";
			
		}else if(comm.equals("/delete.do")) { // 글삭제 후 글목록으로
			String bnum = request.getParameter("bnum"); //유저가 삭제할 글의 번호
			session = request.getSession();
			String sid = (String) session.getAttribute("sessionId");
			
			BoardDto boardDto = boardDao.contentView(bnum); //수정하려고 하는 글 가져오기
			
			if(boardDto.getMemberid().equals(sid)) { //참이면 수정, 삭제 가능
				boardDao.boardDelete(bnum); //해당 글 번호 삭제 메서드 호출				
				viewPage = "boardList.do";
			} else {
				response.sendRedirect("modifyForm.jsp?error=1");
				return;
			}
			viewPage = "boardList.do";
		}else if(comm.equals("/content.do")) { // 게시판에서 게시글 페이지로 이동
			String bnum = request.getParameter("bnum");
			
			//조회수 올려주는 메서드를 호출
			boardDao.updateBhit(bnum); // 조회수 증가
			
			BoardDto bDto = boardDao.contentView(bnum);
			if(bDto == null) { // 선택한 글이 db에 존재하지 않는경우,( 클릭과 동시에 삭제 된 경우)
				request.setAttribute("msg", "존재하지 않는 글입니다.");
				//response.sendRedirect("contentView.jsp?msg=해당글은존재하지않습니다.");;
				//return;
			}
			request.setAttribute("bDto", bDto);
			viewPage = "contentView.jsp";
		}else if(comm.equals("/writeOk.do")) {
			request.setCharacterEncoding("utf-8");
			String btitle = request.getParameter("title"); //  유저가 입력한 글 제목
			String bcontent = request.getParameter("content");
			String memberid = request.getParameter("author"); // 작성자
			boardDao.boardWrite(btitle, bcontent, memberid);
			response.sendRedirect("boardList.do"); // 포워딩을 하지않고 강제로 이동
			return; // 프로그램의 진행 멈춤
		}else if(comm.equals("/login.do")) {
			viewPage = "login.jsp";
		}else if(comm.equals("/loginOk.do")) {
			request.setCharacterEncoding("utf-8");
			String loginId = request.getParameter("memberid");
			String loginPw = request.getParameter("password");
			
			int loginFlag = memberDao.loginCheck(loginId, loginPw); // 성공이면 1, 실패면 0
			if(loginFlag == 1) {
				session = request.getSession();
				session.setAttribute("sessionId", loginId);
			}else {
				response.sendRedirect("login.do?msg=1");
				return;
			}
			
			viewPage = "boardList.do";
		}	else {
		viewPage = "index.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		// viewPage 에게 request 객체를 전달하고 해당하는 viewPage 로 이동해라
		
	}
}
