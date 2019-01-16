package com.faurecia.vo;

import lombok.Data;

/**
 * Created by Administrator on 2018/8/27.
 */
@Data
public class LaunchVO extends BaseArg {
    private String name;
    private Integer indexNo;
    private Object id;
    private String dis;
    private String isDisplay;
    private Long createTime;
    private String isDel;
    private Long delTime;
    private String fileUrl;
    private String tempFileUrl;
    private String fileType;
    private String launchType;
    private Integer programId;


}
