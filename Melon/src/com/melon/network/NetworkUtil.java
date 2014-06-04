package com.melon.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @Description: ���繤����
 * @author maozhou
 * @date 2013-10-10 ����9:10:46
 */
public class NetworkUtil {
	/**
	 * ������
	 */
	public final static int NO_NETWORK=-1;
	
	/**
	 * wifi����
	 */
	public final static int WIFI = 1;
	
	/**
	 * wap����
	 */
	public final static int CMWAP = 2;
	
	/**
	 * net����
	 */
	public final static int CMNET = 3;
	
	/**
	 * ��ȡ��ǰ������״̬ 
	 * @param context
	 * @return -1��û������  1��WIFI����2��wap����3��net����
	 */
	public static int getAPNType(Context context) {
		int netType = NO_NETWORK;
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();

		if (nType == ConnectivityManager.TYPE_MOBILE) {
			if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
				netType = CMNET;
			} else {
				netType = CMWAP;
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = WIFI;
		}
		return netType;
	}
	
}
