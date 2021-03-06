package com.melon.httpclient;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * @Description: 获取HttpGet类
 * @author maozhou
 * @date 2013-10-10 下午2:27:17
 */
public class CustomHttpGet {
	
	/**
	 * 获得httpPost
	 * @param url 请求url
	 * @return httpPost
	 */
	public static synchronized HttpGet getHttpGet(String url){
		return getHttpGet(url, null, CustomHttpConstant.CONNECTION_TIMEOUT, CustomHttpConstant.SOTIMEOUT);
	}
	
	/**
	 * 获取httpPost
	 * @param url 请求url
	 * @param connectionTimeout 连接超时时间
	 * @param soTimeout socket超时时间
	 * @return httpPost
	 */
	public static synchronized HttpGet getHttpGet(String url, int connectionTimeout, int soTimeout){
		return getHttpGet(url, null, connectionTimeout, soTimeout);
	}
	
	/**
	 * 获取httpPost
	 * @param url 请求url
	 * @param params 请求提交参数
	 * @param connectionTimeout 连接超时时间
	 * @param soTimeout socket超时时间
	 * @return httpPost
	 */
	public static synchronized HttpGet getHttpGet(String url, Map<String, String> params, int connectionTimeout, int soTimeout){
		if(params!=null && params.size()>0){
			StringBuffer strBuffer=new StringBuffer(url);
			if(url.contains("?")){
				strBuffer.append("&");
			}else{
				strBuffer.append("?");
			}
			Set<Entry<String, String>> set=params.entrySet();
			for(Entry<String, String> entry:set){
				strBuffer.append(entry.getKey());
				strBuffer.append("=");
				strBuffer.append(entry.getValue());
				strBuffer.append("&");
			}
			strBuffer.delete(strBuffer.length()-1, strBuffer.length());
			url=strBuffer.toString();
		}
		
		HttpGet httpGet = new HttpGet(url);
		HttpParams httpParams = httpGet.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeout);
		HttpConnectionParams.setSoTimeout(httpParams, soTimeout);
		httpGet.setParams(httpParams);
		
		return httpGet;
	}
	
}
