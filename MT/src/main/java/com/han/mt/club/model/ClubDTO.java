package com.han.mt.club.model;

public class ClubDTO {
	
	private int socialNum;
	private String category;
	private String title;
	private String contents;
	private String content;
	private int maximum;
	private String socialImage;
	private String nickName;
	private String email;
	private String preview;
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getMaximum() {
		return maximum;
	}
	public void setMaximum(int maximum) {
		this.maximum = maximum;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	
	
	public String getPreview() {
		return preview;
	}
	public void setPreview(String preview) {
		this.preview = preview;
	}
	@Override
	public String toString() {
		return "ClubDTO [socialNum=" + socialNum + ", category=" + category + ", title=" + title + ", contents="
				+ contents + ", content=" + content + ", maximum=" + maximum + ", socialImage=" + socialImage
				+ ", nickName=" + nickName + ", email=" + email + ", preview=" + preview + "]"+"\n";
	}

	
	
}
