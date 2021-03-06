package com.melon.reflect;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class CustomField {
	public static String dateFormat="yyyy-MM-dd HH:mm:ss SSS";

	@SuppressLint("SimpleDateFormat")
	public static void setValue(Object bean, String value, Field field) throws IllegalAccessException {
		Class c=field.getType();
		if(value==null || "".equals(value.trim())){
			if(c.equals(String.class) || c.equals(Integer.class) || c.equals(Float.class) || c.equals(Double.class) || c.equals(Byte.class) || c.equals(Short.class) || 
					c.equals(Long.class) || c.equals(Boolean.class) || c.equals(Date.class) || c.equals(Character.class)){
				field.set(bean, null);
			}else if(c.equals(int.class) || c.equals(float.class) || c.equals(double.class) || c.equals(byte.class) || 
					c.equals(short.class) || c.equals(long.class) || c.equals(char.class)){
				field.set(bean, 0);
			}else if(c.equals(boolean.class)){
				field.set(bean, false);
			}
			return;
		}
		
		if(c.equals(Integer.class) || c.equals(int.class)){
			field.set(bean, Integer.parseInt(value));
		}else if(c.equals(String.class)){
			field.set(bean, value);
		}else if(c.equals(Float.class) || c.equals(float.class)){
			field.set(bean, Float.parseFloat(value));
		}else if(c.equals(Double.class) || c.equals(double.class)){
			field.set(bean, Double.parseDouble(value));
		}else if(c.equals(Byte.class) || c.equals(byte.class)){
			field.set(bean, Byte.parseByte(value));
		}else if(c.equals(Short.class) || c.equals(short.class)){
			field.set(bean, Short.parseShort(value));
		}else if(c.equals(Long.class) || c.equals(long.class)){
			field.set(bean, Long.parseLong(value));
		}else if(c.equals(Boolean.class) || c.equals(boolean.class)){
			field.setBoolean(bean, Boolean.parseBoolean(value));
		}else if(c.equals(Date.class)){
			try {
				field.set(bean, new SimpleDateFormat(dateFormat).parseObject(value));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if(c.equals(char.class) || c.equals(Character.class)){
			field.set(bean, value.charAt(0));
		}
	}
	
}
