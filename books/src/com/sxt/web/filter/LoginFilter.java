package com.sxt.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class LoginFilter implements Filter {
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 寮鸿浆璇锋眰鍜屽搷搴�
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		//鑾峰彇璇锋眰鐨勮矾寰�
		String uri = req.getRequestURI();
		//璺緞鍒ゆ柇锛岄儴鍒嗗唴瀹逛笉鎷︽埅
		if (uri.contains("/login") || uri.contains("/static")) {
			//鐩存帴鏀捐
			chain.doFilter(req, res);
		} else {
			//楠岃瘉鏄惁鐧婚檰
			Object userDB = req.getSession().getAttribute("userDB");
			if (userDB != null) {
				//宸茬櫥褰曪紝鏀捐
				chain.doFilter(req, res);
			} else {
				//鏈櫥褰�
				res.sendRedirect("login.jsp");
			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
