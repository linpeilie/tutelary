package com.tutelary.common.helper;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tutelary.common.annotation.Query;
import com.tutelary.common.annotation.Sort;
import com.tutelary.common.enums.QueryType;
import com.tutelary.common.enums.SortDirection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MybatisPlusQueryHelper {

    public static <T, V> LambdaQueryWrapper<T> buildLambdaQueryWrapper(V queryParam) {
        QueryWrapper<T> queryWrapper = buildQueryWrapper(queryParam);
        return queryWrapper.lambda();
    }

    public static <T, V> QueryWrapper<T> buildQueryWrapper(V queryParam) {
        QueryWrapper<T> queryWrapper = Wrappers.query();
        if (queryParam == null) {
            return queryWrapper;
        }
        try {
            List<QueryMeta> queryMetas = QueryMetaStore.getQueryMeta(queryParam.getClass());
            for (QueryMeta queryMeta : queryMetas) {
                // 排序条件
                Sort sort = queryMeta.getSort();
                if (sort != null) {
                    SortDirection direction = sort.direction();
                    String fieldName = StringUtils.camelToUnderline(queryMeta.getField().getName());
                    if (SortDirection.ASC.equals(direction)) {
                        queryWrapper.orderByAsc(fieldName);
                    } else {
                        queryWrapper.orderByDesc(fieldName);
                    }
                }

                // 查询条件
                Query query = queryMeta.getQuery();
                if (query == null) {
                    continue;
                }
                Object fieldValue = ReflectUtil.invoke(queryParam, queryMeta.getReadMethod());
                if (fieldValue == null) {
                    continue;
                }
                if (StrUtil.isBlankIfStr(fieldValue)) {
                    continue;
                }
                if (fieldValue instanceof Collection<?> && CollectionUtil.isEmpty((Collection<?>) fieldValue)) {
                    continue;
                }

                // 属性名
                String queryField = query.field();
                // 如果没有定义字段，则使用属性名，驼峰转下划线的方式
                queryField = StrUtil.isBlank(queryField) ? StringUtils.camelToUnderline(queryMeta.getField().getName())
                                                         : queryField;
                QueryType queryType = query.queryType();
                switch (queryType) {
                    case EQ:
                        queryWrapper.eq(queryField, fieldValue);
                        break;
                    case NE:
                        queryWrapper.ne(queryField, fieldValue);
                        break;
                    case GE:
                        queryWrapper.ge(queryField, fieldValue);
                        break;
                    case GT:
                        queryWrapper.gt(queryField, fieldValue);
                        break;
                    case LE:
                        queryWrapper.le(queryField, fieldValue);
                        break;
                    case LT:
                        queryWrapper.lt(queryField, fieldValue);
                        break;
                    case LIKE:
                        queryWrapper.like(queryField, fieldValue);
                        break;
                    case LIKE_LEFT:
                        queryWrapper.likeLeft(queryField, fieldValue);
                        break;
                    case LIKE_RIGHT:
                        queryWrapper.likeRight(queryField, fieldValue);
                        break;
                    case IN:
                        queryWrapper.in(queryField, (Collection) fieldValue);
                        break;
                    case BETWEEN:
                        List<Object> list = new ArrayList<>((Collection<?>) fieldValue);
                        queryWrapper.between(list.size() == 2, queryField, list.get(0), list.get(1));
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            log.error("MybatisPlusQueryHelper.buildQueryWrapper 构建查询条件异常", e);
        }
        return queryWrapper;
    }

}
