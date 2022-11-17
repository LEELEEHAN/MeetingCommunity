package com.han.mt.notice.model;

import java.sql.Date;

public class NoticeBoardDTO {
 
	private int noticeNum;
	private String title; 
	private String content;
	private String category;
	private String fileName;
	private Date writeDate;
	private int viewed;
	
	
	
	
	public int getNoticeNum() {
		return noticeNum;
	}
	public void setNoticeNum(int noticeNum) {
		this.noticeNum = noticeNum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public int getViewed() {
		return viewed;
	}
	public void setViewed(int viewed) {
		this.viewed = viewed;
	}
	
	@Override
	public String toString() {
		return "NoticeBoardDTO [noticeNum=" + noticeNum + ", title=" + title + ", content=" + content + ", category="
				+ category + ", fileName=" + fileName + ", writeDate=" + writeDate + ", viewed=" + viewed + "]"+"\n";
	}
	
	
	
}
