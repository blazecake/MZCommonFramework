package com.melon.httpclient;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;

/**
 * @Description: 文件上传
 * @author maozhou
 * @date 2013-10-10 下午5:10:06
 */
public class HttpFilesUpload {
	
	public static String upload(Context context, String url, MultipartEntity multipartEntity, Long requestTimeStamp) {
		String result=null;
		HttpPost httpPost =CustomHttpPost.getHttpPost(url, multipartEntity);
	    httpPost.setEntity(multipartEntity);
	    BasicHttpParams httpParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		HttpConnectionParams.setSoTimeout(httpParams, 600000);
		httpPost.setParams(httpParams);
		
		HttpClient httpClient = CustomHttpClient.getHttpClient();
		HttpResponse response=null;
		try {
			response = CustomHttpResponse.getHttpResponse(context, httpClient, httpPost, requestTimeStamp);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
			}else{
				result="error: "+response.getStatusLine().getStatusCode();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 批量上传文件
	 * @param url 请求url
	 * @param params 请求参数
	 * @param fileParam 请求上传文件参数名
	 * @param fileList 请求上传文件集合
	 * @param requestTimeStamp 当前请求时间戳
	 * @return
	 */
	public static synchronized String upload(Context context, String url, Map<String, String> params, String fileParam, List<File> fileList, Long requestTimeStamp){
		String result=null;
	    MultipartEntity multipartEntity = new MultipartEntity();
	    for(File file:fileList){
	    	FileBody fileBody = new FileBody(file);
			multipartEntity.addPart(fileParam, fileBody);
	    }
	    if (params != null) {
	    	try {
				Set<Entry<String, String>> set=params.entrySet();
				for (Map.Entry<String, String> m : set) {
					multipartEntity.addPart(m.getKey(), new StringBody(m.getValue()));
				}
	    	} catch (UnsupportedEncodingException e) {
	    		e.printStackTrace();
	    	}
		}
	    result = upload(context, url, multipartEntity, requestTimeStamp);
	    return result;
	}

	/**
	 * 批量上传文件
	 * @param url 请求url
	 * @param params 请求参数
	 * @param fileMap 请求上传文件集合
	 * @param requestTimeStamp 当前请求时间戳
	 * @return
	 */
	public static synchronized String upload(Context context, String url, Map<String, String> params, Map<String, File> fileMap, Long requestTimeStamp){
		String result=null;
	    MultipartEntity multipartEntity = new MultipartEntity();
	    Set<Entry<String, File>> fileSet=fileMap.entrySet();
	    for(Entry<String, File> entry:fileSet){
	    	FileBody fileBody = new FileBody(entry.getValue());
			multipartEntity.addPart(entry.getKey(), fileBody);
	    }
	    if (params != null) {
	    	try {
				Set<Entry<String, String>> set=params.entrySet();
				for (Map.Entry<String, String> m : set) {
					multipartEntity.addPart(m.getKey(), new StringBody(m.getValue()));
				}
	    	} catch (UnsupportedEncodingException e) {
	    		e.printStackTrace();
	    	}
		}
	    result = upload(context, url, multipartEntity, requestTimeStamp);
	    return result;
	}
	
	/**
	 * 根据请求时间戳终止文件上传
	 * @param requestTimeStamp 当前请求时间戳,可用于终止请求
	 */
	public static void abort(Long requestTimeStamp){
		CustomHttpResponse.abort(requestTimeStamp);
	}
	
	/**
	 * 终止所有正在请求的连接
	 */
	public static void abort(){
		CustomHttpResponse.abortAll();
	}
	
}
