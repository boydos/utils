package com.ds.html;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtils {
	private static final int DEFAULT_TIME_OUNT=3000;
	public static Document get(String url) {
		try {
			Document doc = Jsoup.connect(url).timeout(DEFAULT_TIME_OUNT).get();
			return doc;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Document post(String url,Map<String,String> params,Map<String, String>headers,String charset) {
		Connection con = Jsoup.connect(url).data(params).userAgent("").headers(headers).postDataCharset(charset);
		try {
			return con.post();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
