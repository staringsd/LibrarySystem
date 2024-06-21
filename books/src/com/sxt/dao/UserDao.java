package com.sxt.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.sxt.entity.UserDB;
import com.sxt.utils.C3p0Tool;
import com.sxt.utils.PageTool;



public class UserDao {
	
	QueryRunner queryRunner = new QueryRunner(C3p0Tool.getDataSource());
	
	BeanProcessor bean = new GenerousBeanProcessor();
	RowProcessor processor = new BasicRowProcessor(bean);
	
	
	//判断登录的用户是否包含在数据库内部
	public UserDB login(String account, String password) {
		String sql = "select * from t_user where account = ? and password = ?";
		Object[] params = {account, password};
		try {
			return queryRunner.query(sql, new BeanHandler<UserDB>(UserDB.class, processor), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//查用户列表
	public PageTool<UserDB> list(String currentPage, String pageSize, Integer order){
		try {
			StringBuffer listSql = new StringBuffer("select * ");
			StringBuffer countSql = new StringBuffer("select count(*) ");
			StringBuffer sql = new StringBuffer("from t_user");
			if (order != null && order == 1) {
				sql.append(" where role = 1 order by times desc");
			}
			//
			Long total = queryRunner.query(countSql.append(sql).toString(), new ScalarHandler<Long>());
			//
			PageTool<UserDB> pageTools = new PageTool<UserDB>(total.intValue(), currentPage, pageSize);
			sql.append(" limit ?,?");
			//		
			 List<UserDB> list = queryRunner.query(listSql.append(sql).toString(), new BeanListHandler<UserDB>(UserDB.class, processor),pageTools.getStartIndex(),pageTools.getPageSize());
			 pageTools.setRows(list);
			 System.out.println(pageTools);
			 return pageTools;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new PageTool<UserDB>(0, currentPage, pageSize);
		}
	

	
	//查询用户
	public List<UserDB> getList(UserDB userDB){
		String sql = "select * from t_user where account = ?";
		Object[] params = {userDB.getAccount()};
		try {
			
			return queryRunner.query(sql, new BeanListHandler<UserDB>(UserDB.class, processor),params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//添加用户
	public Integer addUser(UserDB userDB) {
		String sql = "insert into t_user (account,password,name,phone,times,lend_num,max_num,role) values (?,?,?,?,?,?,?,?)";
		Object[] params = {userDB.getAccount(),userDB.getPassword(),userDB.getName(),userDB.getPhone(),userDB.getTimes(),userDB.getLendNum(),userDB.getMaxNum(),userDB.getRole()};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//删除用户
	public int  delUser(Integer uid){
		String sql ="delete from t_user where uid =?";
		Object[] params= {uid};
		try {
			return queryRunner.update(sql,params);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	//修改用户信息
	public Integer updUser(UserDB userDB) {
		String sql = "update t_user set phone = ?, lend_num = ?, max_num = ? where uid = ?";
		Object[] params = {userDB.getPhone(),userDB.getLendNum(),userDB.getMaxNum(),userDB.getUid()};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer updNum(UserDB userDB, Connection conn) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "update t_user set times = ?, max_num = ? where uid = ?";
		Object[] params = {userDB.getTimes(),userDB.getMaxNum(),userDB.getUid()};
		return qr.update(conn, sql, params);
	}
	
}
