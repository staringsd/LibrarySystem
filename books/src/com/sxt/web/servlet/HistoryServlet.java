package com.sxt.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sxt.entity.HistoryDB;
import com.sxt.entity.UserDB;
import com.sxt.service.HistoryService;
import com.sxt.utils.DateUtils;
//import com.sxt.utils.DateUtils;
import com.sxt.utils.PageTool;
import com.sxt.utils.PaginationUtils;

/**
 * 鍥句功鍊熼槄鍘嗗彶璁板綍
 * @author Administrator
 *
 */
@WebServlet("/history")
public class HistoryServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	private HistoryService historyService = new HistoryService();

	/**
	 * 鏌ヨ姝ｅ湪琚�熼槄涔︾睄
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDB userDB = (UserDB) request.getSession().getAttribute("userDB");
		//鏍规嵁褰撳墠鐧婚檰鐨勭敤鎴疯幏鍙栬鑹�
		Integer role = userDB.getRole();
		String currentPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		//鏅�氱敤鎴峰彧鑳芥煡璇㈣嚜宸辩殑  绠＄悊鍛樻煡璇㈡墍鏈�  
		PageTool<HistoryDB> pageTool = null;
		if (role == 1) {
			//鏅�氱敤鎴�
			pageTool = historyService.listByPage(currentPage, pageSize, userDB.getUid(), 1);
		} else {
			//绠＄悊鍛�
			pageTool = historyService.listByPage(currentPage, pageSize, null, 1);
		}		
		String path = "history?method=list";
		//鐢熸垚鍓嶇鍒嗛〉鎸夐挳
		String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), path);
		request.setAttribute("pagation", pagation);
		request.setAttribute("hList", pageTool.getRows());
		//鏍规嵁role鍒ゆ柇璺宠浆鐨勯〉闈�
		if (role == 1) {
			//鏅�氱敤鎴�
			request.getRequestDispatcher("user/borrow.jsp").forward(request, response);
		} else {
			//绠＄悊鍛�
			request.getRequestDispatcher("admin/admin_borrow.jsp").forward(request, response);
		}
		
	}
	
	/**
	 * 鏌ヨ宸茬粡琚綊杩樼殑鍊熼槄涔︾睄璁板綍
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void backList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDB userDB = (UserDB) request.getSession().getAttribute("userDB");
		//鏍规嵁褰撳墠鐧婚檰鐨勭敤鎴疯幏鍙栬鑹�
		Integer role = userDB.getRole();
		String currentPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		//鏅�氱敤鎴峰彧鑳芥煡璇㈣嚜宸辩殑  绠＄悊鍛樻煡璇㈡墍鏈�  
		PageTool<HistoryDB> pageTool = null;
		if (role == 1) {
			//鏅�氱敤鎴�
			pageTool = historyService.listByPage(currentPage, pageSize, userDB.getUid(), 2);
		} else {
			//绠＄悊鍛�
			pageTool = historyService.listByPage(currentPage, pageSize, null, 2);
		}		
		String path = "history?method=backList";
		//鐢熸垚鍓嶇鍒嗛〉鎸夐挳
		String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), path);
		request.setAttribute("pagation", pagation);
		request.setAttribute("hList", pageTool.getRows());
		//鏍规嵁role鍒ゆ柇璺宠浆鐨勯〉闈�
		if (role == 1) {
			//鏅�氱敤鎴�
			request.getRequestDispatcher("user/history.jsp").forward(request, response);
		} else {
			//绠＄悊鍛�
			request.getRequestDispatcher("admin/admin_history.jsp").forward(request, response);
		}
		
	}
	
	/**
	 * 鍥句功褰掕繕寤舵湡
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws ServletException
	 * @throws IOException
	 */
	
}
