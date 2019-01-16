package com.faurecia.util;

import com.faurecia.vo.RequestLog;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

    /**
     * 前置通知：入参打印
     * @param point
     */
    @Before("execution(* com.faurecia.controller.*.*(..))")
    public void qztz(JoinPoint point){
        try {
            RequestLog logInfo = new RequestLog();
            logInfo.setMethodName(point.getSignature().getName());
            logInfo.setParam(point.getArgs());
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

}
