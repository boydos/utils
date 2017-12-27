package com.ds.utils;

import java.util.HashMap;
import java.util.Map;

import com.ds.html.HtmlUnit;

public class TouTiaoTest {

	public static void main(String [] args) {
		String id="6492959834042794509";
		String url ="http://m.pstatp.com/item/"+id+"/?app=news_article&utm_medium=toutiao_ios&iid=";
		//String url ="http://m.toutiao.com/i"+id+"/?app=news_article&utm_medium=toutiao_ios&iid=";
		//String url="http://m.toutiao.com/i"+id+"/ad/";
		Map<String,String> headers = new HashMap<String,String>();
		//headers.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 10_2 like Mac OS X) AppleWebKit/602.3.12 (KHTML, like Gecko) Mobile/14C92 MicroMessenger/6.5.9 NetType/WIFI Language/zh_CN");
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		/*headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "en-US,en;q=0.5");*/
		//headers.put("Host", "m.pstatp.com");
		//headers.put("Cookie", "JSESSIONID=6b25c0d7e8517d949899f7e7348f373a6e1b4b8f63f161b6b17791acb2774cc2;wuid=123456712345678; wuid_createAt=2017-11-15 19:07:03; weather_auth=2;"+cookie);
		headers.put("Upgrade-Insecure-Requests", "1");
		headers.put("Connection", "keep-alive");
		//183519270603345
		
      	//tt_webid=6488617505334167053; uuid="w:2c0ec768882949ba895fcc288f24d5c8"; sso_login_status=1; login_flag=70c2f14bf12121054e527e37614ad181; sessionid=8b4123da07fd561ea7bfc52faa42e52d; uid_tt=b75baac90524e90ba88b97563ec6c6f1; sid_tt=8b4123da07fd561ea7bfc52faa42e52d; sid_guard="8b4123da07fd561ea7bfc52faa42e52d|1510793021|15552000|Tue\054 15-May-2018 00:43:41 GMT"; _ga=GA1.2.1160927121.1510793041; UM_distinctid=15fc42f839588c-0606e62e941d41-71206751-15f900-15fc42f8396614; _gid=GA1.2.407497360.1512471063; __utma=24953151.1160927121.1510793041.1512610489.1512610489.1; __utmb=24953151.2.10.1512610489; __utmc=24953151; __utmz=24953151.1512610489.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmt=1
		
		int count=40;
		for(int i=0;i<count;i++) {
			String tempUrl = url+(i+1)*100;
			//headers.put("Cookie","sso_login_status=0;_ga=GA1.1.2075885034.1512609934;_gid=GA1.1.1895051370.1512609934;_ba=BA0.2-20171207-51225-YBAOXBfLGYprWrVect1o;__tasessionId=vkt0kbxkl1512612032951");
			System.out.println(tempUrl);
			String data=HtmlUnit.getHtml(tempUrl,headers);
			
			//String data = NetWorkUtils.getByHttpClient(tempUrl,null,headers);
			System.out.println(data);
			
		}
		
		/*String data = NetWorkUtils.postByHttpClient("p.tanx.com/ex?i=mm_26632268_8510653_28656653&m=1",new HashMap<String, String>());
		System.out.println(data);*/
	}
}
