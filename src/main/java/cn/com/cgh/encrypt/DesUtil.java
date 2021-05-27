package cn.com.cgh.encrypt;


import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;

/**
 * des加密解密
 * 
 */
public class DesUtil {

	private Key key;

	public DesUtil(String str) {
		setKey(str);// 生成密匙
	}

	public DesUtil() {
		setKey("dwsoftmx");
	}

	/**
	 * 根据参数生成KEY
	 */
	public void setKey(String strKey) {
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			this.key  = keyFactory.generateSecret(new DESKeySpec(strKey.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		}
	}

	/**
	 * 加密String明文输入,String密文输出
	 */
	public String encrypt(String strMing) {
		byte[] byteMi;
		byte[] byteMing;
		String strMi;
		try {
			byteMing = strMing.getBytes(StandardCharsets.UTF_8);
			byteMi = this.getEncCode(byteMing);
			strMi = DatatypeConverter.printBase64Binary(byteMi);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		}
		return strMi;
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 */
	public String decrypt(String strMi) {
		byte[] byteMing;
		byte[] byteMi;
		String strMing;
		try {
			byteMi = DatatypeConverter.parseBase64Binary(strMi);
			byteMing = this.getDesCode(byteMi);
			strMing = new String(byteMing, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		}
		return strMing;
	}

	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 */
	private byte[] getEncCode(byte[] byteS) {
		byte[] byteFina;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key,SecureRandom.getInstance("SHA1PRNG"));
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		}
		return byteFina;
	}

	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 */
	private byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key,SecureRandom.getInstance("SHA1PRNG"));
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing SqlMap class. Cause: " + e);
		}
		return byteFina;
	}
	
	public static void main(String[] args)  {
		DesUtil des = new DesUtil();

		String str1 = "1qaz!QAZ";
		// DES加密
		String str2 = des.encrypt(str1);
		DesUtil des1 = new DesUtil();
		String deStr = des1.decrypt(str2);
		System.out.println("密文:" + str2);
		// DES解密
		System.out.println("明文:" + deStr);
	}
}
