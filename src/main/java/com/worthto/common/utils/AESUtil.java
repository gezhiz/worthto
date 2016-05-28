/**
 * 
 */
package com.worthto.common.utils;

import org.apache.shiro.codec.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * AES加密解密单元.
 * http://www.cnblogs.com/arix04/archive/2009/10/15/1511839.html
 * http://aub.iteye.com/blog/1133494
 * @author bjqinkan
 */
public class AESUtil {

	private static AESUtil instance = new AESUtil();

	private AESUtil() {
		try {
			Security.addProvider(new BouncyCastleProvider());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static AESUtil getInstance() {
		return instance;
	}

	/**
	 * 加密.
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String key, String content) throws Exception {
		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC"); //"算法/模式/补码方式"
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
			return new String(Base64.encode(encrypted), "UTF-8");//此处使用BASE64做转码功能，同时能起到2次加密的作用。
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * 解密.
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String key, String content) throws Exception {
		try {
			byte[] raw = key.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted = Base64.decode(content);
			try {
				byte[] original = cipher.doFinal(encrypted);
				String originalString = new String(original, "utf-8");
				return originalString;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			AESUtil aes = AESUtil.getInstance();
			String en = aes.encrypt("abcdefgggggggggg", "qinkan");
			System.out.println(en);
			String de = aes.decrypt("abcdefgggggggggg", en);
			System.out.println(de);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
