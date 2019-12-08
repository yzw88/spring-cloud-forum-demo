package pers.walyex.getaway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关启动类
 *
 * @author Waldron Ye
 * @date 2019/12/7 14:47
 */
@SpringBootApplication
@EnableDiscoveryClient
public class YzwForumCommonGetawayApplication {

    public static void main(String[] args) {
        SpringApplication.run(YzwForumCommonGetawayApplication.class, args);
    }

}
