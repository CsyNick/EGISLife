package com.chsy.ngis.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class RSSContnetParser {
	  String Tag = "RSSContnetParser";
		List<NgisEvent> ngisList = new ArrayList<NgisEvent>(); ;
	
	   public RSSContnetParser(List<String> feedList) {
		// TODO Auto-generated constructor stub
		 
		
	}
	public RSSContnetParser() {
		// TODO Auto-generated constructor stub
		prepareData();
		Log.i(Tag, ngisList.size()+"SS");
	}
	   
	   
	   
	   public void parser(){
		   
		   Element link;
		   org.jsoup.nodes.Document doc2;
		
		
				   GetServerMessage serverMessage = new GetServerMessage();
					
					String s=serverMessage.stringQuery("http://ngis.moea.gov.tw/MoeaWeb/Main.aspx?Key=162&Category=Case&IsPreview=Y");
			  doc2 = Jsoup.parse(s);
			   
			    link = doc2.select("meta").get(5);
				String img = link.attr("content");

				Log.i(Tag, img);
			   
			   
			   
			   
		   
		   
	
	   }
	   

	   void prepareData(){
//			headlines = new ArrayList<String>();
//			links = new ArrayList<String>();
			 
			try {
			    URL url = new URL("http://feeds.feedburner.com/moeaWeb/RSS?format=xml");
			 
			    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			    factory.setNamespaceAware(false);
			    XmlPullParser xpp = factory.newPullParser();
			 
			        // We will get the XML from an input stream
			    xpp.setInput(getInputStream(url), "UTF_8");
			 
			        /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
			         * However, we should take in consideration that the rss feed name also is enclosed in a "<title>" tag.
			         * As we know, every feed begins with these lines: "<channel><title>Feed_Name</title>...."
			         * so we should skip the "<title>" tag which is a child of "<channel>" tag,
			         * and take in consideration only "<title>" tag which is a child of "<item>"
			         *
			         * In order to achieve this, we will make use of a boolean variable.
			         */
			    boolean insideItem = false;
			 NgisEvent ngisEvent =null ;
			        // Returns the type of current event: START_TAG, END_TAG, etc..
			    int eventType = xpp.getEventType();
			    while (eventType != XmlPullParser.END_DOCUMENT) {
			        if (eventType == XmlPullParser.START_TAG) {
			        	ngisEvent = new NgisEvent();
			            if (xpp.getName().equalsIgnoreCase("item")) {
			                insideItem = true;
			            } else if (xpp.getName().equalsIgnoreCase("title")) {
			                if (insideItem)
			                	ngisEvent.setTitle(xpp.nextText());
			                	//Log.d(Tag, xpp.nextText());
//			                    headlines.add(xpp.nextText()); //extract the headline
			                    
			            } else if (xpp.getName().equalsIgnoreCase("link")) {
			                if (insideItem)
			                ngisEvent.setUrl_link(xpp.nextText());
			               // Log.d(Tag, xpp.nextText()); //extract the link of article
//			                parser(xpp.nextText()); //extract the link of article
			            }
			            ngisList.add(ngisEvent);
			        }else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
			            insideItem=false;
			        }
			 
			        eventType = xpp.next(); //move to next element
			    }
			 
			} catch (MalformedURLException e) {
			    e.printStackTrace();
			} catch (XmlPullParserException e) {
			    e.printStackTrace();
			} catch (IOException e) {
			    e.printStackTrace();
			}
			    
	   }
		   
		   
			public InputStream getInputStream(URL url) {
				   try {
				       return url.openConnection().getInputStream();
				   } catch (IOException e) {
				       return null;
				     }
				}
	   
	  
	  
}
