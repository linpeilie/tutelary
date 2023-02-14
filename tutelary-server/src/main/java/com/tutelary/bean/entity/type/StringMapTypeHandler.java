package com.tutelary.bean.entity.type;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import java.util.Map;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringMapTypeHandler extends AbstractJsonTypeHandler<Map<String, String>> {
    @Override
    protected Map<String, String> parse(String json) {
        return JSONUtil.toBean(json, new TypeReference<Map<String, String>>() {
        }, true);
    }

    @Override
    protected String toJson(Map<String, String> obj) {
        return JSONUtil.toJsonStr(obj);
    }
}
