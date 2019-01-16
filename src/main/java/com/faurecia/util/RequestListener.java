package com.faurecia.util;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2018/1/27
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@Component
@WebListener
public class RequestListener implements ServletRequestListener {

    public void requestInitialized(ServletRequestEvent sre)  {
        //将所有request请求都携带上httpSession
//        System.out.println("listener touch");
        ((HttpServletRequest) sre.getServletRequest()).getSession();

    }
    public RequestListener() {
    }

    public void requestDestroyed(ServletRequestEvent arg0)  {
    }
}
