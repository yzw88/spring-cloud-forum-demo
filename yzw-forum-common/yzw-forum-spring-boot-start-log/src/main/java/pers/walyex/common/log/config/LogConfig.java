package pers.walyex.common.log.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import pers.walyex.common.log.filter.LogbackFilter;

import javax.servlet.Filter;

/**
 * log配置
 *
 * @author Waldron Ye
 * @date 2019/12/8 17:02
 */
public class LogConfig {

    /**
     * 注册logbackFilter
     *
     * @return filterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean registerLogbackFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(logbackFilter());
        registration.addUrlPatterns("/*");
        registration.setName("logbackFilter");
        registration.setOrder(-1);
        return registration;
    }

    /**
     * 创建一个logbackFilter bean
     *
     * @return filter
     */
    @Bean(name = "logbackFilter")
    public Filter logbackFilter() {
        return new LogbackFilter();
    }

}
