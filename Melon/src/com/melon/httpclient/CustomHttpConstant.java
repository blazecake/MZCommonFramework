package com.melon.httpclient;

/**
 * @Description: Http请求常量类
 * @author maozhou
 * @date 2013-10-10 下午2:28:40
 */
public class CustomHttpConstant {
	
	/** 请求方法post  */
	public final static String POST="post";
	
	/** 请求方法get */
	public final static String GET="get";
	
	/** 连接池超时时间 */
	public static int CONNMANAGER_TIMEOUT=5000;
	
	/**  请求连接超时时间 */
	public static int CONNECTION_TIMEOUT=5000;
	
	/** socket响应超时时间 */
	public static int SOTIMEOUT=10000;
	
//	/** 不设置默认端口 */
//	public final static int NO_PORT=-1;
	
	public static int defaultHttpPort=80;
	public static int defaultHttpsPort=443;
	
	
	
}
