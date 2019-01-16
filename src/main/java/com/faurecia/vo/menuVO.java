package com.faurecia.vo;

import lombok.Data;

/**
 * Created by Administrator on 2018/8/27.
 */
@Data
public class menuVO extends BaseArg {
    private Object id;
    private String name;
    private String dis;
    private String isDel;
    private Long delTime;



}
