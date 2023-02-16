package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tutelary.bean.domain.App;
import com.tutelary.common.entity.BaseEntity;
import com.tutelary.installer.annotation.Column;
import com.tutelary.installer.annotation.Index;
import com.tutelary.installer.annotation.Table;
import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import io.github.zhaord.mapstruct.plus.annotations.AutoMapSpring;
import java.time.LocalDateTime;
import lombok.Data;

@TableName(value = "app")
@Data
@Table(comment = "应用", indexs = {
    @Index(unique = true, columns = {"app_name"})
})
@AutoMap(targetType = App.class)
public class AppEntity extends BaseEntity {

    @Column(comment = "应用名称", isNull = false, sequence = 2, length = 64)
    private String appName;

    @Column(comment = "注册时间", isNull = false, sequence = 3)
    private LocalDateTime registerDate;

    @Column(comment = "实例数", isNull = false, sequence = 4, defaultValue = "0")
    private Integer instanceNum;

}
