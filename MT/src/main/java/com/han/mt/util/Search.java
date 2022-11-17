package com.han.mt.util;

import java.util.ArrayList;
import java.util.List;

public class Search extends Paging {


	public Search(List<Object> datas, int currentPageNumber, int limit) {
		super(datas, currentPageNumber, limit);
		
	}

	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "Search [keyword=" + keyword + "]";
	}	
	
	
	
}
