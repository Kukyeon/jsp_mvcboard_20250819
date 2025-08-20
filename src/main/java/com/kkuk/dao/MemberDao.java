package com.kkuk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.kkuk.dto.BoardDto;

public class MemberDao {

	private String driverName = "com.mysql.jdbc.Driver"; // MySQL 의 JDBC 드라이버 이름
	private String url = "jdbc:mysql://localhost:3306/jspdb"; //MySQL이 설치된 서버의 주소(IP)와 연결할 DB(스키마) 이름
	private String username = "root"; // DB 로그인한 아이디
	private String password = "12345"; // DB 비밀번호

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	
	public int loginCheck(String mid, String mpw) {
		String sql = "SELECT * FROM members WHERE memberid = ? AND memberpw = ?";
		
		int sqlResult = 0;
		
		try{ // 에러 날 가능성이 높기때문에 예외처리 필수 트라이 캣치
			Class.forName(driverName); // MySQL 드라이버 클래스 불러오기
			conn = DriverManager.getConnection(url, username, password);
			//conn 커넥션이 메모리에 생성이됨 (DB와의 연결 커넥션 conn 생성)
			// stmt = conn.createStatement(); // stmt 객체 생성
			
			pstmt = conn.prepareStatement(sql); // pstmt 객체 생성
			pstmt.setString(1, mid);
			pstmt.setString(2, mpw);
			
			rs = pstmt.executeQuery(); //  아이디와 비밀번호가 일치하는 레코드1개 또는 0개가 반환
			
			
			if(rs.next()) {	//참이면 로그인성공
				sqlResult = 1;
			}else { // 거짓이면 로그인실패
				sqlResult = 0;
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
		return sqlResult;  // 로그인 성공 = 1 , 로그인실패 = 0 
	}
	
	
}
