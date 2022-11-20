package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tutelary.common.bean.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@TableName (value = "t_app")
@Data
public class AppEntity extends BaseEntity {

    private String appName;

    private LocalDateTime registerDate;

    private Integer instanceNum;

}
