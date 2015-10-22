package com.yutong.clwwxp.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yutong.clwwxp.utils.SHA1;

public class WeixinUrlFilter implements Filter {

	// 这个Token是给微信开发者接入时填的
	// 可以是任意英文字母或数字，长度为3-32字符
	private static String Token = "ytxcwxb123454321";

	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("WeixinUrlFilter启动成功!");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		// 微信服务器将发送GET请求到填写的URL上,这里需要判定是否为GET请求
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		System.out.println("获得微信请求:" + request.getMethod() + " 方式");
		if (isGet) {
			// 验证URL真实性
			String signature = request.getParameter("signature");// 微信加密签名
			String timestamp = request.getParameter("timestamp");// 时间戳
			String nonce = request.getParameter("nonce");// 随机数
			String echostr = request.getParameter("echostr");// 随机字符串
			List<String> params = new ArrayList<String>();
			params.add(Token);
			params.add(timestamp);
			params.add(nonce);
			// 1. 将token、timestamp、nonce三个参数进行字典序排序
			Collections.sort(params, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
			// 2. 将三个参数字符串拼接成一个字符串进行sha1加密
			String temp = SHA1.encode(params.get(0) + params.get(1)	+ params.get(2));
			if (temp.equals(signature)) {
				response.getWriter().write(echostr);
			}
			
		} else {
	        /*response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
	        out.println("<HTML>");
	        out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
	        out.println("  <BODY>");
	        this.printMethod(request, response);
	        this.printParameters(request, response);
	        out.println("  </BODY>");
	        out.println("</HTML>");
	        out.flush();
	        out.close();*/
	        
	        this.printMethod(request, response);
	        this.printParameters(request, response);
	        chain.doFilter(request, response);
		}
	}
	
    private void printMethod(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        out.println("Method: " + request.getMethod() + "<br>"); 
    }
    
	private void printParameters(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        Enumeration<String> e = request.getParameterNames();
        String parameterName, parameterValue;
        while(e.hasMoreElements())
        {
            parameterName = e.nextElement();
            parameterValue = request.getParameter(parameterName);
            out.println(parameterName + ": " + parameterValue + "<br>");            
        }
    }


	@Override
	public void destroy() {

	}
}
