package com.melon.xml;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.melon.reflect.CustomField;

/**
 * xml中节点名称需与clazz对象属性需相同,否则无法保证解析正确
 * 不能深度解析，即在类中嵌套另一对象无法解析
 * @Description: pull解析
 * @author maozhou
 * @date 2013-10-28 下午3:31:55
 */
public class CustomPullParser extends CustomField{
	public static String inputEncoding="utf-8";
	
	public static <T> List<T> parse(InputStream is, String nodeName, Class<T> clazz) throws Exception {
		List<T> list=null;
		T bean=null;
		
		XmlPullParser parser=Xml.newPullParser();
		parser.setInput(is, inputEncoding);
		int eventType=parser.getEventType();
		while(eventType!=XmlPullParser.END_DOCUMENT){
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				list=new ArrayList<T>();
				break;
			case XmlPullParser.START_TAG:
				String name=parser.getName();
				if(bean!=null){
					String value=parser.nextText();
					Field field=clazz.getDeclaredField(name);
					field.setAccessible(true);
					setValue(bean, value, field);
				}
				
				if(nodeName.equals(name)){
					bean=clazz.newInstance();
				}
				
				for(int i=0; i<parser.getAttributeCount(); i++){
					String attName=parser.getAttributeName(i);
					Field field=clazz.getDeclaredField(attName);
					field.setAccessible(true);
					String attValue=parser.getAttributeValue(i);
					if(field!=null){
						setValue(bean, attValue, field);
					}
				}
				break;
			case XmlPullParser.END_TAG:
				if(nodeName.equals(parser.getName())){
					list.add(bean);
					bean=null;
				}
				break;
			default:
				break;
			}
			eventType=parser.next();
		}
		return list;
	}

}
