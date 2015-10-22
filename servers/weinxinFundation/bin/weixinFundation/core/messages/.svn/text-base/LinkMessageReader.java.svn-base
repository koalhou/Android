package weixinFundation.core.messages;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import weixinFundation.core.utils.LogHelper;

class LinkMessageReader {

	public static LinkMessage readTextMessage(String xmlStr) {
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource inputSource = new InputSource(new StringReader(xmlStr));
			inputSource.setEncoding("utf-8");
			Document doc = builder.parse(inputSource);

			NodeList nl = doc.getElementsByTagName("xml");
			if (nl != null && nl.getLength() > 0) {
				Element rootElement = (Element) nl.item(0);
				String ToUserName = rootElement
						.getElementsByTagName("ToUserName").item(0)
						.getTextContent();
				String FromUserName = rootElement
						.getElementsByTagName("FromUserName").item(0)
						.getTextContent();
				String CreateTime = rootElement
						.getElementsByTagName("CreateTime").item(0)
						.getTextContent();
				String MsgType = rootElement.getElementsByTagName("MsgType")
						.item(0).getTextContent();
				String Title = rootElement.getElementsByTagName("Title")
						.item(0).getTextContent();
				String Description = rootElement
						.getElementsByTagName("Description").item(0)
						.getTextContent();
				String Url = rootElement.getElementsByTagName("Url").item(0)
						.getTextContent();
				String MsgId = rootElement.getElementsByTagName("MsgId")
						.item(0).getTextContent();
				// ToUserName 接收方微信号
				// FromUserName 发送方微信号，若为普通用户，则是一个OpenID
				// CreateTime 消息创建时间
				// MsgType 消息类型，link
				// Title 消息标题
				// Description 消息描述
				// Url 消息链接
				// MsgId 消息id，64位整型
				LinkMessage bean;
				bean = new LinkMessage();
				bean.ToUserName = ToUserName;
				bean.FromUserName = FromUserName;
				bean.CreateTime = CreateTime;
				bean.MsgType = MsgType;
				bean.Title = Title;
				bean.Description = Description;
				bean.Url = Url;
				bean.MsgId = MsgId;

				return bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogHelper.e( "Error：" + e.getMessage());
		}
		return null;
	}
}
