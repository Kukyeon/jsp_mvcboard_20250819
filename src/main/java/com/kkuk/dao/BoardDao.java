package com.kkuk.dao;

import java.net.Authenticator.RequestorType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kkuk.dto.BoardDto;
import com.kkuk.dto.BoardMemberDto;
import com.kkuk.dto.MemberDto;

public class BoardDao {

	
	
	
	
	private String driverName = "com.mysql.jdbc.Driver"; // MySQL 의 JDBC 드라이버 이름
	private String url = "jdbc:mysql://localhost:3306/jspdb"; //MySQL이 설치된 서버의 주소(IP)와 연결할 DB(스키마) 이름
	private String username = "root"; // DB 로그인한 아이디
	private String password = "12345"; // DB 비밀번호

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public static final int PAGE_SIZE = 10;
	
	
	public List<BoardDto> boardList(int page) { //게시판 모든 글 리스트를 가져와서 반환하는 메서드
		//String sql = "SELECT * FROM board ORDER BY bnum DESC";
		String sql = "SELECT row_number() OVER (order by bnum ASC) AS bno,"
				+ "b.bnum, b.btitle, b.bcontent, b.memberid, m.memberemail, b.bdate, b.bhit "
				+ "FROM board b "
				+ "INNER JOIN members m ON b.memberid = m.memberid"
				+ " ORDER BY bno DESC";
				
		//members 테이블과 board 테이블의 조인 SQL문
		//List<BoardMemberDto> bmDtos = new ArrayList<BoardMemberDto>();
		List<BoardDto> bDtos = new ArrayList<BoardDto>();
		int offset = (page - 1) * PAGE_SIZE;
		try {
			Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
			conn = DriverManager.getConnection(url, username, password);
			//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)			
			
			rs = pstmt.executeQuery(); //모든 글 리스트(모든 레코드) 반환
			
			while(rs.next()) {
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");				
				String memberemail = rs.getString("memberemail");
				
				int bno = rs.getInt("bno");
				
				MemberDto memberDto = new MemberDto();
				memberDto.setMemberid(memberid); 
				memberDto.setMemberemail(memberemail); 
				
				BoardDto bDto = new BoardDto(bno, bnum, btitle, bcontent, memberid, bhit, bdate, memberDto);
				//BoardMemberDto bmDto = new BoardMemberDto(bnum, btitle, bcontent, memberid, memberemail, bhit, bdate);
				bDtos.add(bDto);
				
			}	
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 게시판 목록 가져오기 실패!");
			e.printStackTrace(); //에러 내용 출력
		} finally { //에러의 발생여부와 상관 없이 Connection 닫기 실행 
			try {
				if(rs != null) { //rs가 null 이 아니면 닫기(pstmt 닫기 보다 먼저 실행)
					rs.close();
				}				
				if(pstmt != null) { //stmt가 null 이 아니면 닫기(conn 닫기 보다 먼저 실행)
					pstmt.close();
				}				
				if(conn != null) { //Connection이 null 이 아닐 때만 닫기
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return bDtos; //모든 글(bDto) 여러 개가 담긴 list인 bDtos를 반환
		
	}
		
	public List<BoardDto> boardPage(int page) {
	    int offset = (page - 1) * PAGE_SIZE;
	    String sql = "SELECT row_number() OVER (ORDER BY bnum ASC) AS bno, "
	               + "b.bnum, b.btitle, b.bcontent, b.memberid, m.memberemail, b.bdate, b.bhit "
	               + "FROM board b "
	               + "INNER JOIN members m ON b.memberid = m.memberid "
	               + "ORDER BY bno DESC "
	               + "LIMIT ? OFFSET ?";

	    List<BoardDto> bDtos = new ArrayList<>();

	    try {
	        Class.forName(driverName);
	        conn = DriverManager.getConnection(url, username, password);
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, PAGE_SIZE);
	        pstmt.setInt(2, offset);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int bnum = rs.getInt("bnum");
	            String btitle = rs.getString("btitle");
	            String bcontent = rs.getString("bcontent");
	            String memberid = rs.getString("memberid");
	            int bhit = rs.getInt("bhit");
	            String bdate = rs.getString("bdate");
	            String memberemail = rs.getString("memberemail");
	            int bno = rs.getInt("bno");

	            MemberDto memberDto = new MemberDto();
	            memberDto.setMemberid(memberid);
	            memberDto.setMemberemail(memberemail);

	            BoardDto bDto = new BoardDto(bno, bnum, btitle, bcontent, memberid, bhit, bdate, memberDto);
	            bDtos.add(bDto);
	        }

	    } catch (Exception e) {
	        System.out.println("DB 에러 발생! 페이지별 게시글 목록 가져오기 실패!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return bDtos;
	}
	
	public int countSearchBoard(String keyword, String type) {
	    String sql = "SELECT COUNT(*) FROM board WHERE " + type + " LIKE ?";
	    int count = 0;

	    try {
	        Class.forName(driverName);
	        conn = DriverManager.getConnection(url, username, password);
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, "%" + keyword + "%");
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            count = rs.getInt(1);
	        }

	    } catch (Exception e) {
	        System.out.println("DB 에러 발생! 검색된 게시글 수 가져오기 실패!");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return count;
	}
	
		public int countBoard() { // 게시판 모든글의 갯수를 반환하는 메서드
		String sql = "SELECT COUNT(*) FROM board";
		
		int count = 0;
		
	
		try {
			Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
			conn = DriverManager.getConnection(url, username, password);
			//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)			
			
			rs = pstmt.executeQuery(); //모든 글 리스트(모든 레코드) 반환
			
		
			
			while(rs.next()) {
				 count = rs.getInt(1);		
				//count++;// rs의 레코드 수 만큼 늘어남
				
			}	
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 글 갯수 가져오기 실패!");
			e.printStackTrace(); //에러 내용 출력
		} finally { //에러의 발생여부와 상관 없이 Connection 닫기 실행 
			try {
				if(rs != null) { //rs가 null 이 아니면 닫기(pstmt 닫기 보다 먼저 실행)
					rs.close();
				}				
				if(pstmt != null) { //stmt가 null 이 아니면 닫기(conn 닫기 보다 먼저 실행)
					pstmt.close();
				}				
				if(conn != null) { //Connection이 null 이 아닐 때만 닫기
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return count; //모든 글(bDto) 여러 개가 담긴 list인 bDtos를 반환
		
		
		
	}
	
	public List<BoardDto> searchBoardPage(String searchKeyword, String searchType, int page) { //게시판 모든 글 리스트를 가져와서 반환하는 메서드
		//String sql = "SELECT * FROM board ORDER BY bnum DESC";
		
		String sql = "SELECT row_number() OVER (order by bnum ASC) AS bno, "
				+ "b.bnum, b.btitle, b.bcontent, b.memberid, m.memberemail, b.bdate, b.bhit "
				+ " FROM board b "
				+ " INNER JOIN members m ON b.memberid = m.memberid "
				+ " WHERE " + searchType + " LIKE ? "
				+ " ORDER BY bno DESC "
				+ " LIMIT ? OFFSET ? ";
		//members 테이블과 board 테이블의 조인 SQL문
		//List<BoardMemberDto> bmDtos = new ArrayList<BoardMemberDto>();
		List<BoardDto> bDtos = new ArrayList<BoardDto>();
		int offset = (page - 1) * PAGE_SIZE;
		try {
			Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
			conn = DriverManager.getConnection(url, username, password);
			//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)			
			pstmt.setString(1, "%" + searchKeyword + "%");
			pstmt.setInt(2, PAGE_SIZE);
	        pstmt.setInt(3, offset);
			rs = pstmt.executeQuery(); //모든 글 리스트(모든 레코드) 반환
			
			while(rs.next()) {
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");				
				String memberemail = rs.getString("memberemail");
				
				int bno = rs.getInt("bno");
				
				MemberDto memberDto = new MemberDto();
				memberDto.setMemberid(memberid); 
				memberDto.setMemberemail(memberemail); 
				
				BoardDto bDto = new BoardDto(bno, bnum, btitle, bcontent, memberid, bhit, bdate, memberDto);
				//BoardMemberDto bmDto = new BoardMemberDto(bnum, btitle, bcontent, memberid, memberemail, bhit, bdate);
				bDtos.add(bDto);
				
			}	
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 게시판 목록 가져오기 실패!");
			e.printStackTrace(); //에러 내용 출력
		} finally { //에러의 발생여부와 상관 없이 Connection 닫기 실행 
			try {
				if(rs != null) { //rs가 null 이 아니면 닫기(pstmt 닫기 보다 먼저 실행)
					rs.close();
				}				
				if(pstmt != null) { //stmt가 null 이 아니면 닫기(conn 닫기 보다 먼저 실행)
					pstmt.close();
				}				
				if(conn != null) { //Connection이 null 이 아닐 때만 닫기
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return bDtos; //모든 글(bDto) 여러 개가 담긴 list인 bDtos를 반환
		
	}
	
	
	public void boardWrite(String btitle, String bcontent, String memberid) {
		String sql = "INSERT INTO board(btitle, bcontent, memberid, bhit)VALUES(?,?,?,0)";
		
		
		try{ // 에러 날 가능성이 높기때문에 예외처리 필수 트라이 캣치
			Class.forName(driverName); // MySQL 드라이버 클래스 불러오기
			conn = DriverManager.getConnection(url, username, password);
			//conn 커넥션이 메모리에 생성이됨 (DB와의 연결 커넥션 conn 생성)
			// stmt = conn.createStatement(); // stmt 객체 생성
			
			pstmt = conn.prepareStatement(sql); // pstmt 객체 생성
			
			pstmt.setString(1, btitle);
			pstmt.setString(2, bcontent);
			pstmt.setString(3, memberid);
			
			
			pstmt.executeUpdate(); // 성공시 sqlResult 값이 1로 변환
			
			
		} catch (Exception e) {
			System.out.println("DB 에러 게시판 글쓰기 실패");
			e.printStackTrace(); // 에러 내용 출력
		} finally { // 에러 발생 여부와 상관없이 커넥션 닫아줘야함
			try{
				if(pstmt != null){ // stmt가 null 아니면 닫기 (conn 보다 먼저 실행되어야함)
					pstmt.close();
				}
				if(conn != null){ // 커넥션이 null이 아닐때만 닫기
					conn.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		
		
		
	}
	
	public BoardDto contentView(String boardnum) {
		//String sql = "SELECT * FROM board WHERE bnum=?";
		String sql = "SELECT b.bnum, b.btitle, b.bcontent, b.memberid, b.bhit, b.bdate,"
				+ " m.memberemail FROM board b INNER JOIN members m ON b.memberid=m.memberid "
				+ "WHERE b.bnum = ? ";
		BoardDto boardDto = null;
		
		try{ // 에러 날 가능성이 높기때문에 예외처리 필수 트라이 캣치
			Class.forName(driverName); // MySQL 드라이버 클래스 불러오기
			conn = DriverManager.getConnection(url, username, password);
			//conn 커넥션이 메모리에 생성이됨 (DB와의 연결 커넥션 conn 생성)
			// stmt = conn.createStatement(); // stmt 객체 생성
			
			pstmt = conn.prepareStatement(sql); // pstmt 객체 생성
			pstmt.setString(1, boardnum);
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) { 
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");
			
				String memberemail = rs.getString("memberemail");
				
				MemberDto memberDto = new MemberDto();
				memberDto.setMemberemail(memberemail);
				
				boardDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate);
				boardDto.setMember(memberDto);
			}	
			
			
		} catch (Exception e) {
			System.out.println("DB 에러 contentvi 체크 실패");
			e.printStackTrace(); // 에러 내용 출력
		} finally { // 에러 발생 여부와 상관없이 커넥션 닫아줘야함
			try{
				if(rs != null){ // rs가 null 아니면 닫기 (spstmt 보다 먼저 실행되어야함)
					rs.close();
				}
				if(pstmt != null){ // pstmt가 null 아니면 닫기 (conn 보다 먼저 실행되어야함)
					pstmt.close();
				}
				if(conn != null){ // 커넥션이 null이 아닐때만 닫기
					conn.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return boardDto; 
	}
	
		public void boardUpdate(String bnum, String btitle, String bcontent) {
			String sql = "UPDATE board SET btitle = ?, bcontent = ? WHERE bnum = ?";
			
			try{ // 에러 날 가능성이 높기때문에 예외처리 필수 트라이 캣치
				Class.forName(driverName); // MySQL 드라이버 클래스 불러오기
				conn = DriverManager.getConnection(url, username, password);
				//conn 커넥션이 메모리에 생성이됨 (DB와의 연결 커넥션 conn 생성)
				// stmt = conn.createStatement(); // stmt 객체 생성
				
				pstmt = conn.prepareStatement(sql); // pstmt 객체 생성
				
				pstmt.setString(1, btitle);
				pstmt.setString(2, bcontent);
				pstmt.setString(3, bnum);
				
				
				pstmt.executeUpdate(); // 성공시 sqlResult 값이 1로 변환
				
				
			} catch (Exception e) {
				System.out.println("DB 에러 게시판 글쓰기 실패");
				e.printStackTrace(); // 에러 내용 출력
			} finally { // 에러 발생 여부와 상관없이 커넥션 닫아줘야함
				try{
					if(pstmt != null){ // stmt가 null 아니면 닫기 (conn 보다 먼저 실행되어야함)
						pstmt.close();
					}
					if(conn != null){ // 커넥션이 null이 아닐때만 닫기
						conn.close();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			
		}
		public void boardDelete(String bnum) {
			String sql = "DELETE FROM board WHERE bnum = ?";
			
			
			
			try{ // 에러 날 가능성이 높기때문에 예외처리 필수 트라이 캣치
				Class.forName(driverName); // MySQL 드라이버 클래스 불러오기
				conn = DriverManager.getConnection(url, username, password);
				//conn 커넥션이 메모리에 생성이됨 (DB와의 연결 커넥션 conn 생성)
				// stmt = conn.createStatement(); // stmt 객체 생성
				
				pstmt = conn.prepareStatement(sql); // pstmt 객체 생성
				
				pstmt.setString(1, bnum);
				
				pstmt.executeUpdate(); // 성공시 sqlResult 값이 1로 변환
				
				
			} catch (Exception e) {
				System.out.println("DB 에러 삭제 실패");
				e.printStackTrace(); // 에러 내용 출력
			} finally { // 에러 발생 여부와 상관없이 커넥션 닫아줘야함
				try{
					if(pstmt != null){ // stmt가 null 아니면 닫기 (conn 보다 먼저 실행되어야함)
						pstmt.close();
					}
					if(conn != null){ // 커넥션이 null이 아닐때만 닫기
						conn.close();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		
		}
		
		public void updateBhit(String bnum) {
			String sql ="UPDATE board SET bhit=bhit+1 WHERE bnum=?"; // 조회수가 1 씩 늘어나는 sql 문
			

			try{ // 에러 날 가능성이 높기때문에 예외처리 필수 트라이 캣치
				Class.forName(driverName); // MySQL 드라이버 클래스 불러오기
				conn = DriverManager.getConnection(url, username, password);
				//conn 커넥션이 메모리에 생성이됨 (DB와의 연결 커넥션 conn 생성)
				// stmt = conn.createStatement(); // stmt 객체 생성
				pstmt = conn.prepareStatement(sql); // pstmt 객체 생성
				
				pstmt.setString(1, bnum);
				
				pstmt.executeUpdate(); // 성공시 sqlResult 값이 1로 변환
				
				
			} catch (Exception e) {
				System.out.println("DB 에러 조회수 수정 실패");
				e.printStackTrace(); // 에러 내용 출력
			} finally { // 에러 발생 여부와 상관없이 커넥션 닫아줘야함
				try{
					if(pstmt != null){ // stmt가 null 아니면 닫기 (conn 보다 먼저 실행되어야함)
						pstmt.close();
					}
					if(conn != null){ // 커넥션이 null이 아닐때만 닫기
						conn.close();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}
}
