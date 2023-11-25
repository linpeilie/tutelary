package cn.easii.tutelary.bean.req;

import cn.easii.tutelary.common.bean.req.AbstractRequest;
import cn.easii.tutelary.bean.domain.User;
import cn.easii.tutelary.common.utils.ParameterUtils;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Schema(name = "UserEditRequest", description = "用户信息编辑入参")
@AutoMapper(target = User.class, reverseConvertGenerate = false)
public class UserEditRequest extends AbstractRequest {

    @Schema(name = "userId", description = "用户ID")
    private String userId;

    @Schema(name = "nickName", description = "用户昵称")
    private String nickName;

    @Schema(name = "phoneNumber", description = "用户手机号")
    private String phoneNumber;

    @Schema(name = "remark", description = "备注")
    private String remark;

    @Override
    public void checkInput() {
        super.checkInput();
        ParameterUtils.notBlank(userId, "用户ID不能为空");
        ParameterUtils.notBlank(nickName, "用户昵称不能为空");
        ParameterUtils.notBlank(phoneNumber, "手机号不能为空");
    }
}