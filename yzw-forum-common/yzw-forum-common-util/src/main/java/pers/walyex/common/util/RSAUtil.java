package pers.walyex.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA算法工具类
 *
 * @author Waldron Ye
 * @date 2019/6/22 18:40
 */
public class RSAUtil {
    /**
     * 指定加密算法为RSA
     */
    private static final String ALGORITHM = "RSA";
    /**
     * 密钥长度，用来初始化
     */
    private static final int KEYSIZE = 1024;

    /**
     * RSA分段最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA分段最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 生成密钥对
     *
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static String[] generateKeyPair() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEYSIZE, new SecureRandom());

        /** 生成密匙对 */
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        /** 得到公钥 */
        Key publicKey = keyPair.getPublic();

        /** 得到私钥 */
        Key privateKey = keyPair.getPrivate();
        return new String[]{Base64.encodeBase64String(publicKey.getEncoded()), Base64.encodeBase64String(privateKey.getEncoded())};
    }

    /**
     * 加密
     *
     * @param data
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {

        return useKey(data, getPublicKey(key), Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByKey(byte[] data, String key) throws Exception {
        return useKey(data, getPrivateKey(key), Cipher.DECRYPT_MODE);
    }

    /**
     * 得到公钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(key);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, Key key) throws Exception {
        return useKey(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByKey(byte[] data, Key key) throws Exception {
        return useKey(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 核心处理方法
     *
     * @param data
     * @param key
     * @param mode
     * @return
     * @throws Exception
     */
    private static byte[] useKey(byte[] data, Key key, int mode) throws Exception {
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(mode, key);

        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int offSet = 0;
        byte[] cache;
        int i = 0;
        int block = Cipher.DECRYPT_MODE == mode ? MAX_DECRYPT_BLOCK : MAX_ENCRYPT_BLOCK;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > block) {
                cache = cipher.doFinal(data, offSet, block);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * block;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 解密
     *
     * @param privateKey   私钥
     * @param bizPublicKey 业务系统公钥
     * @param json         要解密的json数据
     * @return string
     * @throws Exception
     */
    public static String decry(PrivateKey privateKey, PublicKey bizPublicKey, String json) throws Exception {

        Map<String, String> map = FastJsonUtil.jsonStrToMap(json);
        String charset = StringUtils.isBlank(map.get("charset")) ? "UTF-8" : map.get("charset");
        String bizContent = map.get("bizContent");
        String sign = map.get("sign");
        String appId = map.get("appId");
        String timestamp = map.get("timestamp");

        Map<String, String> parameterMap = new HashMap<>(8);
        parameterMap.put("appId", appId);
        parameterMap.put("timestamp", timestamp);
        parameterMap.put("sign", sign);
        parameterMap.put("bizContent", bizContent);
        parameterMap.put("charset", charset);

        byte[] original = RSAUtil.decryptByKey(Base64.decodeBase64(bizContent), privateKey);
        byte[] data = SignUtil.createLinkString(parameterMap).getBytes(charset);
        if (SignUtil.verifySign(bizPublicKey, data, Base64.decodeBase64(sign))) {
            return new String(original, charset);
        }
        return null;
    }


    /**
     * 加密
     *
     * @param privateKey   私钥
     * @param bizPublicKey 公钥
     * @param charset      字符
     * @param appId        应用id
     * @param json         json
     * @return string
     * @throws Exception
     */
    public static String encrypt(PrivateKey privateKey, PublicKey bizPublicKey, String charset, String appId, String json) throws Exception {

        try {
            charset = StringUtils.isBlank(charset) ? "UTF-8" : charset;
            // 加密后字节
            byte[] ev = RSAUtil.encryptByPublicKey(json.getBytes(charset), bizPublicKey);
            // base64编码
            String bizContent = Base64.encodeBase64String(ev);
            String timestamp = DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);

            Map<String, String> parameterMap = new HashMap<>(8);
            parameterMap.put("appId", appId);
            parameterMap.put("timestamp", timestamp);
            parameterMap.put("bizContent", bizContent);
            parameterMap.put("charset", charset);

            String stringTmp = SignUtil.createLinkString(parameterMap);
            // 生成签名
            String sign = SignUtil.sign(privateKey, stringTmp,
                    charset);
            parameterMap.put("sign", sign);

            return FastJsonUtil.toJson(parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
