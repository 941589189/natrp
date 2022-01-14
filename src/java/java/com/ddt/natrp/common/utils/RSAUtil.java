package com.ddt.natrp.common.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPublicKey;

/**
 * @Desc RAS加密工具
 */
public class RSAUtil {
    private static final KeyPair KEYPAIR= initKey();
    private static KeyPair initKey() {
        try {
            Provider provider =new BouncyCastleProvider();
            Security.addProvider(provider);
            SecureRandom random = new SecureRandom();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", provider);
            generator.initialize(256,random);
            return generator.generateKeyPair();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String generateBase64PublicKey() {
        PublicKey publicKey = (RSAPublicKey)KEYPAIR.getPublic();
        return new String(Base64.encodeBase64(publicKey.getEncoded()));
    }
    public static String decryptBase64(String string) {
        return new String(decrypt(Base64.decodeBase64(string.getBytes())));
    }
    private static byte[] decrypt(byte[] byteArray) {
        try {
            Provider provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
            Security.addProvider(provider);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
            PrivateKey privateKey = KEYPAIR.getPrivate();
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] plainText = cipher.doFinal(byteArray);
            return plainText;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}

