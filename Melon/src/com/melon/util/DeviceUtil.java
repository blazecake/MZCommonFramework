package com.melon.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class DeviceUtil {

	public static String getVersionName(Context context) throws NameNotFoundException {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),
				0);
		String version = packInfo.versionName;
		return version;
	}
	
	@SuppressLint("NewApi")
	public static void setClipboard(Context context, String text) {
		if(getSystemVersion(context)>=android.os.Build.VERSION_CODES.HONEYCOMB){
			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			clipboard.setText(text);
		}else{
			android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			clipboard.setText(text);
		}
	}
	
	public static int getSystemVersion(Context context){
		int currentapiVersion = android.os.Build.VERSION.SDK_INT; 
		return currentapiVersion;
//		if (currentapiVersion >= android.os.Build.VERSION_CODES.FROYO){     
//			// Do something for froyo and above versions 
//		} else{     
//			// do something for phones running an SDK before froyo 
//		}
	}
	
}
