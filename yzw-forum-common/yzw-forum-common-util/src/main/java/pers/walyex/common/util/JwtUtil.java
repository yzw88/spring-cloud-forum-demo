package pers.walyex.common.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 *
 * @author Waldron Ye
 * @date 2019/5/31 19:56
 */
@Slf4j
public class JwtUtil {

    private static final byte[] SECRET_BYTE = "walyex:yezongwu20190531abcdefgabcd1234ABCD".getBytes();

    /**
     * 初始化head部分的数据为
     * {
     * "alg":"HS256",
     * "type":"JWT"
     * }
     */
    private static final JWSHeader HEADER = new JWSHeader(JWSAlgorithm.HS256, JOSEObjectType.JWT, null, null, null, null, null, null, null, null, null, null, null);

    /**
     * 生成token
     *
     * @param businessJson 业务json，可以存储用户id，token生成时间，token过期时间等自定义字段
     * @return string
     */
    public static String createToken(String businessJson) {

        String token = null;

        try {
            //创建一个 jwsObject
            JWSObject jwsObject = new JWSObject(HEADER, new Payload(businessJson));
            // 将jwsObject 进行HMAC签名
            jwsObject.sign(new MACSigner(SECRET_BYTE));
            token = jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("生成token异常:msg=[{}]", e);
        }

        return token;
    }

    /**
     * 解析token
     *
     * @param token token字符串
     * @return json string
     */
    public static String resolveToken(String token) {
        String resolveStr = null;
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            Payload payload = jwsObject.getPayload();
            JWSVerifier verifier = new MACVerifier(SECRET_BYTE);
            if (jwsObject.verify(verifier)) {
                JSONObject jsonObject = payload.toJSONObject();
                resolveStr = FastJsonUtil.toJson(jsonObject);
            } else {
                log.error("验签不通过:token=[{}]", token);
            }
        } catch (Exception e) {
            log.error("解析token异常:msg=[{}]", e);
        }
        return resolveStr;
    }


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("userId", 1);
        map.put("systemIdentity", "pay-test");
        map.put("platformType", "PC_WEB");
        String json = FastJsonUtil.toJson(map);
        System.out.println("装备创建token,json为：" + json);
        String token = createToken(json);
        System.out.println("创建token为：" + token);
        String resolveStr = resolveToken(token);
        System.out.println("解密token，得到resolveStr为：" + resolveStr);
    }
}
