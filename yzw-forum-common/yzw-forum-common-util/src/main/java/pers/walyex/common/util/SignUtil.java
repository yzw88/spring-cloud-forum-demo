package pers.walyex.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 签名工具类
 *
 * @author Waldron Ye
 * @date 2019/6/22 18:51
 */
public class SignUtil {


    /**
     * 验签
     *
     * @param key
     * @param data
     * @param sign
     * @return
     * @throws SignatureException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static boolean verifySign(PublicKey key, byte[] data, byte[] sign) throws SignatureException, InvalidKeyException, NoSuchAlgorithmException {
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(sign);
    }

    /**
     * 加签
     *
     * @param key
     * @param str
     * @param charset
     * @return
     * @throws SignatureException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sign(PrivateKey key, String str, String charset)
            throws SignatureException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(str.getBytes(charset));
        // 验证签名是否正常
        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 将map拼接成参数串
     *
     * @param params
     * @return
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if ("sign".equalsIgnoreCase(key) || StringUtils.isEmpty(value)) {
                continue;
            }
            if (i != 0) {
                stringBuilder.append("&");
            }

            stringBuilder.append(key).append("=").append(value);
        }

        return stringBuilder.toString();
    }

}
