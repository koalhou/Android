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

import weixinFundation.core.messages.TextAndImageMessageReply.Article;
import weixinFundation.core.utils.XmlWriteUtil;

class TextAndImageWriter {
	public static String toXml(TextAndImageMessageReply replayMsg) {
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
		// <MsgType><![CDATA[news]]></MsgType>
		// <ArticleCount>2</ArticleCount>
		// <Articles>
		// <item>
		// <Title><![CDATA[title1]]></Title>
		// <Description><![CDATA[description1]]></Description>
		// <PicUrl><![CDATA[picurl]]></PicUrl>
		// <Url><![CDATA[url]]></Url>
		// </item>
		// <item>
		// <Title><![CDATA[title]]></Title>
		// <Description><![CDATA[description]]></Description>
		// <PicUrl><![CDATA[picurl]]></PicUrl>
		// <Url><![CDATA[url]]></Url>
		// </item>
		// </Articles>
		// </xml>
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

		e = doc.createElement("ArticleCount");
		e.appendChild(doc.createCDATASection(replayMsg.ArticleCount+""));
		root.appendChild(e);

		Element eleArticles = doc.createElement("Articles");
		root.appendChild(eleArticles);
		
		for (int i = 0; i <replayMsg.Articles.size(); i++) {
			Element eleItem = doc.createElement("item");
			eleArticles.appendChild(eleItem);
			
			Article article = replayMsg.Articles.get(i);
			
			Element tmp;
			tmp = doc.createElement("Title");
			tmp.appendChild(doc.createCDATASection(article.Title));
			eleItem.appendChild(tmp);
			
			tmp = doc.createElement("Description");
			tmp.appendChild(doc.createCDATASection(article.Description));
			eleItem.appendChild(tmp);
			
			tmp = doc.createElement("PicUrl");
			tmp.appendChild(doc.createCDATASection(article.PicUrl));
			eleItem.appendChild(tmp);
			
			tmp = doc.createElement("Url");
			tmp.appendChild(doc.createCDATASection(article.Url));
			eleItem.appendChild(tmp);
		}

		StringWriter sw = new StringWriter();
		XmlWriteUtil.callDomWriter(doc, sw, "utf-8");
		String xmlRes = sw.getBuffer().toString();
		return xmlRes;
	}

}
