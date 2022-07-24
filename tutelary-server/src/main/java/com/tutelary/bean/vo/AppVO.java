package com.tutelary.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AppVO implements Serializable {

    private String appName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerDate;

}
