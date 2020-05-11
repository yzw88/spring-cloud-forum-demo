package pers.walyex.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.walyex.common.core.util.ResultUtil;

@RestController
@Slf4j
public class Test2Controller {

    @GetMapping("/b/login")
    public Object login() {
        log.info("/b/login====");
        return ResultUtil.getSuccessResult("/b/login====");
    }
}
