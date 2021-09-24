package com.zjm.commonutil;


import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

/**
 */
public class AesUtil {
    public static void main(String[] args){
       String key=generateSecretkey();
       String value="我是张三";
        String enValue=dataEncryption(value,key);
        System.out.println("enValue 加密结果:"+enValue);
        String decValue=dataDecryption(enValue,key);
        System.out.println("decValue 解密结果:"+decValue);
    }
    /**
     * @Description: 随机生成秘钥
     **/
    public static String generateSecretkey() {
        // 生成key//返回生成指定算法密钥的KeyGenerator对象
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("DES");
        } catch (Exception e) {
            e.printStackTrace();
        }
        keyGenerator.init(56);//初始化此密钥生成器,使其具有确定的密钥大小
        SecretKey secretKey = keyGenerator.generateKey();//生成一个密钥
        byte[] bs = secretKey.getEncoded();
        String encodeHexString = Hex.encodeHexString(bs);
        return encodeHexString;
    }

    /**
     * @param plainValue  明文
     * @param securityKey 秘钥
     * @Description: 数据加密
     * @Date: 2021/4/19
     **/
    public static String dataEncryption(String plainValue, String securityKey) {
        byte[] result = null;
        DESKeySpec desKeySpec = null; //实例化DES密钥规则
        try {
            desKeySpec = new DESKeySpec(Hex.decodeHex(securityKey));
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES"); //实例化密钥工厂
            Key convertSecretKey = factory.generateSecret(desKeySpec); //生成密钥
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            // 加密
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(plainValue.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Hex.encodeHexString(result);
    }

    /**
     * @param securityValue 密文
     * @param securityKey   秘钥
     * @Description: 数据解密
     * @Date: 2021/4/19
     **/
    public static String dataDecryption(String securityValue, String securityKey) {
        String desStr = "";
        try {
            DESKeySpec desKeySpec = new DESKeySpec(Hex.decodeHex(securityKey)); //实例化DES密钥规则
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES"); //实例化密钥工厂
            Key convertSecretKey = factory.generateSecret(desKeySpec); //生成密钥
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            byte[] desResult = cipher.doFinal(Hex.decodeHex(securityValue));
            desStr = new String(desResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desStr;
    }

}
