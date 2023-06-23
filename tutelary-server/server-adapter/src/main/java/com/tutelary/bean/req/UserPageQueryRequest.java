package com.tutelary.bean.req;

import com.tutelary.bean.domain.query.UserQuery;
import com.tutelary.common.bean.req.PageQueryRequest;
import io.github.linpeilie.annotations.AutoMapper;

@AutoMapper(target = UserQuery.class, reverseConvertGenerate = false)
public class UserPageQueryRequest extends PageQueryRequest {
}
