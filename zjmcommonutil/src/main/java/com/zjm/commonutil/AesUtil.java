package com.zjm.commonutil;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

/**
 * ��ȫ����΢����ʹ�õ�AES���ܷ�ʽ��
 * aes��key������256byte��������32���ַ���������ʹ��AesKit.genAesKey()������һ��key
 */
public class AesUtil {
    public static void main(String[] args) throws Exception {
       String key=genAesKey();
       String value="��������";
        byte[] enValue=encrypt(value,key);
        String enValStr=new String(enValue);
        System.out.println("enValue ���ܽ��:"+enValStr);
        String decValue=decryptToStr(enValStr.getBytes(Charsets.UTF_8),key);
        System.out.println("decValue ���ܽ��:"+decValue);
    }

    private AesUtil() {
    }

    public static String genAesKey() {
        return StringUtil.random(32);
    }

    public static byte[] encrypt(byte[] content, String aesTextKey) {
        return encrypt(content, aesTextKey.getBytes(Charsets.UTF_8));
    }

    public static byte[] encrypt(String content, String aesTextKey) {
        return encrypt(content.getBytes(Charsets.UTF_8), aesTextKey.getBytes(Charsets.UTF_8));
    }

    public static byte[] encrypt(String content, java.nio.charset.Charset charset, String aesTextKey) {
        return encrypt(content.getBytes(charset), aesTextKey.getBytes(Charsets.UTF_8));
    }

    public static byte[] decrypt(byte[] content, String aesTextKey) {
        return decrypt(content, aesTextKey.getBytes(Charsets.UTF_8));
    }

    public static String decryptToStr(byte[] content, String aesTextKey) {
        return new String(decrypt(content, aesTextKey.getBytes(Charsets.UTF_8)), Charsets.UTF_8);
    }

    public static String decryptToStr(byte[] content, String aesTextKey, java.nio.charset.Charset charset) {
        return new String(decrypt(content, aesTextKey.getBytes(Charsets.UTF_8)), charset);
    }

    public static byte[] encrypt(byte[] content, byte[] aesKey) {

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(aesKey);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            return cipher.doFinal(Pkcs7Encoder.encode(content));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(byte[] encrypted, byte[] aesKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(aesKey);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            return Pkcs7Encoder.decode(cipher.doFinal(encrypted));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * �ṩ����PKCS7�㷨�ļӽ��ܽӿ�.
     */
    static class Pkcs7Encoder {
        static int BLOCK_SIZE = 32;

        static byte[] encode(byte[] src) {
            int count = src.length;
            // ������Ҫ����λ��
            int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
            if (amountToPad == 0) {
                amountToPad = BLOCK_SIZE;
            }
            // ��ò�λ���õ��ַ�
            byte pad = (byte) (amountToPad & 0xFF);
            byte[] pads = new byte[amountToPad];
            for (int index = 0; index < amountToPad; index++) {
                pads[index] = pad;
            }
            int length = count + amountToPad;
            byte[] dest = new byte[length];
            System.arraycopy(src, 0, dest, 0, count);
            System.arraycopy(pads, 0, dest, count, amountToPad);
            return dest;
        }

        static byte[] decode(byte[] decrypted) {
            int pad = (int) decrypted[decrypted.length - 1];
            if (pad < 1 || pad > BLOCK_SIZE) {
                pad = 0;
            }
            if (pad > 0) {
                return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
            }
            return decrypted;
        }
    }
}
