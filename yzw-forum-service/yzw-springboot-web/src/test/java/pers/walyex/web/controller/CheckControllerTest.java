package pers.walyex.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import pers.walyex.common.util.CertUtil;
import pers.walyex.common.util.FastJsonUtil;
import pers.walyex.common.util.HttpClientUtil;
import pers.walyex.common.util.RSAUtil;
import pers.walyex.web.YzwSpringbootWebApplicationTests;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class CheckControllerTest extends YzwSpringbootWebApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void check() throws Exception {

        String responseString = mockMvc.perform(
                get("/check")    //请求的url,请求的方法是get
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)  //数据的格式
                        .param("pcode", "root")         //添加参数
        ).andExpect(status().isOk())    //返回的状态是200
                .andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
        log.info("响应数据:responseString={}", responseString);
    }

    @Test
    public void redisTest() {
        stringRedisTemplate.boundHashOps("my2").put("111", "yezi");
        stringRedisTemplate.boundHashOps("my2").put("name", "23432432");

        Map<Object, Object> map = stringRedisTemplate.boundHashOps("my2").entries();
        Optional.of(map).ifPresent(t -> t.forEach((k, v) -> {
            log.info("k={},v={}", k, v);
        }));

/*
        redisTemplate.boundListOps("namelist2").leftPush("刘备");
        redisTemplate.boundListOps("namelist2").leftPush("张飞");
        redisTemplate.boundListOps("namelist2").leftPush("关羽");*/

        List list = redisTemplate.boundListOps("namelist2").range(0, 2);
        log.info("获取list={}", FastJsonUtil.toJson(list));
    }

    @Test
    public void rsaTest() {
        String json = "{\n" +
                "    \"name\": \"BeJson\",\n" +
                "    \"url\": \"http://www.bejson.com\",\n" +
                "    \"page\": 88,\n" +
                "    \"isNonProfit\": true,\n" +
                "    \"address\": {\n" +
                "        \"street\": \"科技园路.\",\n" +
                "        \"city\": \"江苏苏州\",\n" +
                "        \"country\": \"中国\"\n" +
                "    },\n" +
                "    \"links\": [\n" +
                "        {\n" +
                "            \"name\": \"Google\",\n" +
                "            \"url\": \"http://www.google.com\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Baidu\",\n" +
                "            \"url\": \"http://www.baidu.com\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"SoSo\",\n" +
                "            \"url\": \"http://www.SoSo.com\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        String charset = "UTF-8";
        String appId = "test001";
        // 证书密码
        String keyStorePwd = "";
        // 证书类型
        String creType = CertUtil.CERTYPE_PFX;

        String cerPath = "D:\\cert\\rsa\\public-rsa.cer";
        String pfxPath = "D:\\cert\\rsa\\user-rsa.pfx";

        try {
            PrivateKey privateKey = (PrivateKey) CertUtil.getKey(pfxPath, creType, keyStorePwd, true);
            PublicKey bizPublicKey = CertUtil.getPublicKey(cerPath);
            String encryptJson = RSAUtil.encrypt(privateKey, bizPublicKey, charset, appId, json);
            log.info("加密的字符串为:encryptJson{}", encryptJson);

            String decryptJson = RSAUtil.decry(privateKey, bizPublicKey, encryptJson);
            log.info("解密的字符串为:decryptJson{}", decryptJson);
        } catch (Exception e) {
            log.error("加解密异常", e);
        }
    }

    @Test
    public void httpTest() {
        String url = "http://192.168.31.214:18182/check";
        try {
            String result = HttpClientUtil.doGet(url);
            log.info("请求结果:result={}", result);
        } catch (Exception e) {
            log.info("请求异常", e);
        }

    }
}