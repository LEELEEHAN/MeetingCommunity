package com.han.mt.club.model;

public class ClubMemberDTO {
	
	
	private int socialNum;
	private String id;
	private String nickName;
	private String profile;
	private String auth;
	public int getSocialNum() {
		return socialNum;
	}
	public void setSocialNum(int socialNum) {
		this.socialNum = socialNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	@Override
	public String toString() {
		return "ClubMemberDTO [socialNum=" + socialNum + ", id=" + id + ", nickName=" + nickName + ", profile="
				+ profile + ", auth=" + auth + "]";
	}
	
}
