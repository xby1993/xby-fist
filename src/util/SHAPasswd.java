package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHAPasswd {
	static String algorithm = "SHA-1";

	/**
	 * MD5加密，返回byte[]类型
	 * 
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] encrypt(byte[] data)
			throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.update(data);
		return digest.digest();
	}

	/**
	 * 把MD5加密数据转换String类型
	 * 
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String encryptString(byte[] data)
			throws NoSuchAlgorithmException {
		String str = "";
		String str16;
		System.out.println(data.length);
		for (int i = 0; i < data.length; i++) {
			str16 = Integer.toHexString(0xFF & data[i]);
			if (str16.length() == 1) {
				str = str + "0" + str16;
			} else {
				str = str + str16;
			}

		}
		return str;
	}
	public static String encry(String str){
		byte[] b;
		try {
			b = encrypt(str.getBytes());
			return encryptString(b);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
