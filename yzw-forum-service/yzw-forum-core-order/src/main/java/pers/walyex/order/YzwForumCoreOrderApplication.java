package pers.walyex.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = {"pers.walyex.order.dao"})
public class YzwForumCoreOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(YzwForumCoreOrderApplication.class, args);
    }

}
