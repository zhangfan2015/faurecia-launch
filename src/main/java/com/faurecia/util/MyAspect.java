package com.faurecia.util;

import com.faurecia.dao.BaseDao;
import com.faurecia.vo.RequestLog;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/10/8.
 */
@Aspect
@Component("annotationTest")
public class MyAspect {
    private final static Logger INFO_LOG = LoggerFactory.getLogger("infoLog");
    private final static Logger ERROR_LOG = LoggerFactory.getLogger("errorLog");


    @Autowired
    private BaseDao dao;
    /**
     * 前置通知：入参打印
     * @param point
     */
    @Before("execution(* com.faurecia.controller.*.*(..))")
    public void qztz(JoinPoint point){
        try {
            //获取RequestAttributes
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            //从获取RequestAttributes中获取HttpServletRequest的信息
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            RequestLog logInfo = new RequestLog();
            request.getRemoteHost();
            logInfo.setMethodName(point.getSignature().getName());
            logInfo.setParam(point.getArgs());
            logInfo.setIp(getIpAddress(request));
            INFO_LOG.info(new Gson().toJson(logInfo));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 后置通知：返回值打印
     * @param point
     * @param returnVal
     */
    @AfterReturning(value="execution(* com.faurecia.controller.*.*(..))",returning = "returnVal")
    public void AfterReturning(JoinPoint point,Object returnVal){
        INFO_LOG.info("method: "+point.getSignature().getName()+":return: "+new Gson().toJson(returnVal));
    }
//
//

    /**
     * 环绕通知：方法运行时间统计
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.faurecia.controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.nanoTime();
        Object obj= (Object) joinPoint.proceed();
        Long endTime = System.nanoTime();
        INFO_LOG.info(joinPoint.getSignature().getName()+"-complete cost:"+String.valueOf((endTime-startTime)/1000000));
        return obj;
    }

    /**
     * 异常打印
     * @param e
     */
    @AfterThrowing(value="execution(* com.faurecia.*.*.*(..))",throwing = "e")
    public void afterThrowable(Throwable e){
        ERROR_LOG.error("error:",e);
    }


    public static String getIpAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }

}
