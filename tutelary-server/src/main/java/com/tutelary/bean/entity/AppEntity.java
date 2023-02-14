package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tutelary.common.bean.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;

@TableName(value = "t_app")
@Data
public class AppEntity extends BaseEntity {

    private String appName;

    private LocalDateTime registerDate;

    private Integer instanceNum;

}
