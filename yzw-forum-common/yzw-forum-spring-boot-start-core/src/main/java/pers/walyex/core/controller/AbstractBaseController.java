package pers.walyex.core.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制器基类
 *
 * @author Waldron Ye
 * @date 2019/12/28 9:18
 */
public abstract class AbstractBaseController {

    /**
     * 响应对象
     */
    protected HttpServletResponse response;

    /**
     * 请求对象
     */
    protected HttpServletRequest request;

    /**
     * 被@ModelAttribute注释的方法会在此controller每个方法执行前被执行
     *
     * @param request  请求对象
     * @param response 响应对象
     */
    @ModelAttribute
    private void handle(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        //共享上下文
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        RequestContextHolder.setRequestAttributes(attributes, true);
    }
}
