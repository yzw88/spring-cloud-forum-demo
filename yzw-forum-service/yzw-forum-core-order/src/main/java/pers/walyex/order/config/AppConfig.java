package pers.walyex.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.walyex.order.interceptor.GlobalRequestHandleInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createGlobalRequestHandleInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public GlobalRequestHandleInterceptor createGlobalRequestHandleInterceptor() {
        return new GlobalRequestHandleInterceptor();
    }

    /**
     * 静态资源访问配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {


        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");


    }
}
