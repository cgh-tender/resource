package cn.com.cgh.encrypt;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class PasswordUtil {
	
	/**********************DES加密**********************/
	/** 
	* 解密 
	* @param value 数据源
	* @param _key 密钥，长度必须是8的倍数
	* @return 返回解密后的原始数据 
	* @throws Exception 
	*/
	public String decrypt(String value,String _key)throws Exception { 
		byte[] src = this.hex2byte(value.getBytes());
		byte[] key = _key.getBytes();
		SecureRandom sr = new SecureRandom(); 
		DESKeySpec dks = new DESKeySpec(key); 
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
		SecretKey securekey = keyFactory.generateSecret(dks); 
		Cipher cipher = Cipher.getInstance("DES"); 
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr); 
		byte[] b = cipher.doFinal(src);
		return new String(b); 
	} 

    /** 
	 * 二行制转字符串 
	 * @return
     * @throws Exception 
	 */
	public String byte2hex(String value, String _key) throws Exception {
		byte b[] = this.encrypt(value, _key);
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	/** 
	* 加密 
	* @param value 数据源
	* @param _key 密钥，长度必须是8的倍数
	* @return 返回加密后的数据 
	* @throws Exception 
	*/ 
	private byte[] encrypt(String value, String _key)throws Exception { 
		byte[] src = value.getBytes();
		byte[] key = _key.getBytes();
		//DES算法要求有一个可信任的随机数源 
		SecureRandom sr = new SecureRandom(); 
		// 从原始密匙数据创建DESKeySpec对象 
		DESKeySpec dks = new DESKeySpec(key); 
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成 
		// 一个SecretKey对象 
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
		SecretKey securekey = keyFactory.generateSecret(dks); 
		// Cipher对象实际完成加密操作 
		Cipher cipher = Cipher.getInstance("DES"); 
		// 用密匙初始化Cipher对象 
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr); 
		return cipher.doFinal(src); 
	} 

	public byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException("输入不正确");
		} else {
			byte[] b2 = new byte[b.length / 2];
			for (int n = 0; n < b.length; n += 2) {
				String item = new String(b, n, 2);
				b2[n / 2] = (byte) Integer.parseInt(item, 16);
			}
			return b2;
		}
	}
	
	/**********************MD5加密**********************/
	
	/**
	 * MD5加密
	 * @param value
	 * @return 返回加密后的String
	 * @author 马鑫
	 * @see
	 */
	public String getMD5String (String value){
		byte[] source = value.getBytes();
		String arg = null;
		// 用来将字节转换成 16 进制表示的字符
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'}; 
		try{
			MessageDigest md5 = MessageDigest.getInstance( "MD5" );
			md5.update( source );
			byte tmp[] = md5.digest();                    // MD5 的计算结果是一个 128 位的长整数，用字节表示就是 16 个字节
			char str[] = new char[16 * 2];                // 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16 进制需要 32 个字符
			int k = 0;                                    // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) {                // 从第一个字节开始，对 MD5 的每一个字节 转换成 16 进制字符的转换
				byte byte0 = tmp[i];                      // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // 取字节中高 4 位的数字转换, '>>>' 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf];        // 取字节中低 4 位的数字转换
			}
			arg = new String(str);                        // 换后的结果转换为字符串
		}catch( Exception e )
			{
				e.printStackTrace();
			}
		return arg;
	}
	
	public static void main(String args[])  {
		String KEY = "DWSOFTMX";
		PasswordUtil pjm = new PasswordUtil();
		String pwd;
		try {
			pwd = pjm.byte2hex("@zhlmcl", KEY);
			

			System.out.println("密文:" + pwd);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	
}
