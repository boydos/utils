package com.ds.license;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class LicenseUtils {
	public final static int ENCODE_BASE_COUNT=5;
	public final static int ENCODE_MD_COUNT=5;
	public final static int ENCODE_LEN_LENGTH=4;
	public static byte[] getBase64(String str) {
		if(str==null||str.length()==0) {
			return null;
		}
		try {
			return getBase64(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return getBase64(str.getBytes());
		}
	}
	public static byte[] getBase64(byte[] bytes) {
		if(bytes==null||bytes.length==0) {
			return null;
		}
		return Base64.encodeBase64(bytes);
	}
	public static byte[] getDeBase64(String str) {
		if(str==null||str.length()==0) {
			return null;
		}
		return Base64.decodeBase64(str);
	}
	public static byte[] getDeBase64(byte[] bytes) {
		if(bytes==null||bytes.length==0) {
			return null;
		}
		return Base64.decodeBase64(bytes);
	}
	public static byte [] getMd5(byte[] bytes) {
		try {
	        //确定计算方法
	        MessageDigest md5=MessageDigest.getInstance("MD5");
	        //加密后的字符串
	        return md5.digest(bytes);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	}
	public static byte[] getMd5(String str) {
		if(str==null||str.length()==0) {
			return null;
		}
	    try {
			return getMd5(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return getMd5(str.getBytes());
		}
	}
	public static byte[] makeDecode(byte[] bytes) {
		byte[] decode=bytes;
		if(decode==null||decode.length==0) {
			return decode;
		}
		for(int i=0;i<ENCODE_BASE_COUNT;i++) {
			decode= Base64.decodeBase64(decode);
		}
		return decode;
	}
	public static byte [] makeEncode(String str) {
		if(str==null||str.length()==0) {
			return null;
		}
	    try {
			return makeEncode(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return makeEncode(str.getBytes());
		}
	}
	public static byte [] makeEncode(byte[] bytes) {
		byte[] encode=bytes;
		if(encode==null||encode.length==0) return encode;
		for(int i=0;i<ENCODE_BASE_COUNT;i++) {
			encode= Base64.encodeBase64(encode);
		}
		return encode;
	}
	public static byte [] makeMd5(byte[] bytes) {
		byte[] encode=bytes;
		if(encode==null||encode.length==0) return encode;
		for(int i=0;i<ENCODE_MD_COUNT;i++) {
			encode= getMd5(encode);
		}
		return encode;
	}
	public static byte [] makeLen(int len) {
		byte [] def= new byte[ENCODE_LEN_LENGTH];
		for(int i=0;i<ENCODE_LEN_LENGTH;i++) {
			def[i]=(byte)((len>>(ENCODE_LEN_LENGTH-1-i)*8)&0xff);
		}
		return def;
	}
	public static int makeLen(byte[] bytes) {
		int value=0;
		for(int i=0;i<ENCODE_LEN_LENGTH;i++) {
			value|=(bytes[i]&0xff)<<(ENCODE_LEN_LENGTH-1-i)*8;
		}
		return (int)value;
	}
	public static void printBytes(byte[] bytes, String tag) {
        String value = "";
        for (byte h : bytes) {
            value += Integer.toHexString(h);
        }
        System.out.println(String.format("%s -0x%s", tag, value));
    }
	public static void printFun(byte[] function, String tag) {
		String msg="";
		int len = function.length;
		for(int i=0;i<16;i++) {
			int value = function[len-1-i/8]&(1<<i>>(i/8)*8);
			if(value!=0) {
				msg+="i="+i+",";
			}
		}
		System.out.println(String.format("%s %s,=%d", tag, msg,8&1<<3));
	}
	public static void main(String[] args) {
		/*int len =256*256-1;
		byte [] lencode= makeLen(len);
		printBytes(lencode, "lencode");
		System.out.println(makeLen(lencode));
		System.out.println(2<<3);
		printBytes(makeLen(8),"f");
		printFun(makeLen(16),"function");*/
		License license = new License();
		license.setDate("2017-10-22 12:15:16");
		license.setMac("fa:fb:fc:fd:fe:ff");
		license.setValid(101);
		license.setFunction(1, true);
		license.setFunction(6, true);
		license.setFunction(15, true);
		ReportUtils report = new ReportUtils();
		byte [] code = report.encode(license);
		printBytes(code, "value");
		license = report.decode(code);
		System.out.println("date:"+license.getDate());
		System.out.println("mac:"+license.getMac());
		System.out.println("valid:"+license.getValid());
		
		boolean [] functions = license.getFunction();
		System.out.print("functions[");
		for(int i=0;i<functions.length;i++) {
			if(functions[i]) {
				System.out.print(i+",");
			}
		}
		System.out.println("]");
	}
}
