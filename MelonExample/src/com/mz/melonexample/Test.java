package com.mz.melonexample;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.test.AndroidTestCase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.melon.comapi.json.GsonUtil;
import com.melon.httpclient.CustomHttpClient;
import com.melon.httpclient.CustomHttpConstant;
import com.melon.security.DESUtil;
import com.melon.storage.SharedPreferencesUtil;
import com.melon.xml.CustomPullParser;
import com.melon.xml.CustomSaxParser;
import com.mz.melonexample.entity.User;

public class Test extends AndroidTestCase{

	//测试httpClient请求
	public void testHttpClient(){
		//httpClient请求数据
		Map<String, String> map=new HashMap<String, String>(); 
		map.put("wd","melon");
		String url="http://www.baidu.com/?rsv_bp=0&tn=baidu&rsv_spt=3&ie=utf-8&rsv_sug3=4&rsv_sug4=549&rsv_sug1=3&rsv_sug2=0&inputT=13632";
		
		long timeStamp=System.currentTimeMillis();  //记录请求时的时间戳
//		CustomHttpConstant.defaultHttpPort=80;
		String result=CustomHttpClient.requestForString(getContext(), url, map, CustomHttpConstant.GET, HTTP.UTF_8, timeStamp);
	
		System.out.println(result);
		
//		CustomHttpClient.abort(timeStamp);//取消时终止某一时间戳上的请求
	}
	
	//测试加密
	public void testDES(){
		String data = "中国ABCabc123";
		DESUtil.ECBModel ecb=DESUtil.getECBModelSingleInstance(null);
		
		String str3 = ecb.desEncodeECB(data); //加密
		String str4 = ecb.desDecodeECB(str3); //解密
		Log.d("tag", "密文:"+str3+"明文:"+str4);
		
		byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8 };
		DESUtil.CBCModel cbc=DESUtil.getCBCModelSingleInstance(null, null);
		String str5 = cbc.des3EncodeCBC(data);
		String str6 = cbc.des3DecodeCBC(str5);
		System.out.println(str5+" "+str6);
		Log.d("tag", "密文:"+str5+" 明文:"+str6);
	}
	
	@SuppressLint("SimpleDateFormat")
	public void testJson(){
		try {
			String dateformat="yyyy-MM-dd HH:mm:ss";
			
			Map<String, List<User>> map=new HashMap<String, List<User>>();
			List<User> list=new ArrayList<User>();
			User user=new User(1234L, "mz", 24, new SimpleDateFormat(dateformat).parse("1999-9-9 9:9:9"), 170, '男', null, 432524L, 63.0);
			list.add(user);
			user=new User(1235L, "xf", 23, new SimpleDateFormat(dateformat).parse("1991-1-1 1:1:1"), 165, '女', null, 412613L, 63.0);
			list.add(user);
			map.put("list1", list);
			
			String json=GsonUtil.toJson(list);
			List<User> map1=GsonUtil.fromJson(json, new TypeToken<List<User>>(){});
			
//			String json2=JsonHelper.toJSON(user).toString();
//			String json3=JsonHelper.toJson(list).toString();
//			User user3=JsonHelper.fromJson(json2, User.class);
			
			Gson gson=new Gson();
			String jsonStr=gson.toJson(user);
			System.out.println(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void testXMLParser(){
		try {
			CustomSaxParser.dateFormat="yyyy-MM-dd";
			List<User> list=CustomSaxParser.parse(getContext().getAssets().open("User.xml"), "user", User.class);
			System.out.println(list);
			
			List<User> list2=CustomSaxParser.parse(getContext().getResources().openRawResource(R.raw.user), "user", User.class);
			System.out.println(list2);
			
			List<User> list3=CustomPullParser.parse(getContext().getAssets().open("User.xml"), "user", User.class);
			System.out.println(list3);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testSharedPreferences(){
		SharedPreferencesUtil util=new SharedPreferencesUtil(getContext());
		System.out.println("xml:"+util.getString("aaa"));
		util.putString("aaa", "abcdefgh");
		System.out.println("xml:"+util.getString("aaa"));
		
		
		util.putString("bbb", "bbb");
		System.out.println("xml:"+util.getString("aaa"));
		System.out.println("xml:"+util.getString("bbb"));
	}
	
	
	public void test(){
		String a=null;
		a="{\"abc\":123}";
		
		try {
			JSONObject obj=new JSONObject(a);
			long id=obj.getLong("abc");
			System.out.println("abc:"+id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
}


