package com.faurecia.vo;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/18.
 */
@Data
public class Item {
    private Integer id;
    private String name;
    private Boolean isSelect=false;
}
