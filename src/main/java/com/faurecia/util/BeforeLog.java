package com.faurecia.util;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/****
 * 前置增强：
 * MethodBeforeAdvice 接口表示重写的方法为前置advice
 * ***/
@Component
public interface BeforeLog
{
    public void cut();
    public void printTime();
    public void  getReturn();
}
