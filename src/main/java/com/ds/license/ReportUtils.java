package com.ds.license;

import java.util.Random;

public class ReportUtils {
	private byte[] version=new byte[]{0x00,0x02};
	private final static int REPORT_LENGTH=500;
	
	public static byte[] getDefaultBytes(int len) {
		byte[] def = new byte[len];
		for(int i=0;i<len;i++) {
			def[i] =0x00;
		}
		return def;
	}
	public static void initByte(byte [] bytes, byte[] values,int index) {
		if(bytes ==null || values ==null) {
			return;
		}
		int bytesLen = bytes.length;
		if(index >bytesLen)  {
			return;
		}
		int valLen = values.length;
		int copyLen =valLen;
		if(valLen > bytesLen-index) {
			copyLen = bytesLen-index;
		}
		for(int i=0;i<copyLen;i++) {
			bytes[i+index] =values[i];
		}
	}
	public static void initRandomByte(byte[] bytes,int start,int len) {
		if(bytes == null) return;
		if(start<0) start =0;
		if(start+len> bytes.length) len = bytes.length-start;
		Random rand = new Random();
		for(int i=start;i<len+start; i++) {
			int temp =rand.nextInt(255);
			bytes[i] =(byte)(temp&0xff);
		}
	}
	public static byte[] getByteFrom(byte []bytes,int from,int len) {
        if(bytes ==null||len<=0) return null;
        if(from>bytes.length-1) return null;
        byte [] result = new byte[len];
        for(int i=from;i<bytes.length&& i<from+len;i++) {
            result[i-from]= bytes[i];
        }
        return result;
    }
	public byte[] encode(License license) {
		byte[] report= getDefaultBytes(REPORT_LENGTH);
		int nextIndex=0;
		initByte(report, version, 0);
		nextIndex+=version.length;
		byte [] temp = LicenseUtils.makeEncode(LicenseUtils.makeLen(license.getValid()));
		byte [] md5= LicenseUtils.makeMd5(temp);
		initByte(report, md5, nextIndex);
		nextIndex+=md5.length;
		byte [] valid= LicenseUtils.getBase64(temp);
		initByte(report, LicenseUtils.makeLen(valid.length), nextIndex);
		initByte(report,valid, nextIndex+LicenseUtils.ENCODE_LEN_LENGTH);
		nextIndex+=LicenseUtils.ENCODE_LEN_LENGTH+valid.length;
		
		byte [] date= LicenseUtils.getBase64(LicenseUtils.makeEncode(license.getDate()));
		initByte(report, LicenseUtils.makeLen(date.length), nextIndex);
		initByte(report, date, nextIndex+LicenseUtils.ENCODE_LEN_LENGTH);
		nextIndex+=LicenseUtils.ENCODE_LEN_LENGTH+date.length;
		
		byte [] mac= LicenseUtils.getBase64(LicenseUtils.makeEncode(license.getMac()));
		initByte(report, LicenseUtils.makeLen(mac.length), nextIndex);
		initByte(report, mac, nextIndex+LicenseUtils.ENCODE_LEN_LENGTH);
		nextIndex+=LicenseUtils.ENCODE_LEN_LENGTH+mac.length;
		
		boolean [] bFuns=license.getFunction();
		initByte(report, LicenseUtils.makeLen(bFuns.length), nextIndex);
		nextIndex+=LicenseUtils.ENCODE_LEN_LENGTH;
		int value = 0;
		for(int i=0;i<bFuns.length;i++) {
			if(bFuns[i]) {
				value +=1<<i;
			}
		}
		byte [] funBytes= LicenseUtils.makeLen(value);
		initByte(report, LicenseUtils.makeLen(funBytes.length), nextIndex);
		initByte(report, funBytes, nextIndex+LicenseUtils.ENCODE_LEN_LENGTH);
		nextIndex+=LicenseUtils.ENCODE_LEN_LENGTH+funBytes.length;
		System.out.println("size:"+nextIndex);
		initRandomByte(report, nextIndex, report.length-nextIndex+1);
		return report;
	}
	
	public License decode(byte[] bytes){
		if(bytes ==null || bytes.length !=REPORT_LENGTH) {
			return null;
		}
		if(version[0]!=bytes[0] || version[1]!=bytes[1]){
			return null;
		}
		int next=2;
		int len=0;
		byte [] temp;
		byte [] md5 = getByteFrom(bytes, next, 16);
		next+=16;
		
		temp = getByteFrom(bytes, next, LicenseUtils.ENCODE_LEN_LENGTH);
		next+=LicenseUtils.ENCODE_LEN_LENGTH;
		len = LicenseUtils.makeLen(temp);
		byte [] valid = getByteFrom(bytes, next, len);
		next+=len;
		
		temp = getByteFrom(bytes, next, LicenseUtils.ENCODE_LEN_LENGTH);
		next+=LicenseUtils.ENCODE_LEN_LENGTH;
		len = LicenseUtils.makeLen(temp);
		byte[] date = getByteFrom(bytes, next, len);
		next+=len;
		
		temp =getByteFrom(bytes, next, LicenseUtils.ENCODE_LEN_LENGTH);
		next+=LicenseUtils.ENCODE_LEN_LENGTH;
		len = LicenseUtils.makeLen(temp);
		byte[] mac = getByteFrom(bytes, next, len);
		next+=len;
		
		int funLen = LicenseUtils.makeLen(getByteFrom(bytes, next, LicenseUtils.ENCODE_LEN_LENGTH));
		next+=LicenseUtils.ENCODE_LEN_LENGTH;
		
		temp =getByteFrom(bytes, next, LicenseUtils.ENCODE_LEN_LENGTH);
		next+=LicenseUtils.ENCODE_LEN_LENGTH;
		len = LicenseUtils.makeLen(temp);
		byte[] fun = getByteFrom(bytes, next, len);
		next+=len;
		
		valid = LicenseUtils.getDeBase64(valid);
		License license = new License();
		if(new String(md5).equals(new String(LicenseUtils.makeMd5(valid)))) {
			valid = LicenseUtils.makeDecode(valid);
			license.setValid(LicenseUtils.makeLen(valid));
		} else license.setValid(-1);
		date = LicenseUtils.makeDecode(LicenseUtils.getDeBase64(date));
		license.setDate(new String(date));
		
		mac = LicenseUtils.makeDecode(LicenseUtils.getDeBase64(mac));
		license.setMac(new String(mac));
		
		len = fun.length;
		for(int i=0;i<(len-1)*8&&i<license.getFunction().length;i++) {
			int value = fun[len-1-i/8]&(1<<i>>(i/8)*8);
			license.setFunction(i, value!=0);
		}
		return license;
	}
}
