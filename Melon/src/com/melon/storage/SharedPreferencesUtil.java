package com.melon.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author maozhou
 * @date2013-12-6
 *
 */
public class SharedPreferencesUtil {
	public static final String preferencesName = "sharedPreferences";
	private SharedPreferences sharedPreferences = null;
	
	public SharedPreferencesUtil(Context context) {
		sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
	}
	
	//获取字符串值
	public String getString(String key) {
		return sharedPreferences.getString(key, null);
	}
	
	// 保存字符串值
	public boolean putString(String key, String value) {
		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.putString(key, value);
		return editor.commit(); // 保存数据
	}
	
	//获取整型值
	public int getInt(String key) {
		return sharedPreferences.getInt(key, 0);
	}
	
	//保存整型值
	public boolean putInt(String key, int value){
		Editor editor=sharedPreferences.edit();
		editor.putInt(key, value);
		return editor.commit();
	}
	
	//获取布尔值
	public boolean getBoolean(String key){
		return sharedPreferences.getBoolean(key, false);
	}
	
	//保存布尔值
	public boolean putBoolean(String key, boolean value) {
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}
	
	//获取浮点值
	public float getFloat(String key){
		return sharedPreferences.getFloat(key, 0.0f);
	}
	
	//保存浮点值
	public boolean pubFloat(String key, float value){
		Editor editor=sharedPreferences.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}
	
}


