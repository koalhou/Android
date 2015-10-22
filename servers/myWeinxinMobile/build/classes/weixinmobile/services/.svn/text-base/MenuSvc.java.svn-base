package weixinmobile.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weixinFundation.core.menu.CustomMenuFileReader;
import weixinFundation.core.menu.MenuUtil;
import weixinFundation.core.utils.LogHelper;

/**
 * 指定 查询字符串 a=create 来创建菜单
 * 
 * @author yunfei
 *
 */
@WebServlet( urlPatterns="/MenuSvc")
public class MenuSvc  extends HttpServlet {
	
	public MenuSvc() {
		super();
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LogHelper.i("开始进入menuSvc");
		String action = req.getParameter("a");
		PrintWriter writer = resp.getWriter();
		if("create".equals(action)){
			LogHelper.i("menuSvc>> 准备创建菜单");
			
			//从默认的 menu.txt 读取 菜单。
			String menuDesc = CustomMenuFileReader.readMenuDescriptionFile();
			//生产菜单
			boolean isok = MenuUtil.createMenu(
					WeixinContextImpl.appID,
					WeixinContextImpl.appsecret,
					menuDesc);
			if(isok){
				writer.write("true");
				LogHelper.i("操作成功");
			}else{
				writer.write("false");
				LogHelper.i("操作失败");
			}
		}else{
			writer.write("缺少必要的参数");
			LogHelper.i("缺少必要的参数");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
