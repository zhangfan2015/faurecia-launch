package com.faurecia.vo;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonBackReference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2018/1/1
 * Time: 12:49
 * To change this template use File | Settings | File Templates.
 */
@Data
public class BaseArg {

    private HttpSession session;
    private int lastId;
    private int page;
    private int rows;
    private String oper;
    private String sord;
    private String sidx;
    private String filters;


    private String nickName;
    private String avatarUrl;
    private String gender;
    private String province;
    private String userInfo;
    private String city;
    private String country;
    private String openid;
}
