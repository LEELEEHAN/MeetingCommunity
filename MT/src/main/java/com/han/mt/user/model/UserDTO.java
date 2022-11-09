package com.han.mt.user.model;

import java.sql.Date;

public class UserDTO {

	private String email;
	private String password;
	private String name;
	private String nickName;
	private Date birth;
	private String phone;
	private String gender;
	private String hidden;
	private String deleted;
	private Date deleteDate;
	private Date dateLog;
	private int mannerPoint;
	private String profile;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHidden() {
		return hidden;
	}
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public Date getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	public Date getDateLog() {
		return dateLog;
	}
	public void setDateLog(Date dateLog) {
		this.dateLog = dateLog;
	}
	public int getMannerPoint() {
		return mannerPoint;
	}
	public void setMannerPoint(int mannerPoint) {
		this.mannerPoint = mannerPoint;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	@Override
	public String toString() {
		return "UserDTO [email=" + email + "password=" + password + ", name=" + name + ", nickName=" + nickName + ", birth="
				+ birth + ", phone=" + phone + ", gender=" + gender + ", hidden=" + hidden
				+ ", deleted=" + deleted + ", deleteDate=" + deleteDate + ", dateLog=" + dateLog + ", mannerPoint="
				+ mannerPoint + ", profile=" + profile + "]"+"\n";
	}
	
	
	
}
