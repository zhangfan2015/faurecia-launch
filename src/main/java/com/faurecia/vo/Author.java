package com.faurecia.vo;

import lombok.Data;

/**
 * Created by Administrator on 2018/8/24.
 */
@Data
public class Author extends BaseArg{
    private Object id;
    private String userName;
    private String pwd;
    private String role;
    private Integer adminId;
    private String scope;

}
