package com.ds.html;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ds.utils.Log;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class HtmlUnit {
	static WebClient webClient;
	public static WebClient getClient() {
		return getClient(new HashMap<String, String>());
	}
	public static WebClient getClient(Map<String,String> map) {
		if(webClient ==null) {
			webClient = new WebClient(BrowserVersion.FIREFOX_38);
			webClient.setJavaScriptTimeout(5000);  
			webClient.getOptions().setUseInsecureSSL(true);//接受任何主机连接 无论是否有有效证书  
			webClient.getOptions().setJavaScriptEnabled(true);//设置支持javascript脚本   
			webClient.getOptions().setCssEnabled(false);//禁用css支持  
			webClient.getOptions().setThrowExceptionOnScriptError(false);//js运行错误时不抛出异常  
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setTimeout(100000);//设置连接超时时间  
			webClient.getOptions().setDoNotTrackEnabled(false);
			webClient.getCookieManager().setCookiesEnabled(true);
			if(map !=null && !map.isEmpty()) {
				Set<String>keys = map.keySet();
				for(String key :keys) {
					webClient.addRequestHeader(key, map.get(key));
				}
			}
			
		}
		return webClient;
	}
	public static Set<Cookie> getCookie(String url) {
		getHtml(url);
		if(webClient!=null) {
		 CookieManager CM = webClient.getCookieManager(); //WC = Your WebClient's name  
         return CM.getCookies();
		}
		return null;
	}
	public static Set<Cookie> getCookie() {
		if(webClient!=null) {
		 CookieManager CM = webClient.getCookieManager(); //WC = Your WebClient's name  
         return CM.getCookies();
		}
		return null;
	}
	public static String getHtml(String url) {
		return getHtml(url,new HashMap<String, String>());
	}
	public static String getHtml(String url,Map<String,String> map) {
		WebClient client = getClient(map);
		try {
			HtmlPage page = webClient.getPage(url);
			return page.asXml();
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close(client);
		return null;
	}
	public static String getText(String url) {
		WebClient client = getClient();
		try {
			HtmlPage page = webClient.getPage(url);
			return page.asText();
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close(client);
		return null;
	}
	public static void close(WebClient ...clbs) {
		try {
			if(clbs!=null){
				for(WebClient clb:clbs ) {
					if(clb!=null)clb.close();
				}
			};
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String [] args) {
		
		Log.d(getText("http://www.yidianzixun.com/mp/content?id=39559256"));
		Log.d(""+System.currentTimeMillis());
	}
}
