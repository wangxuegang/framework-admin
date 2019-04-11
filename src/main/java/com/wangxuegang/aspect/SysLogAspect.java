package com.wangxuegang.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wangxuegang.model.SysLog;
import com.wangxuegang.service.log.SysLogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.Date;

/**
 * 日志处理
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    private static Logger LOGGER = LoggerFactory.getLogger(SysLogAspect.class);
    
    SysLog sysLog = null;
    
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.wangxuegang.controller..*.*(..))")
    public void sysLog(){}


    @Before("sysLog()")
    public void doBefore(JoinPoint joinPoint){
    	
    	sysLog = new SysLog();
    	
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        
        // seesion中获取用户信息
        // HttpSession session = request.getSession();
        
        // 记录下请求内容
        LOGGER.info("URL : " + request.getRequestURL().toString());
        LOGGER.info("HTTP_METHOD : " + request.getMethod());
        LOGGER.info("IP : " + request.getRemoteAddr());
        LOGGER.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOGGER.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        
        sysLog.setUsername("wangxuegang");
        sysLog.setOperation("");
        sysLog.setMethod(request.getMethod());
        sysLog.setParams("");
        sysLog.setIp(request.getRemoteAddr());
    }

    @AfterReturning(returning = "ret", pointcut = "sysLog()")
    public void doAfterReturning(Object ret) throws Throwable {
    	
    	long time = System.currentTimeMillis() - startTime.get();
    	
        // 处理完请求，返回内容
        LOGGER.info("RESPONSE : " + ret);
        LOGGER.info("SPEND TIME : " + time);
        
        sysLog.setTime((int) time);
        sysLog.setCreateDate(new Date());
        sysLogService.addSysLog(sysLog);
        
        // 用完之后记得清除，不然可能导致内存泄露;
        startTime.remove();
    }

}
