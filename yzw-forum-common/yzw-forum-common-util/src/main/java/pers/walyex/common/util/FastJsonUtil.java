package pers.walyex.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * fastJson工具类
 *
 * @author Waldron Ye
 * @date 2019/5/30 22:06
 */
public class FastJsonUtil {


    /**
     * json字符串转换bean
     *
     * @param jsonStr json字符串
     * @param clazz   clazz
     * @param <T>     t
     * @return t
     */
    public static <T> T toBean(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * 转换为List
     *
     * @param jsonStr json字符串
     * @param clazz   clazz
     * @param <T>     t
     * @return List<T>
     */
    public static <T> List<T> toList(String jsonStr, Class<T> clazz) {
        return JSON.parseArray(jsonStr, clazz);
    }

    /**
     * 转换为map
     *
     * @param jsonStr json字符串
     * @param <K>     key
     * @param <V>     value
     * @return map
     */
    public static <K, V> Map<K, V> jsonStrToMap(String jsonStr) {
        Map<K, V> m = (Map<K, V>) JSONObject.parseObject(jsonStr);
        return m;
    }

    /**
     * 转换json
     *
     * @param object object
     * @return string
     */
    public static String toJson(Object object) {
        return JSON.toJSONString(object);
    }
}
