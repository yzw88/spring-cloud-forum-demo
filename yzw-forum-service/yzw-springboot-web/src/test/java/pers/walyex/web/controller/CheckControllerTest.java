package pers.walyex.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import pers.walyex.web.YzwSpringbootWebApplicationTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class CheckControllerTest extends YzwSpringbootWebApplicationTests {

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
}