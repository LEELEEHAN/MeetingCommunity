package com.han.mt.social.model;

public class SocialDynamicDTO {


	private int real;
	private int socialNum;
	
	public int getReal() {
		return real;
	}
	public void setReal(int real) {
		this.real = real;
	}
	public int getSocialNum() {
		return socialNum;
	}
	public void setSocialNum(int socialNum) {
		this.socialNum = socialNum;
	}
	
	@Override
	public String toString() {
		return "SocialDynamicDTO [real=" + real + ", socialNum=" + socialNum + "]";
	}
	
	
}
