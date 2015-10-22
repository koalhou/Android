package weixinFundation.core.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerifyMessageUtil {
	private String token;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public VerifyMessageUtil() {
		super();
	}

	public VerifyMessageUtil(String token) {
		super();
		this.token = token;
	}

	/**
	 * 验证 消息请求 是否 合法
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void rerifyRequest(HttpServletRequest request, HttpServletResponse response)throws IOException {
		if(token == null)
			throw new IllegalArgumentException("未指定要验证的token");
		/**
		 * 第二步：验证URL有效性
		 * 
		 * 开发者提交信息后，微信服务器将发送GET请求到填写的URL上，GET请求携带四个参数：
		 * 
		 * 参数 描述 signature
		 * 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。 timestamp
		 * 时间戳 nonce 随机数 echostr 随机字符串
		 * 开发者通过检验signature对请求进行校验（下面有校验方式）。若确认此次GET请求来自微信服务器
		 * ，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。
		 * 
		 * 加密/校验流程如下： 1. 将token、timestamp、nonce三个参数进行字典序排序 2.
		 * 将三个参数字符串拼接成一个字符串进行sha1加密 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		 * 检验signature的PHP示例代码：
		 * 
		 * 
		 */
		Logger logger = Logger.getLogger("pdwy");
		logger.log(Level.INFO, "日志开始");

		String signature = request.getParameter("signature");
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		String echostr = request.getParameter("echostr");

		if(signature == null && nonce == null && timestamp== null && echostr== null){
			logger.log(Level.INFO, "参数不全");
			response.sendError(500,"参数不全");
			return;
		}
		
//		logger.log(Level.INFO, String.format(
//				"收到：signature=%s,nonce=%s,timestamp=%s,echostr=%s", signature,
//				nonce, timestamp, echostr));
		if (null == signature || "".equals(signature)) {
			response.sendError(500);
			return;
		}

		String[] arrs = new String[] { token, timestamp, nonce };
		Arrays.sort(arrs);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arrs.length; i++) {
			sb.append(arrs[i]);
		}
		String newSignature = sha1(sb.toString());
		if (signature.equals(newSignature)) {
			PrintWriter pw = response.getWriter();
			pw.write(echostr);
			pw.close();
		}
	}

	private static String sha1(String content) {
		// 首先用生成一个MessageDigest类,确定计算方法
		java.security.MessageDigest alga;
		try {
			alga = java.security.MessageDigest.getInstance("SHA");
			// 添加要进行计算摘要的信息
			alga.update(content.getBytes());
			// 计算出摘要
			byte[] digesta = alga.digest();
			return byteArrayToHexString(digesta);

			// String s1 = alga.digest().toString();
			// return s1;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String byteArrayToHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
}
