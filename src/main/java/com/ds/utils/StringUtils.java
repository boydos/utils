package com.ds.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.Base64Encoder;

import com.ds.json.JsonModel;

public class StringUtils {
	/**
	 * check string ==null || string is space
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str==null ||"".equals(str.trim());
	}
	/**
	 * check list ==null ||list is empty
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List list) {
		return list==null||list.size()==0;
	}
	public static boolean isEmpty(JsonModel model) {
		return model ==null||model.isEmpty();
	}
	/**
	 * Use Pattern to check number
	 * @param string
	 * @return if is number return true
	 */
	public static boolean isNumber(String str) {
		if(str ==null|| str.trim().length()==0) return false;
		Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() )
        {
              return false;
        }
        return true;
	}
	/**
	 * @param path
	 * @return
	 */
	public static String getWithoutExt(String path) {
		return  path.substring(0, path.lastIndexOf(".")==-1?path.length():path.lastIndexOf("."));
	}
	/**
	 * @param path
	 * @return
	 */
	public static String getExt(String path) {
		return path.substring(path.lastIndexOf(".")+1, path.length());
	}
	
	public static String EncoderByMd5(String str) {
		try {
	       //确定计算方法
	        MessageDigest md5=MessageDigest.getInstance("MD5");
	       //加密后的字符串
	        return Base64.encodeBase64String(md5.digest(str.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     return str;
	}
	
}
