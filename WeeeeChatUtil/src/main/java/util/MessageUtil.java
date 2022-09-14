package util;

import org.springframework.util.Base64Utils;
import util.aes.AESUtil;
import util.rsa.RSAUtil;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

/**
 * 消息加密、解密工具
 */
public class MessageUtil {

    /**
     * 分隔符
     */
    private static final String s = "--------------------------";

    /**
     * 加密
     * @param message       消息
     * @param userPublicKey 用户发布的公钥
     * @return 密文
     */
    public static String encrypt(String message, String userPublicKey) {
        try {
            // 加密消息
            String aesEncrypt = AESUtil.encode(AESUtil.key, message);
            // 拿用户的公钥
            RSAPublicKey publicKey = RSAUtil.generatePublicKey(Base64.getDecoder().decode(userPublicKey));
            // 加密AES密钥
            byte[] rsaEncrypt = RSAUtil.encrypt(AESUtil.key.getBytes(), publicKey);
            // 二进制密文转base64
            String rsa = new String(Base64.getEncoder().encode(rsaEncrypt));
            return aesEncrypt + s + rsa + s + "\n";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     * @param aesCipherText AES加密的内容
     * @param rsaCipherText RSA加密的内容(AES密钥)
     * @return json明文
     */
    public static String decode(String aesCipherText, String rsaCipherText) {
        try {
            // 拿私钥
            RSAPrivateKey privateKey = RSAUtil.generatePrivateKey(RSAUtil.getPrivate());
            // 私钥解密出AES密钥
            String aesKey = new String(RSAUtil.decrypt(Base64Utils.decode(rsaCipherText.getBytes()), privateKey));
            // AES密钥解密出消息
            String message = AESUtil.decode(aesKey, aesCipherText);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
