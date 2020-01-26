package pers.walyex.forum.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 正则校验工具类
 *
 * @author Waldron Ye
 * @date 2020/1/26 10:11
 */
public class RegexpUtil {

    /**
     * 邮箱正则表达式
     */
    public static final String REGEX_EMAIL_STR = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则校验是否匹配
     *
     * @param regex 正则表达式
     * @param str   待校验的字符串
     * @return true-匹配 false-不匹配
     */
    public static boolean isValid(String regex, String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        if (StringUtils.isBlank(regex)) {
            return false;
        }
        return Pattern.compile(regex).matcher(str).matches();
    }
}
