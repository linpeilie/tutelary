package cn.easii.tutelary.bean.entity;

import cn.easii.tutelary.common.entity.BaseEntity;
import cn.easii.tutelary.installer.annotation.Column;
import cn.easii.tutelary.installer.annotation.Index;
import cn.easii.tutelary.installer.annotation.Table;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.easii.tutelary.bean.domain.App;
import io.github.linpeilie.annotations.AutoMapper;
import java.time.LocalDateTime;
import lombok.Data;

@TableName(value = "app")
@Data
@Table(comment = "应用", indexs = {
    @Index(unique = true, columns = {"app_name"})
})
@AutoMapper(target = App.class)
public class AppEntity extends BaseEntity {

    @Column(comment = "应用名称", isNull = false, sequence = 2, length = 64)
    private String appName;

    @Column(comment = "注册时间", isNull = false, sequence = 3)
    private LocalDateTime registerDate;

    @Column(comment = "实例数", isNull = false, sequence = 4, defaultValue = "0")
    private Integer instanceNum;

}
