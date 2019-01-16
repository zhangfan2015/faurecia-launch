package com.faurecia.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/27.
 */
@Data
public class RoleVO extends BaseArg {
    private Object id;
    private String name;
    private String dis;
    private String scope;
    private String isDel;
    private Long delTime;

}
