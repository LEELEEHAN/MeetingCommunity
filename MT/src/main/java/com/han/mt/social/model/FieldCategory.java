package com.han.mt.social.model;

public class FieldCategory {

	private String category;
	private int real;

	public int getReal() {
		return real;
	}

	public void setReal(int real) {
		this.real = real;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "FieldCategory [category=" + category + ", real=" + real + "]"+"\n";
	}

}
