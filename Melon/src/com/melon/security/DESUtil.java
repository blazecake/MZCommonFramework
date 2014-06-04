package com.melon.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import android.annotation.SuppressLint;
import android.util.Base64;

/**
 * @Description: DES加解�? * @author maozhou
 * @date 2013-10-14 下午4:43:08
 */
public class DESUtil {
	
	public static String keyStr="jLj7893JLKpifjklUJpoj8093jkJLjp4";
	public static String ivStr = "12345678";
	
	public static String plaintext_charsetName="utf-8"; //明文编码字符�?	
	private static ECBModel ecbModel=null;
	private static CBCModel cbcModel=null;
	
	private DESUtil() {
	}
	
	/**
	 * 获取ECB模式加解密对�?	 * @param key 加密解密秘钥，为空则使用默认秘钥
	 * @return ECB模式加解密对�?	 */
	public static ECBModel getECBModelSingleInstance(byte[] key){
		if(ecbModel==null || !ecbModel.keyByte.equals(key)){
			ecbModel=new DESUtil().new ECBModel(key);
		}
		return ecbModel;
	}
	
	/**
	 * 获取CBC模式加解密对�?	 * @param key 加解密秘密为空使用默认秘�?
	 * @param iv 初始化向量，至少8字节,为空使用默认初始化向�?	 * @return CBC模式加解密对�?	 */
	public static CBCModel getCBCModelSingleInstance(byte[] key, byte[] iv){
		if(cbcModel==null){
			cbcModel=new DESUtil().new CBCModel(key, iv);
		}
		return cbcModel;
	}
	
	
	/**
	 * @Description: ECB模式的加解密
	 * @author maozhou
	 * @date 2013-10-14 下午3:00:33
	 */
	public class ECBModel{
		Cipher cipher =null;
		Key deskey = null;
		protected byte[] keyByte=Base64.decode(DESUtil.keyStr.getBytes(), Base64.DEFAULT);
		
		public ECBModel(byte[] key) {
			try {
				if(key!=null){
					keyByte=key.clone();
				}
				DESedeKeySpec spec = new DESedeKeySpec(keyByte);
				SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
				deskey = keyfactory.generateSecret(spec);
				cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
		}

		/**
		 * ECB模式加密,不要IV
		 * @param plaintext 明文
		 * @return Base64编码的密�?		 * @throws Exception
		 */
		@SuppressLint("NewApi")
		public synchronized String desEncodeECB(String plaintext) {
			byte[] bOut=null;
			try {
				byte[] data = plaintext.getBytes(DESUtil.plaintext_charsetName);
				cipher.init(Cipher.ENCRYPT_MODE, deskey);
				bOut = cipher.doFinal(data);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
			return Base64.encodeToString(bOut, Base64.DEFAULT);
		}

		/**
		 * ECB模式解密,不要IV
		 * @param ciphertext Base64编码的密�?		 * @return 明文
		 * @throws Exception
		 */
		@SuppressLint("NewApi")
		public synchronized String desDecodeECB(String ciphertext) {
			String plaintext=null;
			byte[] data=Base64.decode(ciphertext, Base64.DEFAULT);
			try {
				cipher.init(Cipher.DECRYPT_MODE, deskey);
				byte[] bOut = cipher.doFinal(data);
				plaintext=new String(bOut, DESUtil.plaintext_charsetName);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return plaintext;
		}
	}
	
	/**
	 * @Description: CBC模式的DES加解�?	 * @author maozhou
	 * @date 2013-10-14 下午3:00:01
	 */
	public class CBCModel{
		Cipher cipher =null;
		Key deskey =null;
		IvParameterSpec ips =null;
		private byte[] keyByte=Base64.decode(DESUtil.keyStr.getBytes(), Base64.DEFAULT);
		private byte[] iv=DESUtil.ivStr.getBytes();
		
		public CBCModel(byte[] key, byte[] iv) {
			if(iv!=null && iv.length<8){
				throw new RuntimeException("iv parameters of at least 8 bytes.");
			}
			if(key!=null){
				keyByte=key.clone();
			}
			if(iv!=null){
				this.iv=iv.clone();
			}
			try{
				DESedeKeySpec spec = new DESedeKeySpec(keyByte);
				SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
				deskey = keyfactory.generateSecret(spec);
				cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
				ips = new IvParameterSpec(this.iv);
			}catch(InvalidKeyException e){
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
		}

		/**
		 * CBC模式加密
		 * @param plaintext  明文
		 * @return Base64编码的密�?		 * @throws Exception
		 */
		@SuppressLint("NewApi")
		public synchronized String des3EncodeCBC(String plaintext) {
			byte[] bOut = null;
			try {
				byte[] data = plaintext.getBytes(DESUtil.plaintext_charsetName);
				cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
				bOut = cipher.doFinal(data);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
			return Base64.encodeToString(bOut, Base64.DEFAULT);
		}

		/**
		 * CBC模式解密
		 * @param ciphertext Base64编码的密�?		 * @return 明文
		 * @throws Exception
		 */
		@SuppressLint("NewApi")
		public synchronized String des3DecodeCBC(String ciphertext) {
			String plaintext=null;
			byte[] data=Base64.decode(ciphertext, Base64.DEFAULT);
			byte[] bOut;
			try {
				cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
				bOut = cipher.doFinal(data);
				plaintext=new String(bOut, DESUtil.plaintext_charsetName);
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
			return plaintext;
		}
	}
}