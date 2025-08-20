package com.kkuk.dto;

public class BoardMemberDto {

	private int bnum; // 글 순서
	private String btitle; // 글 제목
	private String bcontent; // 글 내용
	private String memberid; // 글쓴이
	private String memberemail;
	private int bhit; // 조회수
	private String bdate; // 글쓴날자
	public BoardMemberDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardMemberDto(int bnum, String btitle, String bcontent, String memberid, String memberemail, int bhit,
			String bdate) {
		super();
		this.bnum = bnum;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.memberid = memberid;
		this.memberemail = memberemail;
		this.bhit = bhit;
		this.bdate = bdate;
	}
	public int getBnum() {
		return bnum;
	}
	public void setBnum(int bnum) {
		this.bnum = bnum;
	}
	public String getBtitle() {
		return btitle;
	}
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}
	public String getBcontent() {
		return bcontent;
	}
	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getMemberemail() {
		return memberemail;
	}
	public void setMemberemail(String memberemail) {
		this.memberemail = memberemail;
	}
	public int getBhit() {
		return bhit;
	}
	public void setBhit(int bhit) {
		this.bhit = bhit;
	}
	public String getBdate() {
		return bdate;
	}
	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
			
			
	
	
}
