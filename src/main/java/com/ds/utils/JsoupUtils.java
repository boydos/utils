package com.ds.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtils {
	private static final int DEFAULT_TIME_OUNT=5000;
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
	 * getElement　By　Id
	 * @param url
	 * @param id
	 * @return Element
	 */
	public static List<Element> getElementByClass(String url,String className) {
		Document doc = getDocument(url);
		if(doc!=null) {
			return doc.getElementsByClass(className);
		}
		return null;
	}
	public static Element paraseHtml(String html) {
		Document doc= Jsoup.parse(html);
		if(doc!=null) {
			return doc;
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
	public static Document getDocument(String url) {
		Document document=null;
		try {
			document= Jsoup.connect(url).timeout(DEFAULT_TIME_OUNT).get();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("Connected Failed");
		}
		return document;
	}
	/**
	 * post to get document
	 * @param url
	 * @param params
	 * @param headers
	 * @param charset
	 * @return
	 */
	public static Document postDocument(String url,Map<String,String> params,Map<String, String>headers,String charset) {
		Connection con = Jsoup.connect(url).data(params).userAgent("").headers(headers).postDataCharset(charset);
		try {
			return con.post();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("Connected Failed");
		}
		return null;
	}
	public static Response execute(String url,Map<String,String> params,Map<String, String>headers,String charset,Method method) {
		try {
			Response response=Jsoup.connect(url)
					.data(params)
					.userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:55.0) Gecko/20100101 Firefox/55.0")
					.headers(headers)
					.postDataCharset(charset)
					.method(method)
					.execute();
			return response;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("Connected Failed");
		}
		return null;
	}
	
	public static void main(String[] args) {
		String url = "http://www.yidianzixun.com/mp/content?id=39559256";
		/*Response response = execute(url, new HashMap<String, String>(),  new HashMap<String, String>(), "utf-8", Method.GET);
		Map<String, String>headers=response.headers();
		System.out.println(response.body());
		Set<String> keys = headers.keySet();
		for (String key:keys) {
			System.out.println(key+"=="+headers.get(key));
		}*/
		
		System.out.println(getElementById(url, "imedia-article"));
	}
}
