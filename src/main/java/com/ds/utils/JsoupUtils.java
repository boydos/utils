package com.ds.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtils {
	/**
	 * parse Element　to String
	 * @param element
	 * @return
	 */
	public static String getElementString(Element element) {
		return element!=null?element.toString():"";
	}
	/**
	 * getElement　By　Id
	 * @param url
	 * @param id
	 * @return Element
	 */
	public static Element getElementById(String url,String id) {
		Document doc = getDocument(url);
		if(doc!=null) {
			return doc.getElementById(id);
		}
		return null;
	}
	/**
	 * get images url from element
	 * @param element
	 * @return
	 */
	public static List<String> getElementImages(Element element) {
		List<String> list = new ArrayList<String>();
		Elements elements=element.getElementsByTag("img");
		for(Element el : elements) {
			String src = el.attr("src");
			if(!StringUtils.isEmpty(src))list.add(src);
		}
		return list;
	}
	/**
	 * get Docment
	 * @param url
	 * @return
	 */
	private static Document getDocument(String url) {
		Document document=null;
		try {
			document= Jsoup.connect(url).timeout(9000).get();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("Connected Failed");
		}
		return document;
	}
}
