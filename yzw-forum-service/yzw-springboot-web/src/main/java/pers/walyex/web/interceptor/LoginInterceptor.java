package pers.walyex.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author Waldron Ye
 * @date 2019/6/2 13:29
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //进行业务处理
        log.info("preHandle==");
        String url = request.getRequestURI();
        log.info("拦截的url={}", url);

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //当前请求进行处理之后，也就是Controller 方法调用之后执行.对应的视图之前
        log.info("postHandle===");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //当前请求进行处理之后，该方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。对应的视图之后.
        log.info("afterCompletion===");
    }

}
