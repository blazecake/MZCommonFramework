package com.melon.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

public class DeviceUtil {

	/** Obtain app version number */
	public static String getVersionName(Context context) throws NameNotFoundException {
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
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
	}
	
	/** Access to the local IMSI number */
	public static String getImsi(Context context) {
		TelephonyManager phoneMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = phoneMgr.getSubscriberId();
		return imsi;
	}
	
}
