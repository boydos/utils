package com.ds.license;

public class License {
	private String date;
	private int valid=0;
	private String mac;
	private boolean [] function = new boolean[16];
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public boolean[] getFunction() {
		return function;
	}
	public void setFunction(int index,boolean value) {
		if(index>=0 && index<function.length) {
			function[index]=value;
		}
	}
	
	
}
