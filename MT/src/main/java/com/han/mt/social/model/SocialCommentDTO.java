package com.han.mt.social.model;

import java.sql.Date;

public class SocialCommentDTO {

	private int commentNum;
	private int socialNum;
	private String nickName;
	private String writer;
	private String content;
	private Date writeDate;
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public int getSocialNum() {
		return socialNum;
	}
	public void setSocialNum(int socialNum) {
		this.socialNum = socialNum;
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
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	
	@Override
	public String toString() {
		return "SocialCommentDTO [commentNum=" + commentNum + ", socialNum=" + socialNum + ", nickName=" + nickName
				+ ", writer=" + writer + ", content=" + content + ", writeDate=" + writeDate + "]"+"\n";
	}
	
	
}
