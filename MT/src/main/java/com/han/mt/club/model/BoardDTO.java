package com.han.mt.club.model;

import java.sql.Date;

public class BoardDTO {

	private int socialNum;
	private int boardNum;
	private int viewed;
	private String title;
	private String writer;
	private String nickName;
	private String fileName;
	private String contents;
	private String category;
	private Date writeDate;
	private Date rewriteDate;
	
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getSocialNum() {
		return socialNum;
	}
	public void setSocialNum(int socialNum) {
		this.socialNum = socialNum;
	}
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public int getViewed() {
		return viewed;
	}
	public void setViewed(int viewed) {
		this.viewed = viewed;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public Date getRewriteDate() {
		return rewriteDate;
	}
	public void setRewriteDate(Date rewriteDate) {
		this.rewriteDate = rewriteDate;
	}
	@Override
	public String toString() {
		return "BoardDTO [socialNum=" + socialNum + ", boardNum=" + boardNum + ", viewed=" + viewed + ", title=" + title
				+ ", writer=" + writer + ", nickName=" + nickName + ", fileName=" + fileName + ", contents=" + contents
				+ ", category=" + category + ", writeDate=" + writeDate + ", rewriteDate=" + rewriteDate + "]"+"\n";
	}
	
	
	
}
