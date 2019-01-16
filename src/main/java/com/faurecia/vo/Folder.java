package com.faurecia.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/18.
 */
@Data
public class Folder {
    private Integer id;
    private String name;
    private Boolean isSelect=false;
    List<Item> items = new ArrayList<>();
}
