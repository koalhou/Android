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

class VideoMessageReader {

	public static VideoMessage readTextMessage(String xmlStr) {
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
				String MediaId = rootElement.getElementsByTagName("MediaId")
						.item(0).getTextContent();
				String ThumbMediaId = rootElement
						.getElementsByTagName("ThumbMediaId").item(0)
						.getTextContent();
				String MsgId = rootElement.getElementsByTagName("MsgId")
						.item(0).getTextContent();
				// ToUserName 开发者微信号
				// FromUserName 发送方帐号（一个OpenID）
				// CreateTime 消息创建时间 （整型）
				// MsgType 视频为video
				// MediaId 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
				// ThumbMediaId 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
				// MsgId 消息id，64位整型
				VideoMessage bean;
				bean = new VideoMessage();
				bean.ToUserName = ToUserName;
				bean.FromUserName = FromUserName;
				bean.CreateTime = CreateTime;
				bean.MsgType = MsgType;
				bean.MediaId = MediaId;
				bean.ThumbMediaId = ThumbMediaId;
				bean.MsgId = MsgId;

				return bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogHelper.e("Error：" + e.getMessage());
		}
		return null;
	}
}
