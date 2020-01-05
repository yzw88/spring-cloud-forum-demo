package pers.walyex.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import pers.walyex.common.util.FastJsonUtil;
import pers.walyex.web.YzwSpringbootWebApplicationTests;

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
        stringRedisTemplate.boundHashOps("my2").put("111","yezi");
        stringRedisTemplate.boundHashOps("my2").put("name","23432432");

        Map<Object,Object> map = stringRedisTemplate.boundHashOps("my2").entries();
        Optional.of(map).ifPresent(t->t.forEach((k,v)->{log.info("k={},v={}",k,v);}));

/*
        redisTemplate.boundListOps("namelist2").leftPush("刘备");
        redisTemplate.boundListOps("namelist2").leftPush("张飞");
        redisTemplate.boundListOps("namelist2").leftPush("关羽");*/

        List list = redisTemplate.boundListOps("namelist2").range(0,2);
        log.info("获取list={}", FastJsonUtil.toJson(list));
    }
}