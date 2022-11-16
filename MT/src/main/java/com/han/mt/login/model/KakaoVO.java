package com.han.mt.login.model;

public class KakaoVO {

	private Long kakaoId;
	private String nickName;
	private String account_email;
	private String gender;
	private String age_range;
	private String birthday;
	public Long getKakaoId() {
		return kakaoId;
	}
	public void setKakaoId(Long kakaoId) {
		this.kakaoId = kakaoId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAccount_email() {
		return account_email;
	}
	public void setAccount_email(String account_email) {
		this.account_email = account_email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge_range() {
		return age_range;
	}
	public void setAge_range(String age_range) {
		this.age_range = age_range;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "KakaoVO [kakaoId=" + kakaoId + ", nickName=" + nickName + ", account_email=" + account_email
				+ ", gender=" + gender + ", age_range=" + age_range + ", birthday=" + birthday + "]"+"\n";
	}

	
	
	
}
