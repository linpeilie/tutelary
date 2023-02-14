package com.tutelary.common.helper;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.tutelary.common.annotation.Query;
import com.tutelary.common.annotation.Sort;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class QueryMetaStore {

    private static final Map<Class<?>, List<QueryMeta>> QUERY_META_CACHE = new ConcurrentHashMap<>();

    public static List<QueryMeta> getQueryMeta(Class<?> clazz) {
        if (QUERY_META_CACHE.containsKey(clazz)) {
            return QUERY_META_CACHE.get(clazz);
        }
        Field[] fields = ReflectUtil.getFields(clazz);
        List<QueryMeta> list = Lists.newArrayListWithExpectedSize(fields.length);
        for (Field field : fields) {
            Query query = field.getAnnotation(Query.class);
            Sort sort = field.getAnnotation(Sort.class);
            if (query == null && sort == null) {
                continue;
            }
            QueryMeta queryMeta = new QueryMeta();
            queryMeta.setTargetClass(clazz);
            queryMeta.setQuery(query);
            queryMeta.setSort(sort);
            queryMeta.setField(field);

            String fieldName = field.getName();
            String readMethodName = StrUtil.upperFirstAndAddPre(fieldName, "get");
            Method readMethod = ReflectUtil.getMethodByName(clazz, readMethodName);
            if (readMethod == null) {
                readMethodName = StrUtil.upperFirstAndAddPre(fieldName, "is");
                readMethod = ReflectUtil.getMethodByName(clazz, readMethodName);
            }
            if (readMethod == null) {
                log.warn("class : {} , field : {} , 没有获取到 get/is 方法", clazz.getName(), fieldName);
                continue;
            }
            queryMeta.setReadMethod(readMethod);

            list.add(queryMeta);
        }
        QUERY_META_CACHE.putIfAbsent(clazz, list);
        return list;
    }

}
