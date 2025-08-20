package com.kkuk.dao;

import java.net.Authenticator.RequestorType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kkuk.dto.BoardDto;

public class BoardDao {

	
	
	private String driverName = "com.mysql.jdbc.Driver"; // MySQL 의 JDBC 드라이버 이름
	private String url = "jdbc:mysql://localhost:3306/jspdb"; //MySQL이 설치된 서버의 주소(IP)와 연결할 DB(스키마) 이름
	private String username = "root"; // DB 로그인한 아이디
	private String password = "12345"; // DB 비밀번호

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	
	
	public List<BoardDto> boardList() { // 게시판의 모든 글 리스트 가져와서 반환 하기
		
		String sql = "SELECT * FROM board ORDER BY bnum DESC";
		List<BoardDto> bDtos = new ArrayList<BoardDto>();
		
		int sqlResult = 0;
		
		try{ // 에러 날 가능성이 높기때문에 예외처리 필수 트라이 캣치
			Class.forName(driverName); // MySQL 드라이버 클래스 불러오기
			conn = DriverManager.getConnection(url, username, password);
			//conn 커넥션이 메모리에 생성이됨 (DB와의 연결 커넥션 conn 생성)
			// stmt = conn.createStatement(); // stmt 객체 생성
			
			pstmt = conn.prepareStatement(sql); // pstmt 객체 생성(sql 삽입)
			
			rs = pstmt.executeQuery(); // 모든 글 리스트(레코드)
			
			while(rs.next()) {
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");
				
				BoardDto bDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate);
				bDtos.add(bDto);
			}
			
		} catch (Exception e) {
			System.out.println("DB 에러 게시판 목록 가져오기 실패");
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
		return bDtos; // 글(bdto)가 여러개 담긴
		
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
		String sql = "SELECT * FROM board WHERE bnum = ? ";
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
			
				boardDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate);
			}	
			
			
		} catch (Exception e) {
			System.out.println("DB 에러 아이디 존재 여부 체크 실패");
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
	
}
