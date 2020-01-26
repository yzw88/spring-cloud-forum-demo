package pers.walyex.forum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.walyex.forum.aspect.LogAspect;

/**
 * 应用配置类
 *
 * @author Waldron Ye
 * @date 2020/1/26 12:23
 */
@Configuration
public class AppConfig {


    @Bean
    public LogAspect createLogAspect() {
        return new LogAspect();
    }
}
