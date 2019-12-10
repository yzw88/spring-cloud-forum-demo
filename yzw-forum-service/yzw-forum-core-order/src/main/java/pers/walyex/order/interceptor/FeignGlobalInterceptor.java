package pers.walyex.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * feign全局拦截器
 *
 * @author Waldron Ye
 * @date 2019/12/9 21:10
 */
@Component
@Slf4j
public class FeignGlobalInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        String traceId = request.getHeader("traceId");
        log.info("FeignGlobalInterceptor traceId={}", traceId);
        requestTemplate.header("traceId", traceId);
    }
}
