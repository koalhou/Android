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

class GeographyReader {

	public static GeographyMessage readTextMessage(String xmlStr) {
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
				String Location_X = rootElement
						.getElementsByTagName("Location_X").item(0)
						.getTextContent();
				String Location_Y = rootElement
						.getElementsByTagName("Location_Y").item(0)
						.getTextContent();
				String Scale = rootElement.getElementsByTagName("Scale")
						.item(0).getTextContent();
				String Label = rootElement.getElementsByTagName("Label")
						.item(0).getTextContent();
				String MsgId = rootElement.getElementsByTagName("MsgId")
						.item(0).getTextContent();
				// ToUserName 开发者微信号
				// FromUserName 发送方帐号（一个OpenID）
				// CreateTime 消息创建时间 （整型）
				// MsgType location
				// Location_X 地理位置维度
				// Location_Y 地理位置经度
				// Scale 地图缩放大小
				// Label 地理位置信息
				// MsgId 消息id，64位整型
				GeographyMessage bean;
				bean = new GeographyMessage();
				bean.ToUserName = ToUserName;
				bean.FromUserName = FromUserName;
				bean.CreateTime = CreateTime;
				bean.MsgType = MsgType;
				bean.Location_X = Location_X;
				bean.Location_Y = Location_Y;
				bean.Scale = Scale;
				bean.Label = Label;
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
