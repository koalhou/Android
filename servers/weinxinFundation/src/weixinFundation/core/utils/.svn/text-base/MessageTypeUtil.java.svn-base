package weixinFundation.core.utils;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class MessageTypeUtil {
	/**
	 * 获得消息类型
	 * 
	 * @param xmlStr
	 * @return
	 */
	public static String getMessageType(String xmlStr) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(
					xmlStr)));
			NodeList nl = doc.getElementsByTagName("xml");
			if (nl != null && nl.getLength() > 0) {
				Element rootElement = (Element) nl.item(0);
				String MsgType = rootElement.getElementsByTagName("MsgType")
						.item(0).getTextContent();
				return MsgType;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogHelper.e("\r\nError：" + e.getMessage());
		}
		return null;
	}
}
