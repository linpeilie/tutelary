package cn.easii.tutelary.bean.req;

import cn.easii.tutelary.common.bean.req.PageQueryRequest;
import cn.easii.tutelary.bean.domain.query.UserQuery;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@AutoMapper(target = UserQuery.class, reverseConvertGenerate = false)
@Schema(name = "UserPageQueryRequest", description = "用户分页查询入参")
public class UserPageQueryRequest extends PageQueryRequest {

    @Schema(name = "username", description = "用户名")
    private String username;

}
