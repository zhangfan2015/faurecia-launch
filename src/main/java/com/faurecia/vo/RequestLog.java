package com.faurecia.vo;

import com.google.gson.annotations.Expose;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 * Created by Administrator on 2018/10/9.
 */
@Data
public class RequestLog {
    @Expose
    private String methodName;
    @Expose
    private String ip;
    @JsonBackReference
    private Object[] param;


}
