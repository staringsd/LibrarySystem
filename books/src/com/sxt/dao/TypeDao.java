package com.sxt.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.sxt.entity.TypeDB;
import com.sxt.utils.C3p0Tool;
import com.sxt.utils.PageTool;

/**
 * 鍥句功鍒嗙被
 * @author Administrator
 *
 */
public class TypeDao {


	QueryRunner queryRunner = new QueryRunner(C3p0Tool.getDataSource());
	//寮�鍚┘宄拌嚜鍔ㄨ浆鎹�
	BeanProcessor bean = new GenerousBeanProcessor();
	RowProcessor processor = new BasicRowProcessor(bean);
	
	/**
	 * 鐢ㄦ埛鍒楄〃 鍒嗛〉
	 * @return
	 */
	public PageTool<TypeDB> listByPage(String currentPage, String pageSize){
		try {
			StringBuffer listSql = new StringBuffer("select * ");
			StringBuffer countSql = new StringBuffer("select count(*) ");
			StringBuffer sql = new StringBuffer("from t_type");
			//鑾峰彇鎬昏褰曟暟
			Long total = queryRunner.query(countSql.append(sql).toString(), new ScalarHandler<Long>());
			//鍒濆鍖栧垎椤靛伐鍏�
			PageTool<TypeDB> pageTools = new PageTool<TypeDB>(total.intValue(), currentPage, pageSize);
			sql.append(" limit ?,?");
			//褰撳墠椤电殑鏁版嵁		
			 List<TypeDB> list = queryRunner.query(listSql.append(sql).toString(), new BeanListHandler<TypeDB>(TypeDB.class, processor),pageTools.getStartIndex(),pageTools.getPageSize());
			 pageTools.setRows(list);
			 System.out.println(pageTools);
			 return pageTools;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new PageTool<TypeDB>(0, currentPage, pageSize);
	}
	
	/**
	 * 澶氭潯浠舵煡璇�
	 * @param tid
	 * @param typeName
	 * @return
	 */
	public List<TypeDB> list(String tid, String typeName){
		StringBuffer sql = new StringBuffer("select * from t_type where 1 = 1 ");
		List<Object> params = new ArrayList<>();
		if (tid != null && tid != "") {
			sql.append("and tid = ? ");
			params.add(tid);
		}
		if (typeName != null && typeName != "") {
			sql.append("and type_name = ?");
			params.add(typeName);
		}
		try {
			return queryRunner.query(sql.toString(), new BeanListHandler<TypeDB>(TypeDB.class, processor), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 娣诲姞鍥句功绫诲埆
	 * @param 
	 * @return
	 */
	public Integer addType(String typeName) {
		String sql = "insert into t_type (type_name) values (?)";
		Object[] params = {typeName};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 淇敼鍥句功鍒嗙被
	 * @param userDB
	 * @return
	 */
	public Integer updType(TypeDB typeDB) {
		String sql = "update t_type set type_name = ? where tid = ?";
		Object[] params = {typeDB.getTypeName(), typeDB.getTid()};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 鍒犻櫎鍥句功鍒嗙被
	 * @param uid
	 * @return 
	 */
	public int delType(Integer tid) {
		String sql = "delete from t_type where tid = ?";
		Object[] params = {tid};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
