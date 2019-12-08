package pers.walyex.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 库存核心服务启动类
 *
 * @author Waldron Ye
 * @date 2019/12/8 14:16
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class YzwForumCoreInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(YzwForumCoreInventoryApplication.class, args);
    }

}
