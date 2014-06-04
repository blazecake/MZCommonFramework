package com.melon.httpurlconnection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Description: HttpURLConnection请求
 * @author maozhou
 * @date 2013-10-28 下午4:22:47
 */
public class CustomHttpUrlConnection {
	public static final String GET="GET";
	public static final String POST="POST";
	
	/**
	 * 通过HttpURLConnection请求服务器
	 * @param url 请求url地址
	 * @param param 提交参数
	 * @param method 请求方法,GET/POST
	 * @return
	 */
	public static InputStream request(String url, Object param, String method){
		ByteArrayInputStream bais=null;
		try {
			URL url2=new URL(url);
			HttpURLConnection conn=(HttpURLConnection) url2.openConnection();
			
			conn.setDoInput(true);
			if(param!=null){
				conn.setDoOutput(true);
			}
			conn.setUseCaches(false);
			conn.setRequestMethod(method);
			
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(30000);
			
			conn.connect();
			
			if(param!=null){
				OutputStream os=conn.getOutputStream();
				ObjectOutputStream oos=new ObjectOutputStream(os);
				oos.writeObject(param);
				oos.flush();
				oos.close();
			}
			
			InputStream is=conn.getInputStream();
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			byte[] buffer=new byte[1024];
			int len=0;
			if((len=is.read(buffer))!=-1){
				baos.write(buffer, 0, len);
			}
			bais=new ByteArrayInputStream(baos.toByteArray());
			
			baos.close();
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bais;
	}
	

}
