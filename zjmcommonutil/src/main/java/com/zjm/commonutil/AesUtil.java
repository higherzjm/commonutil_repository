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
       String value="��������";
        String enValue=dataEncryption(value,key);
        System.out.println("enValue ���ܽ��:"+enValue);
        String decValue=dataDecryption(enValue,key);
        System.out.println("decValue ���ܽ��:"+decValue);
    }
    /**
     * @Description: ���������Կ
     **/
    public static String generateSecretkey() {
        // ����key//��������ָ���㷨��Կ��KeyGenerator����
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("DES");
        } catch (Exception e) {
            e.printStackTrace();
        }
        keyGenerator.init(56);//��ʼ������Կ������,ʹ�����ȷ������Կ��С
        SecretKey secretKey = keyGenerator.generateKey();//����һ����Կ
        byte[] bs = secretKey.getEncoded();
        String encodeHexString = Hex.encodeHexString(bs);
        return encodeHexString;
    }

    /**
     * @param plainValue  ����
     * @param securityKey ��Կ
     * @Description: ���ݼ���
     * @Date: 2021/4/19
     **/
    public static String dataEncryption(String plainValue, String securityKey) {
        byte[] result = null;
        DESKeySpec desKeySpec = null; //ʵ����DES��Կ����
        try {
            desKeySpec = new DESKeySpec(Hex.decodeHex(securityKey));
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES"); //ʵ������Կ����
            Key convertSecretKey = factory.generateSecret(desKeySpec); //������Կ
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            // ����
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(plainValue.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Hex.encodeHexString(result);
    }

    /**
     * @param securityValue ����
     * @param securityKey   ��Կ
     * @Description: ���ݽ���
     * @Date: 2021/4/19
     **/
    public static String dataDecryption(String securityValue, String securityKey) {
        String desStr = "";
        try {
            DESKeySpec desKeySpec = new DESKeySpec(Hex.decodeHex(securityKey)); //ʵ����DES��Կ����
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES"); //ʵ������Կ����
            Key convertSecretKey = factory.generateSecret(desKeySpec); //������Կ
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // ����
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            byte[] desResult = cipher.doFinal(Hex.decodeHex(securityValue));
            desStr = new String(desResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desStr;
    }

}
