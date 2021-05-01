package pers.walyex.order.feign;

//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//@FeignClient(value = "springcloud-alibaba-inventory")
public interface InventoryFeignClient {

    @GetMapping("/getInventory")
    Object getInventory();
}
