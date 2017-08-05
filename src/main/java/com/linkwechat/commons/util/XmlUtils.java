package com.linkwechat.commons.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Xml工具类
 * 
 * @author linkwechat linkwechat@foxmail.com
 * @version 1.0
 */
public class XmlUtils {

    private static final Log logger = LogFactory.getLog(XmlUtils.class);

    /**
     * 默认字符集
     */
    private static String defaultCharset = "UTF-8";

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML
     *            XML字符串
     * @return XML数据转换后的Map
     */
    public static Map<String, String> xmlToMap(String strXML) {
        return xmlToMap(strXML, defaultCharset);
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML
     *            XML字符串
     * @param charset
     *            数据编码
     * @return XML数据转换后的Map
     */
    public static Map<String, String> xmlToMap(String strXML, String charset) {
        InputStream stream = null;
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            stream = new ByteArrayInputStream(strXML.getBytes(charset));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            return data;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data
     *            Map类型数据
     * @return XML格式的字符串
     */
    public static String mapToXml(Map<String, String> data) {
        return mapToXml(data, defaultCharset);
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data
     *            Map类型数据
     * @param charset
     *            数据编码
     * @return XML格式的字符串
     */
    public static String mapToXml(Map<String, String> data, String charset) {
        StringWriter writer = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document document = documentBuilder.newDocument();
            org.w3c.dom.Element root = document.createElement("xml");
            document.appendChild(root);
            for (String key : data.keySet()) {
                String value = data.get(key);
                if (value == null) {
                    value = "";
                }
                value = value.trim();
                org.w3c.dom.Element filed = document.createElement(key);
                filed.appendChild(document.createTextNode(value));
                root.appendChild(filed);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, charset);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            String output = writer.getBuffer().toString();
            return output;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}
