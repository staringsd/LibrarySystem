package com.sxt.entity;

import java.util.Date;
/**
 * 鍥句功鍊熼槄鍘嗗彶璁板綍
 * @author Administrator
 *
 */
public class HistoryDB {

	private Integer hid; // '璁板綍琛↖D',
	private Integer uid; // '鐢ㄦ埛ID',
	private String name; // '鐢ㄦ埛濮撳悕',
	private String account; // '鐢ㄦ埛璐﹀彿',
	private Integer bid; // '鍥句功ID',
	private String bookName; // '鍥句功鍚嶇О',
	private Date beginTime; // '鍊熶功鏃堕棿',
	private Date endTime; // '杩樹功鏃堕棿',
	private Integer status; // '鍊熼槄鐘舵�侊紝1涓烘鍦ㄥ�熼槄锛�2 宸茬粡杩樹功'
	public Integer getHid() {
		return hid;
	}
	public void setHid(Integer hid) {
		this.hid = hid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
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
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "HistoryDB [hid=" + hid + ", uid=" + uid + ", name=" + name + ", account=" + account + ", bid=" + bid
				+ ", bookName=" + bookName + ", beginTime=" + beginTime + ", endTime=" + endTime + ", status=" + status
				+ "]";
	}
	
	
}
