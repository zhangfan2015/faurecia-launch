package com.faurecia.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/19.
 */
@Data
public class Filter {
    private String groupOp;
    private Rule[] rules;
}
