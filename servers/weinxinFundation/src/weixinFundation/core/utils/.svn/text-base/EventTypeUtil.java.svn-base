package weixinFundation.core.utils;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class EventTypeUtil {

	/**
	 * 获得 event类型
	 * @param xmlStr
	 * @return
	 */
	public static String getEventType(String xmlStr) {
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
				//必须是event类型
				if("event".equals(MsgType)){
					//MsgType
					String event = rootElement.getElementsByTagName("Event")
							.item(0).getTextContent();
					return event;
				}else{
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogHelper.e("\r\nError：" + e.getMessage());
		}
		return null;
	}

}
