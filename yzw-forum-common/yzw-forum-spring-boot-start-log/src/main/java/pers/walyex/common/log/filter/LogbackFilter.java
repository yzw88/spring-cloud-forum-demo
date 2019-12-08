package pers.walyex.common.log.filter;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;

/**
 * 日志过滤器
 *
 * @author Waldron Ye
 * @date 2019/6/2 13:21
 */
public class LogbackFilter implements Filter {

    private static final String THREAD_ID = "threadId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        String uniqueId = THREAD_ID + "-" + RandomStringUtils.randomAlphanumeric(5);
        MDC.put(THREAD_ID, uniqueId);
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(THREAD_ID);
        }
    }

    @Override
    public void destroy() {

    }
}
