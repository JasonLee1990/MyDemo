package org.jason.demo.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HttpFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		config.getInitParameter("baidu");
		Enumeration<String> enumer = config.getInitParameterNames();
		while(enumer.hasMoreElements()){
			String param = enumer.nextElement();
		}
		

	}

}
