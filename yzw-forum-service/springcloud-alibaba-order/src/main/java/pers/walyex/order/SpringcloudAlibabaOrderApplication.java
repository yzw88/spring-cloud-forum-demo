package pers.walyex.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients
@Slf4j
public class SpringcloudAlibabaOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudAlibabaOrderApplication.class, args);

    }

    public static void main2(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringcloudAlibabaOrderApplication.class, args);
        while (true) {
            //当动态配置刷新时，会更新到 Enviroment中，因此这里每隔一秒中从Enviroment中获取配置
            String userName = applicationContext.getEnvironment().getProperty("user.name");
            String userAge = applicationContext.getEnvironment().getProperty("user.age");
            log.info("user name :" + userName + "; age: " + userAge);
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
