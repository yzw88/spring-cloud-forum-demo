package pers.walyex.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController2 {

    @Value("${app.useLocalCache2:11}")
    private String useLocalCache;

    @GetMapping("/get2")
    public Object get() {
        return useLocalCache;
    }
}
