package pers.walyex.forum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"pers.walyex.forum.dao"})
public class YzwSpringbootForumApplication {

    public static void main(String[] args) {
        SpringApplication.run(YzwSpringbootForumApplication.class, args);
    }

}
