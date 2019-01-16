package com.faurecia.controller;

import com.faurecia.vo.BaseArg;
import com.faurecia.vo.BaseResult;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/8/21.
 */
@Controller(value="/menu")
public class MenuController {

    @ResponseBody
    @RequestMapping(value="/getMenuListByRole",name = "查询",produces = "text/json;charset=UTF-8",method = RequestMethod.GET )
    public  String getMenuListByRole()throws Exception{
        BaseResult result = new BaseResult();
        try {
            StackTraceElement Elements = Thread.currentThread().getStackTrace()[1];
            String methodName =Elements.getMethodName();
            String className = Elements.getClassName();
            Method[] methods =Class.forName(className).getMethods();
            for (Method m:methods){
                if(m.getName().equals(methodName)){
                    System.out.print(m.getAnnotation(RequestMapping.class).name());
                }
            }
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }
}
