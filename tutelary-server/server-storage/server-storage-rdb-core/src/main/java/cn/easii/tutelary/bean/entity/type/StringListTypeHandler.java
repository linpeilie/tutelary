package cn.easii.tutelary.bean.entity.type;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import java.util.List;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringListTypeHandler extends AbstractJsonTypeHandler<List<String>> {
    @Override
    protected List<String> parse(String json) {
        return JSONUtil.toList(json, String.class);
    }

    @Override
    protected String toJson(List<String> obj) {
        return JSONUtil.toJsonStr(obj);
    }
}
