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

class TextMessageReader {

	public static TextMessage readTextMessage(String xmlStr) {
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource inputSource = new InputSource(new StringReader(
					xmlStr));
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
				String Content = rootElement.getElementsByTagName("Content")
						.item(0).getTextContent();
				String MsgId = rootElement.getElementsByTagName("MsgId")
						.item(0).getTextContent();

				TextMessage bean;
				bean = new TextMessage();
				bean.ToUserName = ToUserName;
				bean.FromUserName = FromUserName;
				bean.CreateTime = CreateTime;
				bean.MsgType = MsgType;
				bean.Content = Content;
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
