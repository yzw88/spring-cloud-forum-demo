package pers.walyex.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.walyex.order.feign.InventoryFeignClient;

@RestController
@RefreshScope
public class OrderController {

    @Value("${app.useLocalCache}")
    private String useLocalCache;

//    @Autowired
    private InventoryFeignClient inventoryFeignClient;

    @GetMapping("/get")
    public Object get() {
        return useLocalCache;
    }

/*    @GetMapping("/getInventory")
    public Object getInventory() {
        return this.inventoryFeignClient.getInventory();
    }*/
}
