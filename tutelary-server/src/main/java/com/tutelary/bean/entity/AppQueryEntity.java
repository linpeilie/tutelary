package com.tutelary.bean.entity;

import com.tutelary.bean.base.PageRequest;

import lombok.Data;

@Data
public class AppQueryEntity extends PageRequest {

    private String appName;

}
