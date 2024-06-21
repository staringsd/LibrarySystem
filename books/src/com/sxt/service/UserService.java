package com.sxt.service;

import java.util.List;

import com.sxt.dao.UserDao;
import com.sxt.entity.UserDB;
import com.sxt.utils.PageTool;



public class UserService {
	
	private UserDao userDao = new UserDao();
	
	
	public Integer addUser(UserDB userDB) {
		return userDao.addUser(userDB);
	}
	
	public UserDB login(String account, String password) {
		return userDao.login(account, password);
	}
	
	public PageTool<UserDB> list(String currentPage, String pageSize,Integer order){
		return userDao.list(currentPage, pageSize,order);
	}
	
	public List<UserDB> getList(UserDB userDB){
		return userDao.getList(userDB);
	}
	
	public Integer updUser(UserDB userDB) {
		return userDao.updUser(userDB);	
	}
	
	public Integer delUser(Integer uid) {
		return userDao.delUser(uid);
	}
}
