package pers.walyex.forum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = {"pers.walyex.forum.dao"})
@EnableScheduling
public class YzwSpringbootForumApplication {

    public static void main(String[] args) {
        SpringApplication.run(YzwSpringbootForumApplication.class, args);
    }

}
