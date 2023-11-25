package cn.easii.tutelary.bean.entity;

import cn.easii.tutelary.common.entity.BaseEntity;
import cn.easii.tutelary.installer.annotation.Column;
import cn.easii.tutelary.installer.annotation.Index;
import cn.easii.tutelary.installer.annotation.Table;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.easii.tutelary.bean.domain.User;
import io.github.linpeilie.annotations.AutoMapper;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = User.class)
@TableName(value = "user")
@Table(comment = "用户信息", indexs = {
    @Index(unique = true, columns = "username"),
    @Index(unique = true, columns = "phone_number")
})
public class UserEntity extends BaseEntity {

    @Column(isNull = false, length = 32, comment = "用户ID", sequence = 2)
    private String userId;

    @Column(isNull = false, length = 32, comment = "用户账号", sequence = 3)
    private String username;

    @Column(isNull = false, length = 32, comment = "用户昵称", sequence = 4)
    private String nickName;

    @Column(isNull = false, length = 11, comment = "用户手机号", sequence = 5)
    private String phoneNumber;

    @Column(isNull = false, length = 64, comment = "密码", sequence = 6)
    @TableField(
        insertStrategy = FieldStrategy.NOT_EMPTY,
        updateStrategy = FieldStrategy.NOT_EMPTY,
        whereStrategy = FieldStrategy.NOT_EMPTY
    )
    private String password;

    @Column(isNull = false, length = 4, comment = "用户状态「00-正常；10-停用」", sequence = 7)
    private String state;

    @Column(length = 64, comment = "上次登录IP", sequence = 8)
    private String loginIp;

    @Column(comment = "上次登录时间", sequence = 9)
    private LocalDateTime loginDate;

    @Column(length = 128, comment = "备注", sequence = 10)
    private String remark;

}
