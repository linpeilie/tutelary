package com.tutelary.bean.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppEntity implements Serializable {

    private String appName;

    private Date registerDate;
}
