package com.sxt.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sxt.dao.BookDao;
import com.sxt.dao.HistoryDao;
import com.sxt.dao.UserDao;
import com.sxt.entity.BookDB;
import com.sxt.entity.HistoryDB;
import com.sxt.entity.UserDB;
import com.sxt.utils.C3p0Tool;
import com.sxt.utils.DateUtils;
import com.sxt.utils.MyException;
import com.sxt.utils.PageTool;
import com.sxt.utils.PaginationUtils;







public class BookService {
	
	private BookDao bookDao = new BookDao();
	private HistoryDao historyDao = new HistoryDao();
	
	private UserDao userDao = new UserDao();
	
	public PageTool<BookDB> listByPage(String currentPage, String pageSize, String word, Integer order){
		return bookDao.list(currentPage, pageSize, word, order);
	}
	
	public List<BookDB> list(String bookName){
		return bookDao.list(bookName, null);
	}
	
	public Integer addBook(BookDB bookDB) {
		return bookDao.addBook(bookDB);
	}
	public Integer updBook(BookDB bookDB) {
		return bookDao.updBook(bookDB);
	}
	
	public int delBook(String bid) {
		return bookDao.delBook(bid);
	}

	public void borrowBook(UserDB userDB, String bid) {
		//鑾峰彇杩炴帴
		Connection conn = C3p0Tool.getConnection();
		try {
			//璁剧疆浜嬬墿涓嶈嚜鍔ㄦ彁浜�
			conn.setAutoCommit(false);
			// 鏍规嵁bid鑾峰彇瀹屾暣鐨�
			List<BookDB> list = bookDao.list(null, bid);
			BookDB bookDB = list.get(0);
			//淇濊瘉鐢ㄦ埛鏁版嵁鍜屾暟鎹簱鍚屾鏇存柊
			userDB = userDao.getList(userDB).get(0);
			
			// t_history 鍒涘缓鍥句功鍊熼槄鍘嗗彶璁板綍
			HistoryDB historyDB = new HistoryDB();
			historyDB.setUid(userDB.getUid());
			historyDB.setName(userDB.getName());
			historyDB.setAccount(userDB.getAccount());
			historyDB.setBid(bookDB.getBid());
			historyDB.setBookName(bookDB.getBookName());
			Date d = new Date();
			historyDB.setBeginTime(d);
			historyDB.setEndTime(DateUtils.dateAdd(d, userDB.getLendNum()));//杩樹功鏃堕棿  鍊熶功鏃堕棿 + lend_num
			historyDB.setStatus(1);
			historyDao.addHistory(historyDB, conn);
			
			// t_book 鏀瑰彉鍥句功鐨勫簱瀛�  book.num--  book.times++
			Integer num = bookDB.getNum();
			//搴撳瓨鍒ゆ柇
			if (num <= 0) {
				throw new MyException("库存不足");
			}
			bookDB.setNum(--num);
			bookDB.setTimes(bookDB.getTimes() + 1);
			bookDao.changeNum(bookDB,conn);
			
			// t_user 鏀瑰彉鐢ㄦ埛鍥句功鍊熼槄淇℃伅  user.times++   max_num--
			userDB.setTimes(userDB.getTimes() + 1);
			//鍙�熼槄鏁伴噺鍒ゆ柇
			if (userDB.getMaxNum() <= 0) {
				throw new MyException("借阅次数已满");
			}
			userDB.setMaxNum(userDB.getMaxNum() - 1);
			userDao.updNum(userDB,conn);
			//浜嬬墿鎻愪氦
			conn.commit();
		} catch (Exception e) {
			//浜嬬墿鍥炴粴			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			//鍒ゆ柇鑷畾涔夊紓甯�
			if (e instanceof MyException) {
				throw new MyException(e.getMessage());
			} else {
				e.printStackTrace();
				throw new MyException("借阅失败");
			}
		}
		
	}
	public void backBook(String hid) {
		//鑾峰彇杩炴帴
		Connection conn = C3p0Tool.getConnection();
		try {
			//璁剧疆浜嬬墿涓嶈嚜鍔ㄦ彁浜�
			conn.setAutoCommit(false);
			//鏍规嵁hid鑾峰彇historyDB, 淇敼status涓�2
			HistoryDB historyDB = historyDao.list(hid).get(0);
			historyDB.setStatus(2);
			historyDao.updHistory(historyDB, conn);
			
			//鏍规嵁historyDB鑾峰彇鍥句功bid
			Integer bid = historyDB.getBid();
			//鏍规嵁bid鑾峰彇鍥句功淇℃伅锛屼慨鏀瑰簱瀛� + 1
			BookDB bookDB = bookDao.list(historyDB.getBookName(), bid+"").get(0);
			bookDB.setNum(bookDB.getNum() + 1);
			bookDao.changeNum(bookDB, conn);
			
			//鏍规嵁historyDB鑾峰彇鐢ㄦ埛account
			String account = historyDB.getAccount();
			//鏍规嵁uid鑾峰彇鐢ㄦ埛淇℃伅,淇敼max_num +1
			UserDB userDB = new UserDB();
			userDB.setAccount(account);
			userDB = userDao.getList(userDB).get(0);
			userDB.setMaxNum(userDB.getMaxNum() + 1);
			userDao.updNum(userDB, conn);
			//浜嬬墿鎻愪氦
			conn.commit();
		} catch (Exception e) {
			//浜嬬墿鍥炴粴			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			//鍒ゆ柇鑷畾涔夊紓甯�
			if (e instanceof MyException) {
				throw new MyException(e.getMessage());
			} else {
				e.printStackTrace();
				throw new MyException("还书失败");
			}
		}
	}
	
	
}
