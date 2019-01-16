package com.faurecia.vo;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2017/3/12
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
@Data
public class BaseResult {

    public Long serviceTime = System.currentTimeMillis();
    public String message;
    public int status=1;//1 success -1 error 2 success且需要弹窗
    public Object bizObject;
    public Integer page;
    public Integer total;
    public Integer records;
    public List rows;

}
