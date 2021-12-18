package cn.com.cgh.encrypt;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * @author haider
 * @date 2021年04月22日 10:47
 */
@Slf4j
public class TestMain {
    public static void main(String[] args) {
        System.out.println(StringUtils.isBlank("null"));
        String key = "audit10086";
        String phone = "13871005069";
        String key1 = "B5584A5D9B61C23BE52CA1168C9110894C4FE9ABC8E9F251";
        if (StringUtils.isNotBlank(key) && !"null".equalsIgnoreCase(key)){
            System.out.println("signToken :  "+ new PasswordUtil().getMD5String(phone+key));
            try {
                DES des = new DES(0);
                String encrypt = DES.encrypt(phone);
                System.out.println("loginPhone : "+ encrypt);
//                System.out.println("loginPhone : "+ DES.decrypt(encrypt));
                SecretKey deSede1 = KeyGenerator.getInstance("DESede").generateKey();
                SecretKey deskey = new SecretKeySpec(des.hex2byte(key1), "DESede");
                Cipher deSede = Cipher.getInstance("DESede");
                deSede.init(Cipher.ENCRYPT_MODE, deSede1);
                System.out.println(des.byte2hex(deSede.doFinal(phone.getBytes())));

                System.out.println("===============");
                KeyGenerator ke = KeyGenerator.getInstance("DES");
                ke.init(56);
                SecretKey deSede2 = ke.generateKey();

                Cipher cipher = Cipher.getInstance("DES");
                cipher.init(Cipher.ENCRYPT_MODE,deSede2);
                byte[] update = deSede.doFinal(phone.getBytes());
                String encode = Base64.getEncoder().encodeToString(update);
                System.out.println(encode);
                cipher.init(Cipher.DECRYPT_MODE, deSede2);
                System.out.println(cipher.doFinal(update).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main1(String[] args) throws JSONException {
        String a = "[{\"ownerid\":2915816952,\"objtype\":6,\"objid\":856188930314735616,\"value\":\"{\\\"text\\\":\\\"晚安\\\"}\",\"async\":false,\"strid\":\"856188930314735616\"},{\"ownerid\":2915816952,\"objtype\":6,\"objid\":856188930314735616,\"value\":\"{\\\"text\\\":\\\"晚安\\\"}\",\"async\":false,\"strid\":\"856188930314735616\"}]";
        List<Map> jsonArray = JSONArray.parseArray(a, Map.class);
        jsonArray.forEach(map -> map.forEach((k, v)->{
            if (StringUtils.equalsIgnoreCase("value",String.valueOf(k))){
                try {
                    Map parse = JSONObject.parseObject(String.valueOf(v));
                    parse.forEach((k1,v1)-> System.out.println(k + " : " + k1 + " : " + v1));
                } catch (Exception e) {
                    System.out.println("解析 objid=" + map.get("objid") +"的value=" + v + " 异常 "+e.getMessage());
                }
            }else {
                System.out.println(k + " : " + v);
            }
        }));
    }
}
