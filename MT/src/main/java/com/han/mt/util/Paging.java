package com.han.mt.util;
import java.util.ArrayList;
import java.util.List;

public class Paging {

	private int offset;		//湲곗�二쇱냼�뿉�꽌 �떎�쓬�럹�씠吏�濡� 媛덈븣 異붽�濡� �뜑�빐吏� 媛�
	private int limit;		//~源뚯�
	private int currentPageNumber;  //�쁽�옱�럹�씠吏� �꽆踰�
	private int nextPageNumber; 	//�떎�쓬�럹�씠吏� �꽆踰� 
	private int prevPageNumber;		//�씠�쟾 �럹�씠吏� �꽆踰�
	private List<Integer> pageNumberList;	

	private List<Object> pageData;
	private int lastPage;
	private int lastPageMinus;
	private String keyword;
	

	public Paging(List<Object> datas, int currentPageNumber, int limit) {
		this.offset = limit * (currentPageNumber - 1); //1�럹�씠吏� 0 2�럹�씠吏� 10 3�럹�씠吏� 20 4�럹�씠吏� 30
		this.limit = limit;
		this.currentPageNumber = currentPageNumber;
		this.nextPageNumber = currentPageNumber + 1;
		this.prevPageNumber = currentPageNumber - 1;
		int pageNum = 1;
		this.pageNumberList = new ArrayList<Integer>();
		for(int i = 0; i < datas.size(); i += limit) {
			this.pageNumberList.add(pageNum++);
		}
		int max = this.offset + this.limit;
		max = max < datas.size() ? max : datas.size();
		this.pageData = datas.subList(this.offset, max);
		int page2=datas.size()%10;
		if(page2 == 0) {
			this.lastPage = (datas.size())/10;
		} else {
			this.lastPage = ((datas.size())/10)+1;

		}
		this.lastPageMinus=lastPage-1;
	}
	public String getkeyword() {
		return keyword;
	}




	public void setkeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public int getLastPage() {
		return lastPage;
	}
	public int getOffset() {
		return offset;
	}
	
	public int getLimit() {
		return limit;
	}
	
	public int getCurrentPageNumber() {
		return currentPageNumber;
	}
	
	public int getNextPageNumber() {
		return nextPageNumber;
	}
	
	public int getPrevPageNumber() {
		return prevPageNumber;
	}
	
	public List<Integer> getPageNumberList() {
		return pageNumberList;
	}
	
	public List<Integer> getPageNumberList(int start, int end) {
		start = start > 0 ? start : 1;
		end = end < this.pageNumberList.size() ? end : this.pageNumberList.size();
		return pageNumberList.subList(start - 1, end);
	}
	
	public List<Object> getPageData() {
		return pageData;
	}
	

	public Object getPageData(String search) {
		return pageData;
	}
	
	public boolean hasNextPage() {
		return this.pageNumberList.contains(this.nextPageNumber);
	}
	
	public boolean hasPrevPage() {
		return this.pageNumberList.contains(this.prevPageNumber);
	}




	@Override
	public String toString() {
		return "Paging [offset=" + offset + ", limit=" + limit + ", currentPageNumber=" + currentPageNumber
				+ ", nextPageNumber=" + nextPageNumber + ", prevPageNumber=" + prevPageNumber + ", pageNumberList="
				+ pageNumberList + ", pageData=" + pageData + ", keyword=" + keyword + "]";
	}
}


