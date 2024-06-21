package com.sxt.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sxt.entity.TypeDB;
import com.sxt.service.TypeService;
import com.sxt.utils.PageTool;
import com.sxt.utils.PaginationUtils;
import com.sxt.utils.ResBean;
/**
 * 鍥句功鍒嗙被
 * @author Administrator
 *
 */
@WebServlet("/type")
public class TypeServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	private TypeService typeService = new TypeService();
	
	/**
	 * 鍒楄〃  鍒嗛〉
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void listByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currentPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageTool<TypeDB> pageTool = typeService.listByPage(currentPage, pageSize);
		//鐢熸垚鍓嶇鍒嗛〉鎸夐挳
		String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), "type?method=listByPage");
		request.setAttribute("pagation", pagation);
		request.setAttribute("tList", pageTool.getRows());
		request.getRequestDispatcher("admin/admin_booktype.jsp").forward(request, response);
	}
	
	/**
	 * 绫诲瀷鍚嶇О鏍￠獙
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void checkType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String typeName = request.getParameter("typeName");
		List<TypeDB> list = typeService.list(null, typeName);
		ResBean resBean = new ResBean();
		if (list != null && list.size() > 0) {
			resBean.setCode(400);
			resBean.setMsg("类别名称已存在");
		} else {
			resBean.setCode(200);
			resBean.setMsg("类别名称可以使用");
		}
		Gson gson = new Gson();
		String json = gson.toJson(resBean);
		response.getWriter().print(json);
	}
	
	/**
	 * 绫诲瀷娣诲姞
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String typeName = request.getParameter("typeName");
		typeService.addType(typeName);
		request.getRequestDispatcher("type?method=listByPage").forward(request, response);
	}
	
	/**
	 * 绫诲瀷淇敼
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tid = request.getParameter("tid");
		String typeName = request.getParameter("typeName");
		TypeDB typeDB = new TypeDB();
		typeDB.setTid(Integer.parseInt(tid));
		typeDB.setTypeName(typeName);
		typeService.updType(typeDB);
		request.getRequestDispatcher("type?method=listByPage").forward(request, response);
	}
	
	/**
	 * 绫诲瀷鍒犻櫎
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void delType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tid = request.getParameter("tid");
		typeService.delType(Integer.parseInt(tid));
		request.getRequestDispatcher("type?method=listByPage").forward(request, response);
	}
}
