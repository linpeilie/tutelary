package com.tutelary.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AppVO implements Serializable {

    private String appName;

    private LocalDateTime registerDate;

    private Integer instanceNum;

}
