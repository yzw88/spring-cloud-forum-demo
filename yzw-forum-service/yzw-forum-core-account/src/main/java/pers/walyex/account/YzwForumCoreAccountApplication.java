package pers.walyex.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * account服务启动类
 *
 * @author Waldron Ye
 * @date 2019/12/8 13:19
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class YzwForumCoreAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(YzwForumCoreAccountApplication.class, args);
    }

}
