package weixinFundation.core.messages;

import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import weixinFundation.core.utils.XmlWriteUtil;

class ImageMessageWriter {
	// <xml>
	// <ToUserName><![CDATA[toUser]]></ToUserName>
	// <FromUserName><![CDATA[fromUser]]></FromUserName>
	// <CreateTime>12345678</CreateTime>
	// <MsgType><![CDATA[image]]></MsgType>
	// <Image>
	// <MediaId><![CDATA[media_id]]></MediaId>
	// </Image>
	// </xml>
	public static String toXml(ImageMessageReply replayMsg) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbuilder = null;
		try {
			dbuilder = dbf.newDocumentBuilder();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Document doc = dbuilder.newDocument();
		Element root = doc.createElement("xml");
		doc.appendChild(root);

		Element e;

		e = doc.createElement("ToUserName");
		e.setTextContent(replayMsg.ToUserName);
		root.appendChild(e);

		e = doc.createElement("FromUserName");
		e.setTextContent(replayMsg.FromUserName);
		root.appendChild(e);

		e = doc.createElement("CreateTime");
		e.setTextContent(replayMsg.CreateTime);
		root.appendChild(e);

		e = doc.createElement("MsgType");
		e.setTextContent(replayMsg.MsgType);
		root.appendChild(e);
		
		Element e1 = doc.createElement("Image");
		root.appendChild(e);
		e = doc.createElement("MediaId");
		e.appendChild(doc.createCDATASection(replayMsg.MediaId));
		e1.appendChild(e);

		StringWriter sw = new StringWriter();
		XmlWriteUtil.callDomWriter(doc, sw, "utf-8");
		String xmlRes = sw.getBuffer().toString();
		return xmlRes;
	}

}
