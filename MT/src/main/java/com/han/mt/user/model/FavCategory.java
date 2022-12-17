package com.han.mt.user.model;

public class FavCategory {
	
	private String id;
	private String category;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "FavCategory [id=" + id + ", category=" + category + "]"+"\n";
	}
	
	

}
