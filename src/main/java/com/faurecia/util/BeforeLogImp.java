package com.faurecia.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/****
 * 前置增强：
 * MethodBeforeAdvice 接口表示重写的方法为前置advice
 * ***/
@Component
public class BeforeLogImp implements BeforeLog{
    public void cut() {
        System.out.print("123");
    }
    public void printTime()
    {
        System.out.println("CurrentTime = " + System.currentTimeMillis());
    }
    public void  getReturn()
    {
        System.out.println("ReturnValue = ");
    }

    public static void main(String[] args) {
        BeforeLog log = new BeforeLogImp();
        log.printTime();
    }



}
