package com.han.mt.social.model;

public class SocialDTO {

	private int socialNum;
	private String category;
	private String title;
	private String contents;
	private int maximum;
	private String socialImage;
	private String nickName;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
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
	
	@Override
	public String toString() {
		return "SocialDTO [socialNum=" + socialNum + ", category=" + category + ", title=" + title + ", contents="
				+ contents + ", maximum=" + maximum + ", socialImage=" + socialImage + ", nickName=" + nickName
				+ "]";
	}
}
