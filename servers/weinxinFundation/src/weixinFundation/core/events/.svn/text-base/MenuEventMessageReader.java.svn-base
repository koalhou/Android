package weixinFundation.core.events;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import weixinFundation.core.utils.LogHelper;

class MenuEventMessageReader {
	private static final String TAG = "MenuEventMessageReader";

	public static MenuEventMessage fromXml(String xmlStr) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource inputSource = new InputSource(new StringReader(xmlStr));
			inputSource.setEncoding("utf-8");
			Document doc = builder.parse(inputSource);

			NodeList nl = doc.getElementsByTagName("xml");
			if (nl != null && nl.getLength() > 0) {
				Element rootElement = (Element) nl.item(0);
				String ToUserName = rootElement.getElementsByTagName("ToUserName").item(0).getTextContent();
				String FromUserName = rootElement.getElementsByTagName("FromUserName").item(0).getTextContent();
				String CreateTime = rootElement.getElementsByTagName("CreateTime").item(0).getTextContent();
				String MsgType = rootElement.getElementsByTagName("MsgType").item(0).getTextContent();
				String Event = rootElement.getElementsByTagName("Event").item(0).getTextContent();
				String EventKey = rootElement.getElementsByTagName("EventKey").item(0).getTextContent();

				MenuEventMessage bean;
				bean = new MenuEventMessage();
				bean.ToUserName = ToUserName;
				bean.FromUserName = FromUserName;
				bean.CreateTime = CreateTime;
				bean.MsgType = MsgType;
				bean.Event = Event;
				bean.EventKey = EventKey;

				return bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogHelper.i(TAG + "解析消息错误Error：" + e.getMessage());
		}
		return null;
	}

}
