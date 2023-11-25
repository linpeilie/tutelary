package cn.easii.tutelary.bean.domain;

import cn.easii.tutelary.common.domain.BaseDomain;
import lombok.Data;

@Data
public class Role extends BaseDomain {

    private String roleId;

    private String roleName;

    private Integer enableStatus;

    private String remark;

}
