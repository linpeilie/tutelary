package cn.easii.tutelary.bean.entity;

import cn.easii.tutelary.bean.domain.UserRole;
import cn.easii.tutelary.common.entity.BaseEntity;
import cn.easii.tutelary.installer.annotation.Column;
import cn.easii.tutelary.installer.annotation.Index;
import cn.easii.tutelary.installer.annotation.Table;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AutoMapper(target = UserRole.class)
@TableName(value = "user_role")
@Table(comment = "用户角色关联表", indexs = {
    @Index(indexName = "idx_user_role", unique = true, columns = {"user_id", "role_id"})
})
public class UserRoleEntity {

    @Column(isNull = false, length = 32, comment = "用户ID", sequence = 1)
    private String userId;

    @Column(isNull = false, length = 32, comment = "角色ID", sequence = 2)
    private String roleId;

}
