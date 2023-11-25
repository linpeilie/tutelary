package cn.easii.tutelary.bean.entity;

import cn.easii.tutelary.bean.domain.Role;
import cn.easii.tutelary.common.entity.BaseEntity;
import cn.easii.tutelary.installer.annotation.Column;
import cn.easii.tutelary.installer.annotation.Table;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Role.class)
@TableName(value = "role")
@Table(comment = "角色表")
public class RoleEntity extends BaseEntity {

    @Column(isNull = false, length = 32, comment = "角色ID", sequence = 2)
    private String roleId;

    @Column(isNull = false, length = 32, comment = "角色名称", sequence = 3)
    private String roleName;

    @Column(isNull = false, length = 1, comment = "启用状态", sequence = 4)
    private Integer enableStatus;

    @Column(length = 500, comment = "备注", sequence = 5)
    private String remark;

}
