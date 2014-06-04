package com.melon.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.melon.application.A;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

public class FileUtil {
	
	/** ��ȡ�洢�ļ��� */
	public static String getFolder(String folderName){
		String folder=Environment.getExternalStorageDirectory().getAbsolutePath();
		File file=new File(folder+File.separator+folderName);
		if(!file.exists()){
			file.mkdir();
		}
		return file.getAbsolutePath();
	}
	
	/** ��ȡ��ǰʱ��� */
	@SuppressLint("SimpleDateFormat")
	public static String getTimeStamp(){
		Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dateFormat.format(date);
	}
	
	/** ϵͳ�����ļ��� */
    public static String getImgName(String title) {
    	if(title==null || title.trim().length()==0){
    		return getTimeStamp() + ".png";
    	}else{
    		return title+"_"+getTimeStamp() + ".png";
    	}
    }
	
	public static String saveImgFile(Context context, Bitmap bmp, String title) {
		//���Ԫ����
		String name= getImgName(title);
		String dirFile = getFolder(A.folderName);//��ȡ���·��
		File file = new File(dirFile +File.separator+ name);
		try {
			file.createNewFile();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}
	
}
