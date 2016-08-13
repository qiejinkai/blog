package com.qjk.qblog.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qjk.qblog.data.Admin;
import com.qjk.qblog.util.Value;

/**
 * 登陆拦截
 * 
 * @author qiejinkai
 *
 */
public class AdminLoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(true);
		String path = req.getRequestURI();

		if (!Value.isEmpty(path) && (path.startsWith("/admin")||path.startsWith("/druid"))) {

			if (path.indexOf("/admin/login") >= 0) {

				chain.doFilter(req, resp);

				return;
			}

			Object o = session.getAttribute("admin");

			if (o != null && o instanceof Admin) {
				Admin admin = (Admin) o;
				long id = admin.getAdminId();

				if (id != 0) {

					chain.doFilter(req, resp);

					return;
				}

			}

			resp.sendRedirect("/admin/login");

		} else {
			chain.doFilter(req, resp);
			
			return;
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	

}
