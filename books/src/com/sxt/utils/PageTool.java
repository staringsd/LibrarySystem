package com.sxt.utils;

import java.util.List;

public class PageTool<E> {
	
	public PageTool(Integer totalCount, String currentPage, String pageSize) {
		this.totalCount = totalCount;
		initCurrentPage(currentPage);
		initPageSize(pageSize);
		initTotalPages();
		initPrePage();
		initNextPage();
		initStartIndex();
	}
	
	private Integer pageSize;//椤靛閲�
	private Integer totalCount;//鎬昏褰曟暟
	private Integer totalPages;//鎬婚〉鏁�
	private Integer currentPage;//褰撳墠椤电爜
	private Integer prePage;//涓婁竴椤�
	private Integer nextPage;//涓嬩竴椤�
	private Integer startIndex;//姣忛〉绗竴鏉¤褰曠殑瓒呭涓嬫爣
	private List<E> rows; //鏌ュ嚭褰撳墠椤电殑鏁版嵁    
	
	//缁欏綋鍓嶉〉鐮佸垵濮嬪寲
	private void initCurrentPage(String currentPage) {
		if (currentPage == null) {
			this.currentPage = 1;
		}else {
			this.currentPage=Integer.valueOf(currentPage);
		}
	}
	
	//榛樿鍒嗛〉瀹归噺
	private void initPageSize(String pageSize) {
		if (pageSize == null) {
			this.pageSize = 5;
		}else {
			this.pageSize=Integer.valueOf(pageSize);
		}
	}
	
	//璁＄畻鎬婚〉鏁�
	private void initTotalPages() {
		this.totalPages= (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1);
	}
	
	//涓婁竴椤�
	private void initPrePage() {
		if (currentPage == 1) {
			this.prePage = 1;
		}else {
			this.prePage=currentPage - 1;
		}
	}
	//涓嬩竴椤�
	private void initNextPage() {
		if (currentPage == totalPages) {
			this.nextPage = totalPages;
		}else {
			this.nextPage=currentPage + 1;
		}
	}
	
	//姣忛〉绗竴鏉¤褰曠殑瓒呭涓嬫爣
	private void initStartIndex() {
		this.startIndex = pageSize * (currentPage - 1);
	}
	
	
	
	
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPrePage() {
		return prePage;
	}

	public void setPrePage(Integer prePage) {
		this.prePage = prePage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "PageTool [pageSize=" + pageSize + ", totalCount=" + totalCount + ", totalPages=" + totalPages
				+ ", currentPage=" + currentPage + ", prePage=" + prePage + ", nextPage=" + nextPage + ", startIndex="
				+ startIndex + ", rows=" + rows + "]";
	}
	
}