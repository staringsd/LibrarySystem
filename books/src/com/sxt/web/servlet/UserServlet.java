package com.sxt.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.sxt.entity.UserDB;
import com.sxt.service.UserService;
import com.sxt.utils.MD5;
import com.sxt.utils.PageTool;
import com.sxt.utils.PaginationUtils;
import com.sxt.utils.ResBean;



@WebServlet("/user")
public class UserServlet extends BaseServlet {

	
	private static final long serialVersionUID = 1L;
	
	private UserService userService =new UserService();
	
	public void list(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		String currentPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageTool<UserDB> pageTool = userService.list(currentPage, pageSize,null);
		String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), "user?method=list");
		request.setAttribute("pagation", pagation);
		request.setAttribute("uList", pageTool.getRows());
		request.getRequestDispatcher("admin/admin_user.jsp").forward(request, response);
	}
	
	
	public void addUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException
	{

		UserDB userDB=new UserDB();
		BeanUtils.populate(userDB,request.getParameterMap());
		userDB.setPassword(MD5.valueOf(userDB.getPassword()));
		userDB.setTimes(0);
		System.out.println(userDB);
		userService.addUser(userDB);
		response.sendRedirect("user?method=list");
	}
	
	
	public void checkUser(HttpServletRequest request,HttpServletResponse response) throws IOException
	{

		String account =request.getParameter("account");
		UserDB userDB=new UserDB();
		userDB.setAccount(account);
		List<UserDB> list=userService.getList(userDB);
		ResBean resBean=new ResBean();
		if(list !=null&&list.size()>0) {
			resBean.setCode(400);
			resBean.setMsg("账号被占用");
		}
		else {
			resBean.setCode(200);
			resBean.setMsg("账号可以使用");
		}
		
		Gson gson =new Gson();
		String json=gson.toJson(resBean);
		response.getWriter().print(json);
	}
	
	
	public void updUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDB userDB = new UserDB();
		BeanUtils.populate(userDB, request.getParameterMap());
		userService.updUser(userDB);
		response.sendRedirect("user?method=list");
	}

	public void delUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = request.getParameter("uid");
		userService.delUser(Integer.parseInt(uid));
		response.sendRedirect("user?method=list");
	}
	//最佳读者排行
	public void rank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDB userDB = (UserDB) request.getSession().getAttribute("userDB");
		//鏍规嵁褰撳墠鐧婚檰鐨勭敤鎴疯幏鍙栬鑹�
		Integer role = userDB.getRole();
		String currentPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageTool<UserDB> pageTool = userService.list(currentPage, pageSize, 1);
		//鐢熸垚鍓嶇鍒嗛〉鎸夐挳
		String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), "user?method=rank");
		request.setAttribute("pagation", pagation);
		request.setAttribute("start", pageTool.getStartIndex());
		request.setAttribute("uList", pageTool.getRows());
		//鏍规嵁role鍒ゆ柇璺宠浆鐨勯〉闈�
		if (role == 1) {
			//鏅�氱敤鎴�
			request.getRequestDispatcher("user/brtimes.jsp").forward(request, response);
		} else {
			//绠＄悊鍛�
			request.getRequestDispatcher("admin/admin_brtimes.jsp").forward(request, response);
		}
	}
}
