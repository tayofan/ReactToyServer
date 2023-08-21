package kr.co.ocean.board.dto;


public class BoardVO {
	
	private String code;
	private String title;
	private String regDt;
	private String detail;
	private String delDt;
	private String updtDt;
	private String author;
	private int no;
	private String test;
	
	public void setCode(String code) {
		this.code = code;
	}

	public BoardVO() {
		// TODO Auto-generated constructor stub
	}

	public BoardVO(String title, String detail) {
		this.test = title + "$$" + detail;
	}

	
	public String getCode() {
		return code;
	}



	public String getTitle() {
		return title;
	}



	public String getRegDt() {
		return regDt;
	}



	public String getDetail() {
		return detail;
	}



	public String getDelDt() {
		return delDt;
	}



	public String getUpdtDt() {
		return updtDt;
	}



	public String getAuthor() {
		return author;
	}



	@Override
	public String toString() {
		return "BoardVO [code=" + code + ", title=" + title + ", regDt=" + regDt + ", detail=" + detail + ", delDt="
				+ delDt + ", updtDt=" + updtDt + ", author=" + author + ", no=" + no + ", test=" + test + "]";
	}

	public int getNo() {
		return no;
	}

	
	
	
}
