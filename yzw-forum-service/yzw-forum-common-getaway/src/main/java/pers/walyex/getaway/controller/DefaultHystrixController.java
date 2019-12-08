package pers.walyex.getaway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.walyex.common.core.util.ResultUtil;

/**
 * 默认降级处理
 *
 * @author Waldron Ye
 * @date 2019/5/24 20:49
 */
@RestController
@Slf4j
public class DefaultHystrixController {

    @RequestMapping("/defaultFallback")
    public Object defaultFallback() {
        log.info("服务降级 defaultFallback ===");
        return ResultUtil.getServiceErrorResult();
    }

}
