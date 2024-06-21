package com.sxt.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.sxt.entity.BookDB;
import com.sxt.entity.TypeDB;
import com.sxt.entity.UserDB;
import com.sxt.service.BookService;
import com.sxt.service.TypeService;
import com.sxt.utils.PageTool;
import com.sxt.utils.PaginationUtils;
import com.sxt.utils.ResBean;






@WebServlet("/book")
public class BookServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;
	
	private BookService bookService = new BookService();
	private TypeService typeService = new TypeService();
	
	public void listByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDB userDB = (UserDB) request.getSession().getAttribute("userDB");
		//鏍规嵁褰撳墠鐧婚檰鐨勭敤鎴疯幏鍙栬鑹�
		Integer role = userDB.getRole();
		String word = request.getParameter("word");
		String currentPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageTool<BookDB> pageTool = bookService.listByPage(currentPage, pageSize, word, null);
		String path = "book?method=listByPage";
		if (word != null && word != "") {
			path += "&word=" + word;
		}
		//鐢熸垚鍓嶇鍒嗛〉鎸夐挳
		String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), path);
		List<TypeDB> typeList = typeService.list(null, null);
		request.setAttribute("pagation", pagation);
		request.setAttribute("typeList", typeList);
		request.setAttribute("word", word);
		request.setAttribute("bList", pageTool.getRows());
		//鏍规嵁role鍒ゆ柇璺宠浆鐨勯〉闈�
		if (role == 1) {
			//鏅�氱敤鎴�
			request.getRequestDispatcher("user/select.jsp").forward(request, response);
		} else {
			//绠＄悊鍛�
			request.getRequestDispatcher("admin/admin_book.jsp").forward(request, response);
		}
		
	}
	public void checkBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String bookName = request.getParameter("bookName");
		List<BookDB> list = bookService.list(bookName);
		ResBean resBean = new ResBean();
		if (list != null && list.size() > 0) {
			resBean.setCode(400);
			resBean.setMsg("图书名称已经存在");
		} else {
			resBean.setCode(200);
			resBean.setMsg("图书名称可以使用");
		}
		Gson gson = new Gson();
		String json = gson.toJson(resBean);
		response.getWriter().print(json);
	}
	
	public void addBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BookDB bookDB = new BookDB();
		BeanUtils.populate(bookDB, request.getParameterMap());
		bookService.addBook(bookDB);
		response.sendRedirect("book?method=listByPage");
	}
	
	public void updBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BookDB bookDB = new BookDB();
		BeanUtils.populate(bookDB, request.getParameterMap());
		bookService.updBook(bookDB);
		response.sendRedirect("book?method=listByPage");
	}
	
	
	public void delBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bid = request.getParameter("bid");
		bookService.delBook(bid);
		response.sendRedirect("book?method=listByPage");
	}
	
	public void borrowBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//鑾峰彇瑕佸�熼槄鐨勫浘涔D
		String bid = request.getParameter("bid");
		//鑾峰彇褰撳墠鐢ㄦ埛淇℃伅
		UserDB userDB = (UserDB) request.getSession().getAttribute("userDB");
		bookService.borrowBook(userDB,bid);
		response.sendRedirect("history?method=list");
	}
	
	
	public void backBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//鑾峰彇瑕佸綊杩樿褰曠殑ID
		String hid = request.getParameter("hid");
		bookService.backBook(hid);
		response.sendRedirect("history?method=backList");
	}
	
	/**
	 * 热门书籍
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void rank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDB userDB = (UserDB) request.getSession().getAttribute("userDB");
		//鏍规嵁褰撳墠鐧婚檰鐨勭敤鎴疯幏鍙栬鑹�
		Integer role = userDB.getRole();
		String currentPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageTool<BookDB> pageTool = bookService.listByPage(currentPage, pageSize, null, 1);
		String path = "book?method=rank";
		//鐢熸垚鍓嶇鍒嗛〉鎸夐挳
		String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), path);
		request.setAttribute("pagation", pagation);
		request.setAttribute("start", pageTool.getStartIndex());
		request.setAttribute("bList", pageTool.getRows());
		//鏍规嵁role鍒ゆ柇璺宠浆鐨勯〉闈�
		if (role == 1) {
			//鏅�氱敤鎴�
			request.getRequestDispatcher("user/bdtimes.jsp").forward(request, response);
		} else {
			//绠＄悊鍛�
			request.getRequestDispatcher("admin/admin_bdtimes.jsp").forward(request, response);
		}
		
	}
}
