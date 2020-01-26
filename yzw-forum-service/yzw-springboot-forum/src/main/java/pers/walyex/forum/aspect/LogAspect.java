package pers.walyex.forum.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.walyex.common.util.FastJsonUtil;
import pers.walyex.forum.service.SysLogService;
import pers.walyex.order.model.SysLog;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日志切面
 *
 * @author Waldron Ye
 * @date 2020/1/26 12:22
 */
@Aspect
@Slf4j
public class LogAspect {

    @Resource
    private SysLogService sysLogService;

    @Pointcut("execution(* pers.walyex.forum.controller..*.*(..))")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time);

        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("请求类名:{},请求方法名:{},执行时间:{}", className, methodName, time);
        //请求的参数
        Object[] args = joinPoint.getArgs();
        List<Object> argsList = new ArrayList<>();

        for (Object o : args) {
            if (o instanceof HttpServletResponse ||
                    o instanceof HttpServletRequest) {
            } else {
                argsList.add(o);
            }
        }

        String json = FastJsonUtil.toJson(argsList);
        log.info("请求参数为:json={}", json);

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        //获取request
        HttpServletRequest request = sra.getRequest();

        SysLog sysLog = new SysLog();
        sysLog.setUsername("admin");
        sysLog.setOperation(className);
        sysLog.setMethod(methodName);
        sysLog.setParams(json);
        sysLog.setTime(time);
        sysLog.setCreateDate(new Date());
        sysLog.setIp(this.getIpAddress(request));
        this.sysLogService.addSysLog(sysLog);

    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".endsWith(ip)) {
            return "127.0.0.1";
        }
        return ip;
    }
}
