package weixinFundation.core.utils;

import java.io.Writer;

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

public class XmlWriteUtil {
	/**
	 * 创建一个 element,使用 elementName，并且插入一个值为cdataValue 的 CDATA [CDATASection]的子节点
	 * 
	 * @param elementName
	 * @param cdataValue
	 * @param doc
	 * @return
	 */
	public static Element createElementWithCDATAValue(String elementName,
			String cdataValue, Document doc) {
		Element e;
		e = doc.createElement(elementName);
		e.appendChild(doc.createCDATASection(cdataValue));
		return e;
	}

	/**
	 * 创建一个 element,使用 elementName，并且插入一个值为cdataValue 的 CDATA [CDATASection]的子节点
	 * 
	 * @param elementName
	 * @param cdataValue
	 * @param doc
	 * @return
	 */
	public static void appendElementWithCDATAValue(String elementName,
			String cdataValue, Document doc, Element parent) {
		Element e;
		e = doc.createElement(elementName);
		e.appendChild(doc.createCDATASection(cdataValue));
		parent.appendChild(e);
	}

	/**
	 * 创建一个 element,使用 elementName，并且插入一个值为value 的 TextNode的子节点
	 * 
	 * @param elementName
	 * @param value
	 * @param doc
	 * @return
	 */
	public static Element createElementWithTextValue(String elementName,
			String value, Document doc) {
		Element e;
		e = doc.createElement(elementName);
		e.appendChild(doc.createTextNode(value));
		return e;
	}

	/**
	 * 将 Document 的内容 写入到 writer
	 * 
	 * @param doc
	 * @param writer
	 * @param encoding
	 */
	public static void callDomWriter(Document doc, Writer writer,
			String encoding) {
		Source source = new DOMSource(doc);
		Result res = new StreamResult(writer);
		Transformer xformer;
		try {
			xformer = TransformerFactory.newInstance().newTransformer();
			xformer.setOutputProperty(OutputKeys.ENCODING, encoding);
			xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			try {
				xformer.transform(source, res);
			} catch (TransformerException e) {
				e.printStackTrace();
			}
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}

	}
}
