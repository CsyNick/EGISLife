package com.chsy.ngis.rss;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

public class NgisRSSParser {
	final int FEEDNUM = 10;

	public NgisRSSParser() {
		// TODO Auto-generated constructor stub
	}

	public String parser(String html) {

		org.jsoup.nodes.Element link;
		org.jsoup.nodes.Document doc2;

		GetServerMessage serverMessage = new GetServerMessage();

		String s = serverMessage.stringQuery(html);
		doc2 = Jsoup.parse(s);

		link = doc2.select("meta").get(5);
		String img = link.attr("content");

		Log.i("NgisRSSParser", img);
		return img;

	}

	List<NgisEvent> NgisRSSList = new ArrayList<NgisEvent>();

	public List<NgisEvent> getInfo() {
		GetServerMessage serverMessage = new GetServerMessage();
		String msg = serverMessage
				.stringQuery("http://feeds.feedburner.com/moeaWeb/RSS");

		Document doc = null;
		try {
			InputStream inputStream = new ByteArrayInputStream(msg.getBytes());
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(inputStream);
			// Element root = doc.getDocumentElement();
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("item");
			// NodeList nodeList=nList.getElementsByTagName("title");
			NgisEvent event = new NgisEvent();
			for (int temp = 0; temp < FEEDNUM; temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					String titile = eElement.getElementsByTagName("title")
							.item(0).getTextContent();
					String url_link = eElement.getElementsByTagName("link")
							.item(0).getTextContent();
					String pubDate = eElement.getElementsByTagName("pubDate")
							.item(0).getTextContent();
					Log.i("NgisRSSParser", titile + "," + url_link + ","
							+ pubDate);
					event = new NgisEvent();
					event.setTitle(titile);
					event.setUrl_link(url_link);
					event.setPubData(pubDate);
					event.setImage(parser(url_link));
					NgisRSSList.add(event);
				}
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ParserConfigurationException e) {

			e.printStackTrace();

		} catch (SAXException e) {

			e.printStackTrace();
		}

		return NgisRSSList;

	}

}
