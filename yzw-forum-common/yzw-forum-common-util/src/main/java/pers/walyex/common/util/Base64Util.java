package pers.walyex.common.util;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

/**
 * base64工具类
 *
 * @author Waldron Ye
 * @date 2019/6/2 14:18
 */

public class Base64Util {

    /**
     * 编码
     *
     * @param content 待编码的字符串
     * @return string
     */
    public static String encode(final String content) {
        return Base64.encodeBase64String(content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 编码
     *
     * @param data 待编码的字节数据
     * @return string
     */
    public static String encode(final byte[] data) {
        return Base64.encodeBase64String(data);
    }

    /**
     * 解码
     *
     * @param str 待解码的字符串
     * @return string
     */
    public static String decode(final String str) {
        return createUtf8Str(Base64.decodeBase64(str));
    }

    /**
     * 解码
     *
     * @param data 待解码的字节数组
     * @return string
     */
    public static String decode(final byte[] data) {
        return createUtf8Str(Base64.decodeBase64(data));
    }

    /**
     * 创建utf-8字符串
     *
     * @param data 字节数据
     * @return string
     */
    public static String createUtf8Str(final byte[] data) {
        return new String(data, StandardCharsets.UTF_8);
    }

    /**
     * 是否属于base64字符串
     *
     * @param base64Str base64字符串
     * @return boolean
     */
    public static boolean isBase64(final String base64Str) {
        return Base64.isBase64(base64Str);
    }

    /**
     * 是否不属于base64字符串
     *
     * @param base64Str base64字符串
     * @return boolean
     */
    public static boolean isNotBase64(final String base64Str) {
        return !isBase64(base64Str);
    }
}