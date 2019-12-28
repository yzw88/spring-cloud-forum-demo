package pers.walyex.core.filter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import pers.walyex.common.core.constant.CommonConstant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 日志过滤器
 *
 * @author Waldron Ye
 * @date 2019/6/2 13:21
 */
public class LogbackFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String traceId = httpServletRequest.getHeader("traceId");
        if (StringUtils.isBlank(traceId)) {
            traceId = RandomStringUtils.randomAlphanumeric(5);
        }
        MDC.put(CommonConstant.THREAD_ID, traceId);
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(CommonConstant.THREAD_ID);
        }
    }

    @Override
    public void destroy() {

    }
}
