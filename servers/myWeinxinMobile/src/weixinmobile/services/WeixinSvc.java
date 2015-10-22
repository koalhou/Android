package weixinmobile.services;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import weixinFundation.core.common.WeixinBaseServlet;
import weixinFundation.core.common.WeixinContext;

/**
 * Servlet implementation class WeixinSvc
 */
@WebServlet("/WeixinSvc")
public class WeixinSvc extends WeixinBaseServlet {
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WeixinSvc() {
		super();
	}

	@Override
	protected WeixinContext onInitWeixinContext() {
		return new WeixinContextImpl();
	}


}
