package weixinFundation.core.utils;

import java.io.*;
import java.net.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

/**
 * 参考：http://jingyan.baidu.com/article/19192ad84e6269e53e570788.html
 * 
 * @author yunfei
 * 
 */
public class HttpsUtil {
	/**
	 * 
	 * 发起https请求并获取结果
	 * 
	 * 
	 * 
	 * @param requestUrl
	 *            请求地址
	 * 
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * 
	 * @param outputStr
	 *            提交的数据
	 * 
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */

	public static String get(String requestUrl) {
		return requestURL(requestUrl, "GET", null);
	}
	
	public static String post(String requestUrl,
			String content) {
		return requestURL(requestUrl, "POST", content);
	}

	private static String requestURL(String requestUrl, String requestMethod,
			String content) {
		LogHelper.i("准备访问：" + requestUrl);
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != content) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(content.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			bufferedReader.close();
			inputStreamReader.close();

			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			String res = buffer.toString();

			LogHelper.i(String.format("请求ＵＲＬ=%s,收到返回结果：%s", requestUrl, res));
			return res;

		} catch (ConnectException ce) {
			ce.printStackTrace();
			LogHelper.e("Weixin server connection timed out.");
		} catch (Exception e) {
			e.printStackTrace();
			LogHelper.e("https request error:{}", e);
		}
		return null;
	}

	public static class MyX509TrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {

		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {

		}

		public X509Certificate[] getAcceptedIssuers() {

			return null;

		}
	}
}
