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

class TextMessageReplyWriter {

	/**
	 * <xml> <ToUserName><![CDATA[toUser]]></ToUserName>
	 * <FromUserName><![CDATA[fromUser]]></FromUserName>
	 * <CreateTime>12345678</CreateTime> <MsgType><![CDATA[text]]></MsgType>
	 * <Content><![CDATA[你好]]></Content> </xml>
	 * 
	 * @param replayMsg
	 * @return
	 */
	public static String toXml(TextMessageReply replayMsg) {
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
		e.appendChild(doc.createCDATASection(replayMsg.ToUserName));
		root.appendChild(e);

		e = doc.createElement("FromUserName");
		e.appendChild(doc.createCDATASection(replayMsg.FromUserName));
		root.appendChild(e);

		e = doc.createElement("CreateTime");
		e.setTextContent(replayMsg.CreateTime);
		root.appendChild(e);

		e = doc.createElement("MsgType");
		e.appendChild(doc.createCDATASection(replayMsg.MsgType));
		root.appendChild(e);

		e = doc.createElement("Content");
		e.appendChild(doc.createCDATASection(replayMsg.Content));
		root.appendChild(e);

		StringWriter sw = new StringWriter();
		XmlWriteUtil.callDomWriter(doc, sw, "utf-8");
		String xmlRes = sw.getBuffer().toString();
		return xmlRes;
	}


}
