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

class VoiceMessageReader {

	public static VoiceMessage readTextMessage(String xmlStr) {
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
				String Format = rootElement.getElementsByTagName("Format")
						.item(0).getTextContent();
				String MsgID = rootElement.getElementsByTagName("MsgID")
						.item(0).getTextContent();
				// ToUserName 开发者微信号
				// FromUserName 发送方帐号（一个OpenID）
				// CreateTime 消息创建时间 （整型）
				// MsgType 语音为voice
				// MediaId 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
				// Format 语音格式，如amr，speex等
				// MsgID 消息id，64位整型
				VoiceMessage bean;
				bean = new VoiceMessage();
				bean.ToUserName = ToUserName;
				bean.FromUserName = FromUserName;
				bean.CreateTime = CreateTime;
				bean.MsgType = MsgType;
				bean.MediaId = MediaId;
				bean.Format = Format;
				bean.MsgID = MsgID;

				return bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogHelper.e("Error：" + e.getMessage());
		}
		return null;
	}
}
