package cn.com.cgh.encrypt;

import com.sun.crypto.provider.DESedeCipher;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class DES {

    public static int _DES = 1;
    public static int _DESede = 2;
    public static int _Blowfish = 3;

    private Cipher p_Cipher;
    private SecretKey p_Key;
    private String p_Algorithm;
    private static DES _instance;
    private static String hexKey = "B5584A5D9B61C23BE52CA1168C9110894C4FE9ABC8E9F251";

    private void selectAlgorithm(int al) {
        switch (al) {
        default:
        case 1:
            this.p_Algorithm = "DES";
            break;
        case 2:
            this.p_Algorithm = "DESede";
            break;
        case 3:
            this.p_Algorithm = "Blowfish";
            break;
        }
    }

    public DES(int algorithm) throws Exception {
        this.selectAlgorithm(algorithm);
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        this.p_Cipher = Cipher.getInstance(this.p_Algorithm);
    }

    public byte[] getKey() {
        return this.checkKey().getEncoded();
    }

    private SecretKey checkKey() {
        try {
            if (this.p_Key == null) {
                KeyGenerator keygen = KeyGenerator
                        .getInstance(this.p_Algorithm);
                /*
                 * SecureRandom sr = new SecureRandom(key.getBytes());
                 * keygen.init(168, sr);
                 */
                this.p_Key = keygen.generateKey();
            }
        } catch (Exception nsae) {
        }
        return this.p_Key;
    }

    public void setKey(byte[] enckey) {
        this.p_Key = new SecretKeySpec(enckey, this.p_Algorithm);
    }

    public byte[] encode(byte[] data) throws Exception {
        this.p_Cipher.init(Cipher.ENCRYPT_MODE, this.checkKey());
        return this.p_Cipher.doFinal(data);
    }

    public byte[] decode(byte[] encdata, byte[] enckey) throws Exception {
        this.setKey(enckey);
        this.p_Cipher.init(Cipher.DECRYPT_MODE, this.p_Key);
        return this.p_Cipher.doFinal(encdata);
    }

    public String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int i = 0; i < b.length; i++) {
            stmp = Integer.toHexString(b[i] & 0xFF);
            if (stmp.length() == 1) {
                hs += "0" + stmp;
            } else {
                hs += stmp;
            }
        }
        return hs.toUpperCase();
    }

    public byte[] hex2byte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            System.out.println("hex:" + hex + "\nlength:" + hex.length());
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }

    public static String encrypt(String s) throws Exception {
        if (null == _instance) {
            _instance = new DES(DES._DESede);
        }
        _instance.setKey(_instance.hex2byte(hexKey));
        byte[] enc = _instance.encode(s.getBytes()); 
        String hexenc = _instance.byte2hex(enc);
        // System.out.println("hexenc:"+hexenc);
        return hexenc;
    }

    public static String decrypt(String s) throws Exception {
        if (null == _instance) {
            _instance = new DES(DES._DESede);
        }
        return new String(_instance.decode(_instance.hex2byte(s), _instance
                .hex2byte(hexKey)));
    }

}
