package com.yutong.axxc.parents.view.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.common.beans.MsgRecordBean;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.context.StringUtil;

/**
 * 消息建造帮助类。用于对对历史消息的封装，根据配置文件（消息规则定义），获取对应状态的消息相关信息。
 * 
 * @author zhangyongn
 * 
 */
public class MessageBuilder {

	private static List<curmsgconfig> curmsgconfigs = new ArrayList<curmsgconfig>();
	private static List<msgconfig> msgconfigs = new ArrayList<msgconfig>();

	/**
	 * 构建消息正文模板需要的数据字典
	 * 
	 * @param msg
	 *            消息实体
	 * @param student
	 *            学生信息实体
	 * @return
	 */
	public static Map<String, String> buildDataMap(MsgRecordBean msg,
			StudentInfoBean student) {
		Map<String, String> data = null;
		if (msg != null) {
			data = msg.getContents();
			if (data == null)
				data = new HashMap<String, String>();

			data.put("msg_time", msg.getMsg_time());
			data.put("msg_id", msg.getMsg_id());
			data.put("msg_ruleid", msg.getRule_id());
			data.put("msg_userid", msg.getUsr_id());
		}
		if (student != null)
			data.put("student_name", student.getFullName());

		return data;
	}

	public static Map<String, String> buildDataMap(StudentInfoBean student) {
		Map<String, String> data = new HashMap<String, String>();
		if (student != null)
			data.put("student_name", student.getFullName());
		return data;
	}

	/*--------------------------历史消息----------------------------------*/

	public static msgconfig getMsgConfig(MsgRecordBean msg) {
		if (msg == null)
			return null;
		try {
			if (!isLoaded())
				Load();
			for (int i = 0; i < msgconfigs.size(); i++) {
				if (msg.getRule_id().equals(msgconfigs.get(i).getSrvmsg()))
					return msgconfigs.get(i);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*--------------------------当前状态----------------------------------*/

	/**
	 * 获取当前消息的配置信息
	 * 
	 * @param msg
	 * @param serviceState
	 * @return
	 */
	public static curmsgconfig getCurMsgConfig(String serviceState) {
		String tmpsrv;

		if (serviceState == null) {
			tmpsrv = StudentStateConstant.SRV_UNKNOWN;
		} else {
			tmpsrv = serviceState;
		}
		// tmpsrv = (serviceState == null) ? StudentStateConstant.SRV_UNKNOWN
		// : serviceState;
		if (!isLoaded())
			Load();

		for (int i = 0; i < curmsgconfigs.size(); i++) {
			curmsgconfig map = curmsgconfigs.get(i);
			if (tmpsrv.equals(map.getCurstate())) {

				return map;

			}
		}

		return null;
	}

	/*------------------------------加载相关----------------------------------------*/
	public static void Load() {
		// 这里从asset读取xml，加载配置信息。

		String filename = "msgconfig.xml";
		loadFromXml(filename);

	}

	public static void loadFromXml(String fileName) {

		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document document = null;
		InputStream inputStream = null;
		// 首先找到xml文件
		factory = DocumentBuilderFactory.newInstance();
		try {
			// 找到xml，并加载文档
			builder = factory.newDocumentBuilder();
			inputStream = YtApplication.getInstance().getApplicationContext()
					.getResources().getAssets().open(fileName);
			document = builder.parse(inputStream);
			// 找到根Element
			Element root = document.getDocumentElement();
			/*--------------------------获取msgs----------------------------------*/
			NodeList nodes = root.getElementsByTagName("msg");

			msgconfig m = null;
			for (int i = 0; i < nodes.getLength(); i++) {
				m = new msgconfig();

				Element ele = (Element) (nodes.item(i));

				m.setCall(Boolean.parseBoolean(ele.getAttribute("call")));
				m.setDot(Integer.parseInt(ele.getAttribute("dot")));
				m.setGps(Boolean.parseBoolean(ele.getAttribute("gps")));
				m.setSrvmsg(ele.getAttribute("srvmsg"));

				// 获取body标签
				Element introduction = (Element) ele.getElementsByTagName(
						"body").item(0);
				m.setBody(StringUtil.replaceBlank(introduction.getFirstChild()
						.getNodeValue()));

				msgconfigs.add(m);
			}
			/*--------------------------获取curmsgs----------------------------------*/
			nodes = root.getElementsByTagName("curmsg");
			// 遍历根节点所有子节点,rivers 下所有river
			curmsgconfig cur = null;
			for (int i = 0; i < nodes.getLength(); i++) {
				cur = new curmsgconfig();
				// 获取元素节点
				Element ele = (Element) (nodes.item(i));
				// 获取river中name属性值
				cur.setCall(Boolean.parseBoolean(ele.getAttribute("call")));
				cur.setGps(Boolean.parseBoolean(ele.getAttribute("gps")));
				cur.setBusdetail(Boolean.parseBoolean(ele
						.getAttribute("busdetail")));
				cur.setCurstate(ele.getAttribute("curstate"));

				// 获取body标签
				Element body = (Element) ele.getElementsByTagName("body").item(
						0);
				cur.setBody(StringUtil.replaceBlank(body.getFirstChild()
						.getNodeValue()));

				curmsgconfigs.add(cur);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @return the loaded
	 */
	public static boolean isLoaded() {
		if (curmsgconfigs == null || curmsgconfigs.size() <= 0)
			return false;
		if (msgconfigs == null || msgconfigs.size() <= 0)
			return false;
		return true;
	}

	public static class msgmap implements Serializable {
		private static final long serialVersionUID = 1L;
		private String srvmsg;
		private String curstate;

		/**
		 * @return the srvmsg
		 */
		public String getSrvmsg() {
			return srvmsg;
		}

		/**
		 * @param srvmsg
		 *            the srvmsg to set
		 */
		public void setSrvmsg(String srvmsg) {
			this.srvmsg = srvmsg;
		}

		/**
		 * @return the curstate
		 */
		public String getCurstate() {
			return curstate;
		}

		/**
		 * @param curstate
		 *            the curstate to set
		 */
		public void setCurstate(String curstate) {
			this.curstate = curstate;
		}
	}

	/**
	 * 当前 状态的消息模板配置
	 * 
	 * @author zhangyongn
	 * 
	 */
	public static class curmsgconfig implements Serializable {
		private static final long serialVersionUID = 1L;
		private boolean call;
		private boolean gps;
		private boolean busdetail;

		private String curstate;

		private String body;

		/**
		 * @return the call
		 */
		public boolean isCall() {
			return call;
		}

		/**
		 * @param call
		 *            the call to set
		 */
		public void setCall(boolean call) {
			this.call = call;
		}

		/**
		 * @return the gps
		 */
		public boolean isGps() {
			return gps;
		}

		/**
		 * @param gps
		 *            the gps to set
		 */
		public void setGps(boolean gps) {
			this.gps = gps;
		}

		/**
		 * @return the busdetail
		 */
		public boolean isBusdetail() {
			return busdetail;
		}

		/**
		 * @param busdetail
		 *            the busdetail to set
		 */
		public void setBusdetail(boolean busdetail) {
			this.busdetail = busdetail;
		}

		/**
		 * @return the curstate
		 */
		public String getCurstate() {
			return curstate;
		}

		/**
		 * @param curstate
		 *            the curstate to set
		 */
		public void setCurstate(String curstate) {
			this.curstate = curstate;
		}

		/**
		 * @return the body
		 */
		public String getBody() {
			return body;
		}

		/**
		 * @param body
		 *            the body to set
		 */
		public void setBody(String body) {
			this.body = body;
		}

	}

	/**
	 * 历史消息文本配置实体
	 * 
	 * @author zhangyongn
	 * 
	 */
	public static class msgconfig implements Serializable {
		private static final long serialVersionUID = 1L;
		private boolean call;
		private boolean gps;
		private int dot;

		private String srvmsg;
		private String body;

		/**
		 * @return the call
		 */
		public boolean isCall() {
			return call;
		}

		/**
		 * @param call
		 *            the call to set
		 */
		public void setCall(boolean call) {
			this.call = call;
		}

		/**
		 * @return the gps
		 */
		public boolean isGps() {
			return gps;
		}

		/**
		 * @param gps
		 *            the gps to set
		 */
		public void setGps(boolean gps) {
			this.gps = gps;
		}

		/**
		 * @return the dot
		 */
		public int getDot() {
			return dot;
		}

		/**
		 * @param dot
		 *            the dot to set
		 */
		public void setDot(int dot) {
			this.dot = dot;
		}

		/**
		 * @return the srvmsg
		 */
		public String getSrvmsg() {
			return srvmsg;
		}

		/**
		 * @param srvmsg
		 *            the srvmsg to set
		 */
		public void setSrvmsg(String srvmsg) {
			this.srvmsg = srvmsg;
		}

		/**
		 * @return the body
		 */
		public String getBody() {
			return body;
		}

		/**
		 * @param body
		 *            the body to set
		 */
		public void setBody(String body) {
			this.body = body;
		}
	}

}
