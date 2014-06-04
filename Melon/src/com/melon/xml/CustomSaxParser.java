package com.melon.xml;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.melon.reflect.CustomField;

/**
 * <strong>xml中节点名称需与clazz对象属性需相同,否则无法保证解析正确
 * 不能深度解析，即在类中嵌套另一对象无法解析 </strong>
 * @Description: SAX解析通用类
 * @author maozhou
 * @date 2013-8-13 下午7:25:19
 */
public class CustomSaxParser extends CustomField{
	
	public static void setDateFormat(String dateFormat) {
		CustomSaxParser.dateFormat = dateFormat;
	}
	
	public static String getDateFormat() {
		return dateFormat;
	}

	
	/**
	 * 通过文件对象解析xml 
	 * @param f 需解析的xml文件对象
	 * @param nodeName xml起始节点名称
	 * @param clazz 解析xml装入对象的class
	 * @return clazz类型的对象集合
	 * @throws Exception
	 */
	public static <T> List<T> parse(File f, String nodeName, Class<T> clazz) throws Exception{
		SAXParserFactory factory=SAXParserFactory.newInstance();
		SAXParser parse=factory.newSAXParser();
		SaxHandler<T> sax=new SaxHandler<T>(nodeName, clazz);
		parse.parse(f, sax);
		return sax.list;
	}
	
	/**
	 * 通过输入流解析xml 
	 * @param <T>
	 * @param is 需解析的xml输入流
	 * @param nodeName xml起始节点名称
	 * @param clazz 解析xml装入对象的class
	 * @return clazz类型的对象集合
	 * @throws Exception
	 */
	public static <T> List<T> parse(InputStream is, String nodeName, Class<T> clazz) throws Exception{
		SAXParserFactory factory=SAXParserFactory.newInstance();
		SAXParser parse=factory.newSAXParser();
		SaxHandler<T> sax=new SaxHandler<T>(nodeName, clazz);
		parse.parse(is, sax);
		return sax.list;
	}
	
	/**
	 * 通过输入源解析xml 
	 * @param is 需解析的xml输入源
	 * @param nodeName xml起始节点名称
	 * @param clazz 解析xml装入对象的class
	 * @return clazz类型的对象集合
	 * @throws Exception
	 */
	public static <T> List<T> parse(InputSource is, String nodeName, Class<T> clazz) throws Exception{
		SAXParserFactory factory=SAXParserFactory.newInstance();
		SAXParser parse=factory.newSAXParser();
		SaxHandler<T> sax=new SaxHandler<T>(nodeName, clazz);
		parse.parse(is, sax);
		return sax.list;
	}
	
	/**
	 * 通过uri解析xml 
	 * @param is 需解析的xml的uri
	 * @param nodeName xml起始节点名称
	 * @param clazz 解析xml装入对象的class
	 * @return clazz类型的对象集合
	 * @throws Exception
	 */
	public static <T> List<T> parse(String uri, String nodeName, Class<T> clazz) throws Exception{
		SAXParserFactory factory=SAXParserFactory.newInstance();
		SAXParser parse=factory.newSAXParser();
		SaxHandler<T> sax=new SaxHandler<T>(nodeName, clazz);
		parse.parse(uri, sax);
		return sax.list;
	}
	
	private static final class SaxHandler<T> extends DefaultHandler{
		public List<T> list=null;
		String currentTag=null; //当前节点标记
		String nodeName=null; //开始解析节点标记
		
		T bean=null;
		Class<?> clazz=null;
		
		public <T> SaxHandler(String nodeName, Class<T> clazz) {
			this.nodeName=nodeName;
			this.clazz=clazz;
		}
		
		@Override
		public void startDocument() throws SAXException {
			list=new ArrayList<T>();
		}
	
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			try {
				if(qName.equals(nodeName)){
					bean=(T) clazz.newInstance();
					clazz=bean.getClass();
				}
				if(attributes!=null && list!=null){
					String attName=null;
					String attValue=null;
					for(int i=0; i<attributes.getLength();i++){
						attName=attributes.getQName(i);
						attValue=attributes.getValue(i);
						
						Field field=clazz.getDeclaredField(attName);
						field.setAccessible(true);
						setValue(bean, attValue, field);
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			currentTag=qName;
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			try {
				String value=new String(ch, start, length);
				if(currentTag!=null){
					if(value!=null && value.length()!=0 && !value.equals("\n")){
						Field field=clazz.getDeclaredField(currentTag);
						field.setAccessible(true);
						setValue(bean, value, field);
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			currentTag=null;
		}
	
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if(qName.equals(nodeName)){
				list.add(bean);
				bean=null;
			}
			currentTag=null;
		}
	}
	
}
