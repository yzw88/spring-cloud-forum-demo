package pers.walyex.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.walyex.common.core.util.ResultUtil;

@RestController
@Slf4j
public class Test1Controller {

    @GetMapping("/a/login")
    public Object login() {
        log.info("/a/login====");
        return ResultUtil.getSuccessResult("/a/login====");
    }
}
