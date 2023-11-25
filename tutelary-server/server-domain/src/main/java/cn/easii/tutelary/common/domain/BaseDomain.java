package cn.easii.tutelary.common.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BaseDomain implements Serializable {

    private LocalDateTime createTime;

    private String createUserId;

    private LocalDateTime updateTime;

    private String updateUserId;

}
