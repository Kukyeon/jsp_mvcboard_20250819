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

import com.kkuk.command.BModifyCommand;
import com.kkuk.command.BWriteCommand;
import com.kkuk.dao.BoardDao;
import com.kkuk.dao.MemberDao;
import com.kkuk.dto.BoardDto;
import com.kkuk.dto.BoardMemberDto;


@WebServlet("*.do")
public class BoardController extends HttpServlet {
	private static final int PAGE_GROUP_SIZE = 10;
       

	
   
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
			int totalBoardCount = 0;
			int page = 1;
			// 게시판에 페이지 번호 없이 게시판 링크로 접근한 경우 무조건 1페이지의 내용이 출력되어야함
						// 처음에 보여질 페이지의 번호의 초기값을 1로 초기화
			if(request.getParameter("page") == null){ // 참이면 링크타고 게시판으로 들어온경우
				page=1;
			}else { // 유저가 보고싶은 페이지 번호를 누른경우
				page = Integer.parseInt(request.getParameter("page"));
				//유저가 클릭한 보고싶어하는 페이지의 번호
			}
			
						
			//int totalBoardCount = boardDao.countBoard(); // 총 글의 갯수
			
			if(searchType != null && searchKeyword != null && !searchKeyword.strip().isEmpty()) {// 유저가 검색 결과 리스트를 원하는 경우
				bDtos = boardDao.searchBoardList(searchKeyword, searchType, 1);
				if(!bDtos.isEmpty()) {
					totalBoardCount = bDtos.get(0).getBno();
				}
				bDtos = boardDao.searchBoardList(searchKeyword, searchType, page);
				
				request.setAttribute("searchType", searchType);
				request.setAttribute("searchKeyword", searchKeyword);
			}else { // 모든 게시판 글 리스트를 원하는 경우
				bDtos = boardDao.boardList(1); // 게시판에 모든 글이 포함된 arraylist 가 반환
				if(!bDtos.isEmpty()) {
					totalBoardCount = bDtos.get(0).getBno();
				}	
				bDtos = boardDao.boardList(page); //게시판 모든 글이 포함된 ArrayList 반환
			}
			
			int totalPage = (int)Math.ceil((double)totalBoardCount / boardDao.PAGE_SIZE);
			int startPage = (((page - 1) / PAGE_GROUP_SIZE) * PAGE_GROUP_SIZE) + 1; 
			int endPage = startPage + (PAGE_GROUP_SIZE - 1);
			if(endPage > totalPage) {
				endPage = totalPage;
				//totalPage 값을 실제 마지막 페이지로
			}
			
			System.out.println("searchType = " + searchType);
			System.out.println("searchKeyword = " + searchKeyword);
			
			
			// bDtos = boardDao.boardList(); // 게시판에 모든 글이 포함된 arraylist 가 반환
			
			
			request.setAttribute("bDtos", bDtos); // 유저가 선택한 페이지에 해당하는 글
			request.setAttribute("currentPage", page); // 유저가 현재 선택한 페이지 번호
			request.setAttribute("totalPage", totalPage); // 전체 페이지 수
			//총 글의 갯수로 표현될 전체 페이지의 수 (47개면 5개 전달)
			request.setAttribute("startPage", startPage); // 페이지 그룹 출력시 첫번째 페이지 번호
			request.setAttribute("endPage", endPage); // 페이지 그룹 출력시 마지막 페이지 번호
		    request.setAttribute("searchType", searchType);        // 검색 유지용
		    request.setAttribute("searchKeyword", searchKeyword);  // 검색 유지용
			
		    
			
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
			
//			String bnum = request.getParameter("bnum");
//			String btitle = request.getParameter("title"); //  유저가 수정한 글 제목
//			String bcontent = request.getParameter("content"); // 수정하려는 글 내용
//			String memberid = request.getParameter("author"); // 수정 작성자
//			
//			boardDao.boardUpdate(bnum, btitle, bcontent);
//			BoardDto bDto = boardDao.contentView(bnum); // 수정한 글 번호로 수정한 글 다시 가져오기
//			request.setAttribute("bDto", bDto);
			
			// 커맨드에서 불러와서 줄여주기
			BModifyCommand bModifyCommand = new BModifyCommand();
			bModifyCommand.excute(request, response);
			
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
//			request.setCharacterEncoding("utf-8");
//			String btitle = request.getParameter("title"); //  유저가 입력한 글 제목
//			String bcontent = request.getParameter("content");
//			String memberid = request.getParameter("author"); // 작성자
//			boardDao.boardWrite(btitle, bcontent, memberid);
			
			// 커맨드에서 호출
			BWriteCommand bWriteCommand = new BWriteCommand();
			bWriteCommand.excute(request, response);
			
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
				response.sendRedirect("boardList.do?message=login");
				return;
			}else {
				response.sendRedirect("login.do?msg=1");
				return;
			}
			
			// viewPage = "boardList.do";
			// 위에 로그인 성공,실패시 넘어가는 페이지가 있어서 굳이..? 필요없을듯
		}else if(comm.equals("/logout.do")) {
			 session = request.getSession(false);
			    if (session != null) {
			        session.invalidate(); // ✅ 세션 무효화 (로그아웃)
			    }

			    response.sendRedirect("index.do?message=logout"); // ✅ 메인 페이지로 이동
			    return;
			
			
		}else if(comm.equals("/index.do")) {
			viewPage = "index.jsp";
			    }	else {
		viewPage = "index.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		// viewPage 에게 request 객체를 전달하고 해당하는 viewPage 로 이동해라
		
	}
}
