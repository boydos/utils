package com.ds.utils;

public class Log {
	private static final String TAG ="tds-log";
	private static boolean LOG_DEBUG=false;
	private static boolean LOG_INFO=true;
	private static boolean LOG_ERROR=true;
	public static void setDebug(boolean isPrint) {
		LOG_DEBUG = isPrint;
	}
	public static void setInfo(boolean isPrint) {
		LOG_INFO = isPrint;
	}
	public static void setError(boolean isPrint) {
		LOG_ERROR = isPrint;
	}
	/**
	 * info TAG=TDS-log
	 * @param msg
	 */
	public static void i(String msg) {
		i(TAG, msg);
	}
	/**
	 * debug TAG=TDS-log
	 * @param msg
	 */
	public static void d(String msg) {
		d(TAG, msg);
	}
	/**
	 * error TAG=TDS-log
	 * @param msg
	 */
	public static void e(String msg) {
		e(TAG, msg);
	}
	/**
	 * info
	 * @param TAG
	 * @param msg
	 */
	public static void i(String TAG,String msg) {
		if(LOG_INFO)System.out.println(String.format("[%s] [%s]", TAG,msg));
	}
	/**
	 * debug
	 * @param TAG
	 * @param msg
	 */
	public static void d(String TAG,String msg) {
		if(LOG_DEBUG)System.out.println(String.format("[%s] [%s]", TAG,msg));
	}
	/**
	 * error
	 * @param TAG
	 * @param msg
	 */
	public static void e(String TAG,String msg) {
		if(LOG_ERROR)System.err.println(String.format("[%s] [%s]", TAG,msg));
	}

}
