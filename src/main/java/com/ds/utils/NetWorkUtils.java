package com.ds.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
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
			e.printStackTrace();
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
            e.printStackTrace();
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
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public static String getByHttpClient(String url,String charset,Header [] header) {
		if(StringUtils.isEmpty(url)) return null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget= new HttpGet(url);
		if(header!=null&& header.length>0) {
			for(Header hd:header) {
				httpget.addHeader(hd);
			}
		}
		CloseableHttpResponse response=null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity result= response.getEntity();
			if(StringUtils.isEmpty(charset)) {
				charset = getContentCharSet(result);
			}
			return formatString(result,charset);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			closeResources(response,httpclient);
		}
		return null;
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
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
			conn.setConnectTimeout(DEFAULT_TIMEOUT);
			conn.setRequestMethod("GET");
			return conn.getInputStream();
		 }  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			e.printStackTrace();
		}
	    return null;
   }
}
