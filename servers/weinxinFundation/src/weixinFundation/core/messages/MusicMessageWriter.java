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

class MusicMessageWriter {
	public static String toXml(MusicMessageReply replayMsg) {
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
		// <xml>
		// <ToUserName><![CDATA[toUser]]></ToUserName>
		// <FromUserName><![CDATA[fromUser]]></FromUserName>
		// <CreateTime>12345678</CreateTime>
		// <MsgType><![CDATA[music]]></MsgType>
		// <Music>
		// <Title><![CDATA[TITLE]]></Title>
		// <Description><![CDATA[DESCRIPTION]]></Description>
		// <MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>
		// <HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl>
		// <ThumbMediaId><![CDATA[media_id]]></ThumbMediaId>
		// </Music>
		// </xml>

		e = XmlWriteUtil.createElementWithCDATAValue("ToUserName",
				replayMsg.ToUserName, doc);
		root.appendChild(e);

		e = XmlWriteUtil.createElementWithCDATAValue("FromUserName",
				replayMsg.FromUserName, doc);
		root.appendChild(e);

		e = doc.createElement("CreateTime");
		e.setTextContent(replayMsg.CreateTime);
		root.appendChild(e);

		XmlWriteUtil.appendElementWithCDATAValue("MsgType", replayMsg.MsgType,
				doc, root);

		Element musicElement = doc.createElement("Music");
		root.appendChild(musicElement);
		e = doc.createElement("Title");
		e.appendChild(doc.createCDATASection(replayMsg.Music.Title));
		musicElement.appendChild(e);

		e = doc.createElement("Description");
		e.appendChild(doc.createCDATASection(replayMsg.Music.Description));
		musicElement.appendChild(e);

		if (replayMsg.Music.MusicUrl != null
				&& !"".equals(replayMsg.Music.MusicUrl)) {
			//e = doc.createElement("MusicURL");
			e = doc.createElement("MusicUrl");
			e.appendChild(doc.createCDATASection(replayMsg.Music.MusicUrl));
			musicElement.appendChild(e);
		}

		if (replayMsg.Music.HQMusicUrl != null
				&& !"".equals(replayMsg.Music.HQMusicUrl)) {
			e = doc.createElement("HQMusicUrl");
			e.appendChild(doc.createCDATASection(replayMsg.Music.HQMusicUrl));
			musicElement.appendChild(e);
		}

		if (replayMsg.Music.ThumbMediaId != null
				&& !"".equals(replayMsg.Music.ThumbMediaId)) {
			e = doc.createElement("ThumbMediaId");
			e.appendChild(doc.createCDATASection(replayMsg.Music.ThumbMediaId));
			musicElement.appendChild(e);
		}

		StringWriter sw = new StringWriter();
		XmlWriteUtil.callDomWriter(doc, sw, "utf-8");
		String xmlRes = sw.getBuffer().toString();
		return xmlRes;
	}

}
