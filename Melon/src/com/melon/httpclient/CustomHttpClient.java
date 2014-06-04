package com.melon.httpclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 
 * @Description: ��ȡ��ʵ��httpClient, �̰߳�ȫ, �����ڶ��̷߳���
 * @author maozhou
 * @date 2013-10-10 ����10:50:57
 */
public class CustomHttpClient {
	private static HttpClient customHttpClient;
	public final static String USER_AGENT="Mozilla/5.0 (Linux; U; Android 2.2.1; en-us; Nexus One Build/FRG83) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
	
	
	private CustomHttpClient() {
	}
	
	/**
	 * @serialData version Э��汾:HttpVersion.HTTP_1_1
	 * @serialData contentCharset �ַ�����:HTTP.DEFAULT_CONTENT_CHARSET
	 * @serialData useExpectContinue:true
	 * @serialData useragent:CustomHttpClient.userAgent
	 * @serialData timeout ���ӳس�ʱʱ��:1000
	 * @serialData connectionTimeout ���ӳ�ʱʱ��:5000
	 * @serialData soTimeout socket��ʱʱ��:10000
	 * @serialData httpPort http����Ĭ�϶˿�:��
	 * @serialData httpsPort https����Ĭ�϶˿�:��
	 * @return httpClient
	 */
	public static synchronized HttpClient getHttpClient(){
		if(customHttpClient==null){
			customHttpClient=getHttpClient(HttpVersion.HTTP_1_1, HTTP.DEFAULT_CONTENT_CHARSET, true, USER_AGENT, 
					CustomHttpConstant.CONNMANAGER_TIMEOUT, CustomHttpConstant.CONNECTION_TIMEOUT, CustomHttpConstant.SOTIMEOUT, 
					CustomHttpConstant.defaultHttpPort, CustomHttpConstant.defaultHttpsPort);
		}
		return customHttpClient;
	}
	
	/**
	 * ���HttpClient��ʵ��
	 * @param version Э��汾:HttpVersion.HTTP_0_9 / HttpVersion.HTTP_1_0 / HttpVersion.HTTP_1_1
	 * @param contentCharset �ַ�����:HTTP.DEFAULT_CONTENT_CHARSET / HTTP.UTF_8 / HTTP.ISO_8859_1 etc.
	 * @param useExpectContinue 
	 * @see void org.apache.http.params.HttpProtocolParams.setUseExpectContinue(HttpParams params, boolean b)
	 * @param useragent 
	 * @see void org.apache.http.params.HttpProtocolParams.setUserAgent(HttpParams params, String useragent)
	 * @param timeout ���ӳس�ʱʱ��
	 * @param connectionTimeout ���ӳ�ʱʱ��
	 * @param soTimeout socket��ʱʱ��
	 * @param httpPort http����˿�,-1ʱ����˶˿�
	 * @param httpsPort https����˿�,-1ʱ����˶˿�
	 * @return httpClient
	 */
	public static synchronized HttpClient getHttpClient(ProtocolVersion version, String contentCharset, boolean useExpectContinue, String useragent, 
			 long timeout, int connectionTimeout, int soTimeout, int httpPort, int httpsPort){
		if(customHttpClient==null){
			HttpParams params=new BasicHttpParams();
			HttpProtocolParams.setVersion(params, version);
			HttpProtocolParams.setContentCharset(params, contentCharset);
			HttpProtocolParams.setUseExpectContinue(params, useExpectContinue);
			HttpProtocolParams.setUserAgent(params, useragent);
			
			ConnManagerParams.setTimeout(params, timeout);
			HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
			HttpConnectionParams.setSoTimeout(params, soTimeout);
			
			SchemeRegistry schreg=new SchemeRegistry();
			schreg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), httpPort));
			schreg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), httpsPort));
			ClientConnectionManager conMgr=new ThreadSafeClientConnManager(params, schreg);
			
			customHttpClient=new DefaultHttpClient(conMgr, params);
		}
		return customHttpClient;
	}
	
	/**
	 * �����������ȡ������
	 * @param url ����url
	 * @param params �������
	 * @param method ���󷽷�,use CustomHttpClient.POST or CustomHttpClient.GET
	 * @param requestTimeStamp ��ǰ����ʱ���,��������ֹ����
	 * @return ��Ӧ������
	 */
	public static synchronized InputStream requestForInputStream(Context context, String url, Map<String, String> params, String method, Long requestMark){
		ByteArrayInputStream bais=null;
		try {
			HttpResponse httpResponse = CustomHttpResponse.getHttpResponse(context, url, params, method, requestMark);
			if(httpResponse!=null){
				InputStream is=httpResponse.getEntity().getContent();
				ByteArrayOutputStream baos=new ByteArrayOutputStream();
				byte[] buffer=new byte[1024];
				int len=0;
				if((len=is.read(buffer))!=-1){
					baos.write(buffer, 0, len);
				}
				bais=new ByteArrayInputStream(baos.toByteArray());
				baos.close();
				is.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bais;
	}
	
	/**
	 * �����������ȡ�ַ���
	 * @param url ����url
	 * @param params �������
	 * @param method ���󷽷�,use CustomHttpClient.POST or CustomHttpClient.GET
	 * @param responseEncoding ��Ӧ�����ʽ
	 * @param requestTimeStamp ��ǰ����ʱ���,��������ֹ����
	 * @return ��Ӧ�ַ���
	 */
	public static synchronized String requestForString(Context context, String url, Map<String, String> params, String method, String responseEncoding, Long requestTimeStamp){
		String result=null;
		try {
			HttpResponse httpResponse = CustomHttpResponse.getHttpResponse(context, url, params, method, requestTimeStamp);
			if(httpResponse!=null){
				if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
					result=EntityUtils.toString(httpResponse.getEntity(), responseEncoding);
				}else{
					result="error: "+httpResponse.getStatusLine().getStatusCode();
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * �����������ȡBitmap
	 * @param url ����url
	 * @param params �������
	 * @param method ���󷽷�,use CustomHttpClient.POST or CustomHttpClient.GET
	 * @param requestTimeStamp ��ǰ����ʱ���,��������ֹ����
	 * @return ��Ӧ����ͼƬBitmap
	 */
	public static synchronized Bitmap requestForBitmap(Context context, String url, Map<String, String> params, String method, Long requestTimeStamp){
		InputStream is=requestForInputStream(context, url, params, method, requestTimeStamp);
		Bitmap bmp=BitmapFactory.decodeStream(is);
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bmp;
	}
	
	/**
	 * �����������ȡDrawable
	 * @param url ����url
	 * @param params �������
	 * @param method ���󷽷�,use CustomHttpClient.POST or CustomHttpClient.GET
	 * @param requestTimeStamp ��ǰ����ʱ���,��������ֹ����
	 * @param srcName ͼƬ����
	 * @return ��Ӧ����ͼƬDrawable
	 */
	public static synchronized Drawable requestForDrawable(Context context, String url, Map<String, String> params, String method, Long requestTimeStamp, String srcName){
		InputStream is=requestForInputStream(context, url, params, method, requestTimeStamp);
		Drawable drawable=BitmapDrawable.createFromStream(is, srcName);
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return drawable;
	}
	
	/**
	 * ��������ʱ�����ֹ���������
	 * @param requestTimeStamp ��ǰ����ʱ���,��������ֹ����
	 */
	public static void abort(Long requestTimeStamp){
		CustomHttpResponse.abort(requestTimeStamp);
	}
	
	/**
	 * ��ֹ�����������������
	 */
	public static void abort(){
		CustomHttpResponse.abortAll();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}