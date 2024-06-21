package com.sxt.entity;

public class BookDB {

	private Integer bid;// 涓婚敭ID
	private String bookName;// 鍥句功鍚嶇О
	private String author;// 浣滆��
	private Integer num;// 搴撳瓨
	private String press;// 鍑虹増绀�
	private Integer tid;// 绫诲埆ID
	private String typeName;// 绫诲埆鍚嶇О
	private Integer times;// 琚�熼槄娆℃暟
	
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	
	@Override
	public String toString() {
		return "BookDB [bid=" + bid + ", bookName=" + bookName + ", author=" + author + ", num=" + num + ", press="
				+ press + ", tid=" + tid + ", typeName=" + typeName + ", times=" + times + "]";
	}
	
}
