package pers.walyex.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.walyex.common.core.util.ResultUtil;
import pers.walyex.common.util.FastJsonUtil;
import pers.walyex.core.init.MyTestBean;

import javax.annotation.Resource;

/**
 * 测试控制器
 *
 * @author Waldron Ye
 * @date 2020/5/2 9:22
 */
@RestController
@Slf4j
public class TestController {

    @Resource
    private MyTestBean myTestBean;

    /**
     * http://localhost:8080/check
     * @return object
     */
    @GetMapping("/check")
    public Object check() {
        log.info("check====");
        log.info("获取的bean为:myTestBean={}", FastJsonUtil.toJson(myTestBean));
        return ResultUtil.getSuccessResult(200);
    }
}
