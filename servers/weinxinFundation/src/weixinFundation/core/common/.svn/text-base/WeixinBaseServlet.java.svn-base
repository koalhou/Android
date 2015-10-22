package weixinFundation.core.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weixinFundation.core.utils.LogHelper;
import weixinFundation.core.utils.MessageTypeUtil;

public abstract class WeixinBaseServlet extends HttpServlet {
	private static final long serialVersionUID = 914473708162771450L;

	WeixinContext mWeixinContext;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WeixinBaseServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		mWeixinContext = onInitWeixinContext();
		if (mWeixinContext == null)
			throw new RuntimeException(
					"初始化WeixinServlet错误，onInitWeixinContext返回了空值");
		mWeixinContext.onCreate();
	}

	protected abstract WeixinContext onInitWeixinContext();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		LogHelper.i("\r\n收到GET消息：");

		VerifyMessageUtil util = new VerifyMessageUtil();
		util.setToken(mWeixinContext.getToken());
		util.rerifyRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		// 读消息
		String messageBody = readMessageBodyFromRequest(request);
		LogHelper.i("\r\n***************************************");
		LogHelper.i("\r\n收到POST的消息：" + messageBody);

		// 处理消息
		String replayMsgStr = "";
		String messageType = MessageTypeUtil.getMessageType(messageBody);
		LogHelper.i("消息类型为：" + messageType);
		if (mWeixinContext == null)
			replayMsgStr = "";
		else
			replayMsgStr = mWeixinContext.handleMsg(messageType, messageBody);

		// 写入消息
		writeResponse(replayMsgStr == null ? "" : replayMsgStr, response);
		LogHelper.i("\r\n返回响应消息：" + replayMsgStr);
	}

	/**
	 * 处理消息
	 * 
	 * @param messageType
	 * @param messageBody
	 * @return
	 */
	private String handleMsg(String messageBody) {
		// 处理消息
		String messageType = MessageTypeUtil.getMessageType(messageBody);
		LogHelper.i("消息类型为：" + messageType);
		if (mWeixinContext == null)
			return null;
		return mWeixinContext.handleMsg(messageType, messageBody);
	}

	private static void writeResponse(String replayMsgStr,
			HttpServletResponse response) {
		replayMsgStr = (replayMsgStr == null) ? "" : replayMsgStr;
		LogHelper.i("\r\n回复消息：" + replayMsgStr);
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(replayMsgStr);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readMessageBodyFromRequest(HttpServletRequest request)
			throws IOException, UnsupportedEncodingException {
		ServletInputStream inStream = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(inStream, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		String tmp;
		while ((tmp = br.readLine()) != null) {
			sb.append(tmp);
		}
		String str = sb.toString();
		br.close();
		isr.close();
		inStream.close();
		return str;
	}

}
