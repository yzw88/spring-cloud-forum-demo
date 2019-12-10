package pers.walyex.order.interceptor;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局请求处理拦截器
 *
 * @author Waldron Ye
 * @date 2019/12/10 22:49
 */
public class GlobalRequestHandleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //共享上下文
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        RequestContextHolder.setRequestAttributes(attributes, true);
        return true;
    }
}
