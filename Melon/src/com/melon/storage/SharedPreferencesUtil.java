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
	
	//��ȡ�ַ���ֵ
	public String getString(String key) {
		return sharedPreferences.getString(key, null);
	}
	
	// �����ַ���ֵ
	public boolean putString(String key, String value) {
		Editor editor = sharedPreferences.edit();// ��ȡ�༭��
		editor.putString(key, value);
		return editor.commit(); // ��������
	}
	
	//��ȡ����ֵ
	public int getInt(String key) {
		return sharedPreferences.getInt(key, 0);
	}
	
	//��������ֵ
	public boolean putInt(String key, int value){
		Editor editor=sharedPreferences.edit();
		editor.putInt(key, value);
		return editor.commit();
	}
	
	//��ȡ����ֵ
	public boolean getBoolean(String key){
		return sharedPreferences.getBoolean(key, false);
	}
	
	//���沼��ֵ
	public boolean putBoolean(String key, boolean value) {
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}
	
	//��ȡ����ֵ
	public float getFloat(String key){
		return sharedPreferences.getFloat(key, 0.0f);
	}
	
	//���渡��ֵ
	public boolean pubFloat(String key, float value){
		Editor editor=sharedPreferences.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}
	
}


