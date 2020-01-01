package pers.walyex.core.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import pers.walyex.core.exception.GlobalExceptionHandler;
import pers.walyex.core.filter.LogbackFilter;

import javax.servlet.Filter;

/**
 * log配置
 *
 * @author Waldron Ye
 * @date 2019/12/8 17:02
 */
public class AppConfig {

    /**
     * 创建一个logbackFilter bean
     *
     * @return filter
     */
    @Bean(name = "logbackFilter")
    public Filter logbackFilter() {
        return new LogbackFilter();
    }

    /**
     * 注册logbackFilter
     *
     * @return filterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<Filter> registerLogbackFilter(@Qualifier("logbackFilter") Filter filter) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setName("logbackFilter");
        registration.setOrder(-1);
        return registration;
    }

    /**
     * 全局异常处理
     *
     * @return globalExceptionHandler
     */
    @Bean
    public GlobalExceptionHandler createGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

}
