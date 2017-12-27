package com.ds.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class NetWorkUtils extends BaseCloseUtils {
	public static final String DEFAULT_CHARSET="UTF-8";
	public static final int DEFAULT_TIMEOUT=5000;
	/**
	 * Method=GET, dependency HttpURLConnection DEFAULT_CHARSET=UTF-8 DEFAULT_TIMEOUT=5s
	 * @param url
	 * @return HTTP Content
	 */
	public static String getByHttpURL(String url) {
		return getByHttpClient(url,DEFAULT_CHARSET);
	}
	/**
	 * Method=GET, dependency HttpURLConnection DEFAULT_TIMEOUT=5s
	 * @param url
	 * @param charset
	 * @param header
	 * @return
	 */
	public static String getByHttpURL(String url,String charset) {
		return getByHttpURL(url, charset,null);
	}
	/**
	 * Method=GET, dependency HttpURLConnection DEFAULT_TIMEOUT=5s
	 * @param url
	 * @param charset
	 * @return HTTP Content
	 */
	public static String getByHttpURL(String url,String charset, Map<String, String> header) {
	    BufferedReader in = null;        
	    String result=null; 
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
			conn.setConnectTimeout(DEFAULT_TIMEOUT);
			conn.setRequestMethod("GET");
			if(header!=null&&header.size()>0) {
            	Set<String> keys= header.keySet();
            	for(String key:keys) {
            		conn.setRequestProperty(key, header.get(key));
            	}
            }
			conn.connect();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			if(conn.getResponseCode()!=HttpURLConnection.HTTP_OK) {
				throw new IOException("Can't Connect Server");
			}
			result = FileUtils.getReader(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			catchError(e);
		}finally{
	           closeResources(in);
	    }
		return result;
	}
	/**
	 * Method=Post, dependency HttpURLConnection DEFAULT_CHARSET=UTF-8
	 * @param url
	 * @param params
	 * @return HTTP Content
	 */
	public static String postByHttpURL(String url, Map<String, String> params)  {
		return postByHttpURL(url,params,null);
	}
	/**
	 * Method=Post, dependency HttpURLConnection DEFAULT_CHARSET=UTF-8
	 * @param url
	 * @param params
	 * @param header
	 * @return
	 */
	public static String postByHttpURL(String url,Map<String, String> params, Map<String,String>header) {
		return postByHttpURL(url,DEFAULT_CHARSET, params,header);
	}
	/**
	 * Method=Post, dependency HttpURLConnection DEFAULT_CHARSET=UTF-8
	 * @param url
	 * @param charset
	 * @param params
	 * @return
	 */
	public static String postByHttpURL(String url,String charset, Map<String, String> params) {
		return postByHttpURL(url,charset, params,null);
	}
	/**
	 * Method=Post, dependency HttpURLConnection
	 * @param url
	 * @param charset
	 * @param params
	 * @param header
	 * @return HTTP Content
	 */
	public static String postByHttpURL(String url,String charset, Map<String, String> params, Map<String,String>header) {
        OutputStreamWriter out = null;
        BufferedReader in = null;        
        String result=null; 
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if(header!=null&&header.size()>0) {
            	Set<String> keys= header.keySet();
            	for(String key:keys) {
            		conn.setRequestProperty(key, header.get(key));
            	}
            }
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), charset);
            // 发送请求参数            
            if (params != null) {
		          StringBuilder param = new StringBuilder(); 
		          for (Map.Entry<String, String> entry : params.entrySet()) {
		        	  if(param.length()>0){
		        		  param.append("&");
		        	  }	        	  
		        	  param.append(entry.getKey());
		        	  param.append("=");
		        	  param.append(entry.getValue());		        	  
		          }
		          out.write(param.toString());
            }
            // flush输出流的缓冲
           out.flush();
            // 定义BufferedReader输入流来读取URL的响应
           in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
           result = FileUtils.getReader(in);
        } catch (Exception e) {            
			catchError(e);
        } finally {
           closeResources(out,in);
        }
        return result.toString();
    }
	
	/**
	 * Method=Post, dependency HTTPCLIENT
	 * @param url
	 * @param paras
	 * @return HTTP Content
	 */
	public static String postByHttpClient(String url, Map<String,String> paras) {
		return postByHttpClient(url,null,paras);
	}
	/**
	 * Method=Post, dependency HTTPCLIENT
	 * @param url
	 * @param paras
	 * @return HTTP Content
	 */
	public static String postByHttpClient(String url, NameValuePair []paras) {
		return postByHttpClient(url,null,paras);
	}
	/**
	 * Method=Post, dependency HTTPCLIENT
	 * @param url
	 * @param paras
	 * @return
	 */
	public static String postByHttpClient(String url, Header [] paras) {
		return postByHttpClient(url,null,paras);
	}
	/**
	 * Method=Post, dependency HTTPCLIENT
	 * @param url
	 * @param charset
	 * @param namevalues
	 * @return HTTP Content
	 */
	public static String postByHttpClient(String url,String charset, Map<String,String> paras) {
		return postByHttpClient(url,charset,getNameValues(paras),null);
	}
	/**
	 * Method=Post, dependency HTTPCLIENT
	 * @param url
	 * @param charset
	 * @param header
	 * @return
	 */
	public static String postByHttpClient(String url,String charset, Header []header) {
		return postByHttpClient(url,charset,null,header);
	}
	/**
	 * Method=Post, dependency HTTPCLIENT
	 * @param url
	 * @param charset
	 * @param paras
	 * @return
	 */
	public static String postByHttpClient(String url,String charset, NameValuePair []paras) {
		return postByHttpClient(url,charset,paras,null);
	}
	/**
	 * Method=Post, dependency HTTPCLIENT
	 * @param url
	 * @param charset
	 * @param paras
	 * @param header
	 * @return HTTP Content
	 */
	public static String postByHttpClient(String url,String charset, NameValuePair []paras,Header [] header) {
		if(StringUtils.isEmpty(url)) return "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		trustHttpClient(httpclient);
		HttpPost httppost = new HttpPost(url);
		if(header!=null&&header.length>0) {
			for(Header head:header) {
				httppost.addHeader(head);
			}
		}
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if(paras!=null) {
			for(NameValuePair nvp:paras) {
				formparams.add(nvp);
			}
		}
		UrlEncodedFormEntity uefEntity;
		CloseableHttpResponse response=null;
		try {
			if(charset==null) {
				uefEntity=new UrlEncodedFormEntity(formparams);
			} else {
				uefEntity=new UrlEncodedFormEntity(formparams,charset);
			}
			httppost.setEntity(uefEntity);
			response = httpclient.execute(httppost);
			HttpEntity result= response.getEntity();
			if(result!=null) {
				if(charset==null) {
					return EntityUtils.toString(result);
				} else {
					return EntityUtils.toString(result,charset);
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			catchError(e);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			catchError(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			catchError(e);
		}  finally {
			closeResources(response,httpclient);
		}
		return "";
	}
	public static String postJsonHttpClient(String url,String charset,String json,Header [] header) {
		if(StringUtils.isEmpty(url)) return "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		trustHttpClient(httpclient);
		HttpPost httppost = new HttpPost(url);
		if(header!=null&&header.length>0) {
			for(Header head:header) {
				httppost.addHeader(head);
			}
		}
		StringEntity entity = new StringEntity(json,"utf-8");
		CloseableHttpResponse response=null;
		try {
			httppost.setEntity(entity);
			response = httpclient.execute(httppost);
			HttpEntity result= response.getEntity();
			if(result!=null) {
				if(charset==null) {
					return EntityUtils.toString(result);
				} else {
					return EntityUtils.toString(result,charset);
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			catchError(e);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			catchError(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			catchError(e);
		}  finally {
			closeResources(response,httpclient);
		}
		return "";
	}
	/**
	 * Method=Get, dependency HTTPCLIENT
	 * @param url
	 * @return
	 */
	public static String getByHttpClient(String url) {
		return getByHttpClient(url,null);
	}
	/**
	 * Method=Get, dependency HTTPCLIENT
	 * @param url
	 * @param charset
	 * @return
	 */
	public static String getByHttpClient(String url,String charset) {
		return getByHttpClient(url, charset,null);
	}
	/**
	 * Method=Get, dependency HTTPCLIENT
	 * @param url
	 * @param charset
	 * @return HTTP Content
	 */
	public static String getByHttpClient(String url,String charset,Map<String,String> headers) {
		if(StringUtils.isEmpty(url)) return null;
		CloseableHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter("http.protocol.single-cookie-header", true);
		httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		CookieStore cookieStore = new BasicCookieStore(); 
		HttpContext localContext = new BasicHttpContext();
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		trustHttpClient(httpclient);
		HttpGet httpget= new HttpGet(url);
		if(headers !=null) {
			Set<String> keys = headers.keySet();
			for(String key:keys) {
				httpget.addHeader(key, headers.get(key));
			}
		}
		CloseableHttpResponse response=null;
		try {
			response = httpclient.execute(httpget,localContext);
			HttpEntity result= response.getEntity();
			//Log.d("result:"+EntityUtils.toString(result));
			if(StringUtils.isEmpty(charset)) {
				charset = getContentCharSet(result);
			}
			return formatString(result,charset);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			catchError(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			catchError(e);
		} finally {
			closeResources(response,httpclient);

		}
		return null;
	}
	
	public static void trustHttpClient(CloseableHttpClient httpclient) {
		if(httpclient==null) return;
		X509TrustManager xtm = new MyX509TrustManager();
		//这个好像是HOST验证
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
			public void verify(String arg0, SSLSocket arg1) throws IOException {}
			public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {}
			public void verify(String arg0, X509Certificate arg1) throws SSLException {}
			public void verify(String host, java.security.cert.X509Certificate cert) throws SSLException {
				// TODO Auto-generated method stub
				
			}
		};
		try {
		//TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
		SSLContext ctx = SSLContext.getInstance("TLS");
		//使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
		ctx.init(null, new TrustManager[] { xtm }, null);
		//创建SSLSocketFactory
		org.apache.http.conn.ssl.SSLSocketFactory socketFactory = new org.apache.http.conn.ssl.SSLSocketFactory(ctx);
		socketFactory.setHostnameVerifier(hostnameVerifier);
		//通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
		httpclient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443));
		}catch(Exception e) {
			catchError(e);
		}
	}
	public static String getCookie(String url,Map<String,String> headers) {
		HttpResponse response = getResponse(url, headers);
		Header ck=response.getLastHeader("Set-Cookie");
		if(ck!=null) {
			return ck.getValue();
		}
		return null;
	}
	public static void main(String [] args) {
		String url="http://www.yidianzixun.com/";
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:55.0) Gecko/20100101 Firefox/55.0");
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		/*headers.put("Accept-Encoding", "gzip, deflate");*/
		headers.put("Accept-Language", "en-US,en;q=0.5");
		headers.put("Referer", "http://www.yidianzixun.com/");
		headers.put("Host", "www.yidianzixun.com");
		headers.put("Upgrade-Insecure-Requests", "1");
		
		String cookie;//= getCookie(url,headers);
		//System.out.println("1:==="+cookie);
		headers.put("Cookie", "wuid=123456712345678");
		cookie = getCookie("http://www.yidianzixun.com/captcha", headers)+";";
		System.out.println("cookie:"+cookie);

		url ="http://www.yidianzixun.com/home/q/news_list_for_channel?channel_id=hot&cstart=70&cend=80&infinite=false&refresh=1&__from__=pc&requestSource=wxb&multi=5&appid=yidian&_=1510728988937";
		headers = new HashMap<String,String>();
		headers.put("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:55.0) Gecko/20100101 Firefox/55.0");
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		/*headers.put("Accept-Encoding", "gzip, deflate");*/
		headers.put("Accept-Language", "en-US,en;q=0.5");
		headers.put("Host", "www.yidianzixun.com");
		headers.put("Cookie", "JSESSIONID=6b25c0d7e8517d949899f7e7348f373a6e1b4b8f63f161b6b17791acb2774cc2;wuid=123456712345678; wuid_createAt=2017-11-15 19:07:03; weather_auth=2;"+cookie);
		headers.put("Upgrade-Insecure-Requests", "1");
		headers.put("Connection", "keep-alive");
		//183519270603345
		String data = NetWorkUtils.getByHttpClient(url,null,headers);
		System.out.println(data);
	}
	public static CloseableHttpResponse getResponse(String url,Map<String,String> headers) {
		CloseableHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter("http.protocol.single-cookie-header", true);
		httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		CookieStore cookieStore = new BasicCookieStore(); 
		HttpContext localContext = new BasicHttpContext();
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		trustHttpClient(httpclient);
		HttpGet httpget= new HttpGet(url);
		if(headers !=null) {
			Set<String> keys = headers.keySet();
			for(String key:keys) {
				httpget.addHeader(key, headers.get(key));
			}
		}
		CloseableHttpResponse response=null;
		try {
			response = httpclient.execute(httpget,localContext);
			return response;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			catchError(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			catchError(e);
		}
		return response;
	}
	/**
	 * change HttpEntity to String
	 * @param entity
	 * @return HTTP Content
	 */
	public static String formatString(HttpEntity entity) {
		return formatString(entity,null);
	}
	/**
	 * change HttpEntity to String,Use Charset
	 * @param entity
	 * @param charset
	 * @return HTTP content
	 */
	public static String formatString(HttpEntity entity,String charset) {
		try {
			if(StringUtils.isEmpty(charset)) {
				return entity!=null? EntityUtils.toString(entity):""; 
			}
			return entity!=null? EntityUtils.toString(entity,charset):"";
		} catch (org.apache.http.ParseException e) {
			// TODO Auto-generated catch block
			catchError(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			catchError(e);
		}
		return "";
	}
	/**
	 * get HttpEntity's CHARSET,Default is UTF-8
	 * @param entity
	 * @return CHARSET
	 */
	public static String getContentCharSet(final HttpEntity entity) {   
        if (entity == null) {   
            throw new IllegalArgumentException("HTTP entity may not be null");   
        }   
        String charset = null;   
        if (entity.getContentType() != null) {    
            HeaderElement values[] = entity.getContentType().getElements();   
            if (values.length > 0) {   
                NameValuePair param = values[0].getParameterByName("charset" );   
                if (param != null) {   
                    charset = param.getValue();   
                }   
            }   
        }   
         
        if(StringUtils.isEmpty(charset)){  
            charset = DEFAULT_CHARSET;  
        }  
        return charset;   
   }
   /**
    * Change Map to NameValuePair
    * @param map
    * @return NameValuePair
    */
   public static NameValuePair [] getNameValues(Map<String,String>map) {
		if(map ==null) return null;
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Set<String>keys = map.keySet();
		for(String key:keys) {
			list.add(new BasicNameValuePair(key, map.get(key)));
		}
		NameValuePair [] name = new BasicNameValuePair[list.size()];
		return list.toArray(name);
		
	}
   /**
    * Change Map to Header
    * @param map
    * @return
    */
   public static Header [] getHeaderValues(Map<String,String>map) {
		if(map ==null) return null;
		List<Header> list = new ArrayList<Header>();
		Set<String>keys = map.keySet();
		for(String key:keys) {
			list.add(new BasicHeader(key, map.get(key)));
		}
		BasicHeader [] name = new BasicHeader[list.size()];
		return list.toArray(name);
	}
   /**
    * @param url
    * @return
    */
   public static InputStream getInputStream(String url) {
	   if(url!=null) {
		   url =url.trim();
	   } else return null;
		try {
			URL realUrl = new URL(url);
			if(url.startsWith("https://")) {
				HttpsURLConnection conn =(HttpsURLConnection) realUrl.openConnection();
				conn.setHostnameVerifier(new HostnameVerifier() {
					
					public boolean verify(String hostname, SSLSession session) {
						// TODO Auto-generated method stub
						return true;
					}
				});
				conn.setSSLSocketFactory(getSSL());
				conn.setConnectTimeout(DEFAULT_TIMEOUT);
				conn.setRequestMethod("GET");
				return conn.getInputStream();
			}
			HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
			conn.setConnectTimeout(DEFAULT_TIMEOUT);
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
		    conn.setDoInput(true);
			return conn.getInputStream();
		 }  catch (IOException e) {
			// TODO Auto-generated catch block
			 catchError(e);
			 //e.printStackTrace();
			 System.out.println("url:"+url);
		}
	    return null;
   }
   /**
    * @param url
    * @return
    */
   public static InputStream postInputStream(String url) {
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
			conn.setConnectTimeout(DEFAULT_TIMEOUT);
		    // 发送POST请求必须设置如下两行
		    conn.setDoOutput(true);
		    conn.setDoInput(true);
		    // POST方法
		    conn.setRequestMethod("POST");
		    // 设置通用的请求属性
		    conn.setRequestProperty("accept", "*/*");
		    conn.setRequestProperty("connection", "Keep-Alive");
		    conn.setRequestProperty("user-agent",
		           "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			return conn.getInputStream();
		 }  catch (IOException e) {
			// TODO Auto-generated catch block
			catchError(e);
		}
	    return null;
   }
   
   public static SSLSocketFactory getSSL() {
	   TrustManager[] tm = { new MyX509TrustManager() };    
       try {
           SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");    
           sslContext.init(null, tm, new java.security.SecureRandom());
		   SSLSocketFactory ssf = sslContext.getSocketFactory();  
		  return ssf;
       } catch (KeyManagementException e) {
		// TODO Auto-generated catch block
		return null;
	   } catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
	   } catch (NoSuchProviderException e) {
		// TODO Auto-generated catch block
	   } 
       return null;
        
   }
}
