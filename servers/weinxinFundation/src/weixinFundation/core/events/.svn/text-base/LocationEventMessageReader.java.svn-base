package weixinFundation.core.events;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import weixinFundation.core.utils.LogHelper;

public class LocationEventMessageReader {
	private static final String TAG = "LocationEventMessageReader";

	public static LocationEventMessage fromXml(String xmlStr) {
		//Latitude	 地理位置纬度
		//Longitude	 地理位置经度
		//Precision	 地理位置精度
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
				String Event = rootElement.getElementsByTagName("Event")
						.item(0).getTextContent();
				
				String Latitude = rootElement.getElementsByTagName("Latitude")
						.item(0).getTextContent();
				String Longitude = rootElement.getElementsByTagName("Longitude")
						.item(0).getTextContent();
				String Precision = rootElement.getElementsByTagName("Precision")
						.item(0).getTextContent();

				LocationEventMessage bean;
				bean = new LocationEventMessage();
				bean.ToUserName = ToUserName;
				bean.FromUserName = FromUserName;
				bean.CreateTime = CreateTime;
				bean.MsgType = MsgType;
				bean.Event = Event;
				bean.Latitude = Latitude;
				bean.Longitude = Longitude;
				bean.Precision = Precision;

				return bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogHelper.i(TAG+"解析消息错误Error：" + e.getMessage());
		}
		return null;
	}

}
