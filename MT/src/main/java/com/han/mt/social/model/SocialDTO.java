package com.han.mt.social.model;

import java.sql.Date;

public class SocialDTO {

	private int socialNum;
	private String category;
	private String title;
	private String contents;
	private int maximum;
	private String socialImage;
	private String nickName;
	private String email;
	private String auth;
	private String location;
	private Date socialDate;
	
	public int getSocialNum() {
		return socialNum;
	}
	public void setSocialNum(int socialNum) {
		this.socialNum = socialNum;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getMaximum() {
		return maximum;
	}
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
	public String getSocialImage() {
		return socialImage;
	}
	public void setSocialImage(String socialImage) {
		this.socialImage = socialImage;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getSocialDate() {
		return socialDate;
	}
	public void setSocialDate(Date socialDate) {
		this.socialDate = socialDate;
	}
	@Override
	public String toString() {
		return "SocialDTO [socialNum=" + socialNum + ", category=" + category + ", title=" + title + ", contents="
				+ contents + ", maximum=" + maximum + ", socialImage=" + socialImage + ", nickName=" + nickName
				+ ", email=" + email + ", auth=" + auth + ", location=" + location + ", socialDate=" + socialDate
				+ "]"+"\n";
	}
	
	
}
