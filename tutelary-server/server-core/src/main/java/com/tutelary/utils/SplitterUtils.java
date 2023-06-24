package com.tutelary.utils;

import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Lists;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

@Slf4j
public class SplitterUtils {

    /**
     * 将一个大的集合分隔为多个小集合的集合。各小集合中最大长度为count
     *
     * @param values 待分隔的大集合
     * @param count  每个小List中最大的数量
     * @param <T>
     * @return 分隔结果
     */
    public static <T> List<Collection<T>> split(Collection<T> values, int count) {
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }

        int total = values.size();
        log.info("SplitterUtils.split: values.size(): [{}], count: [{}]", total, count);

        // 如果待分隔的values大小不满足分隔要求
        if (total <= count) {
            List<Collection<T>> resultList = Lists.newArrayListWithExpectedSize(1);
            resultList.add(values);
            return resultList;
        }

        int number = total / count + (total % count > 0 ? 1 : 0);
        log.info("SplitterUtils.split: values.size(): [{}], count: [{}], batchNumber: [{}]", total, count, number);

        List<Collection<T>> resultList = Lists.newArrayListWithExpectedSize(number);

        Iterator<T> iterator = values.iterator();
        for (int i = 0; i < number; i++) {
            List<T> subList = Lists.newArrayList();
            while (subList.size() < count && iterator.hasNext()) {
                subList.add(iterator.next());
            }

            resultList.add(subList);
        }
        return resultList;
    }

    /**
     * 将一个大的集合分隔为多个小集合的集合。各小集合中最大长度为count
     *
     * @param values 待分隔的大集合
     * @param count  每个小List中最大的数量
     * @param clazz  元素类型
     * @param <T>
     * @return 分隔结果
     */
    public static <T> List<T[]> splitAsArray(Collection<T> values, Class<? extends T> clazz, int count) {
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }

        int total = values.size();
        log.info("SplitterUtils.splitAsArray: values.size(): [{}], count: [{}]", total, count);

        // 如果待分隔的values大小不满足分隔要求
        if (total <= count) {
            List<T[]> resultList = Lists.newArrayListWithExpectedSize(1);

            T[] array = (T[]) Array.newInstance(clazz, values.size());
            resultList.add(values.toArray(array));
            return resultList;
        }

        int number = total / count + (total % count > 0 ? 1 : 0);
        log.info("SplitterUtils.splitAsArray: values.size(): [{}], count: [{}], batchNumber: [{}]", total, count, number);

        List<T[]> resultList = Lists.newArrayListWithExpectedSize(number);
        Iterator<T> iterator = values.iterator();
        for (int i = 0; i < number; i++) {
            List<T> subList = Lists.newArrayList();
            while (subList.size() < count && iterator.hasNext()) {
                subList.add(iterator.next());
            }

            T[] array = (T[]) Array.newInstance(clazz, subList.size());
            resultList.add(subList.toArray(array));
        }
        return resultList;
    }

    /**
     * 将一个大的Map分隔为多个小Map的集合。各小Map中最大大小为count
     *
     * @param map   待分隔的大Map
     * @param count 每个小Map中最大的数量
     * @param <K>
     * @param <V>
     * @return 分隔结果
     */
    public static <K, V> List<Map<K, V>> split(Map<K, V> map, int count) {
        if (CollectionUtils.isEmpty(map)) {
            return Collections.emptyList();
        }

        int total = map.size();
        log.info("SplitterUtils.split: map.size(): [{}], count: [{}]", total, count);

        // 如果待分隔的values大小不满足分隔要求
        if (total <= count) {
            List<Map<K, V>> resultList = Lists.newArrayListWithExpectedSize(1);
            resultList.add(map);
            return resultList;
        }

        int number = total / count + (total % count > 0 ? 1 : 0);
        log.info("SplitterUtils.split: map.size(): [{}], count: [{}], batchNumber: [{}]", total, count, number);

        List<Map<K, V>> resultList = Lists.newArrayListWithExpectedSize(number);
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        for (int i = 0; i < number; i++) {
            Map<K, V> subMap = new HashMap<>();
            while (subMap.size() < count && iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                subMap.put(entry.getKey(), entry.getValue());
            }

            resultList.add(subMap);
        }
        return resultList;
    }

    /**
     * 将一个大的数据分隔为多个小数组的集合。各小List中最大长度为count
     *
     * @param datas 待分隔的大数组
     * @param count 每个小数组中最大的数量
     * @param <T>
     * @return 分隔结果
     */
    public static <T> List<T[]> split(T[] datas, int count) {
        if (ArrayUtil.isEmpty(datas)) {
            return Collections.emptyList();
        }

        int total = datas.length;
        log.info("SplitterUtils.split: datas.length: [{}], count: [{}]", total, count);

        // 如果待分隔的数组大小不满足分隔要求
        if (total <= count) {
            List<T[]> resultList = Lists.newArrayListWithExpectedSize(1);
            resultList.add(datas);
            return resultList;
        }

        int number = total / count + (total % count > 0 ? 1 : 0);
        log.info("SplitterUtils.split: datas.length: [{}], count: [{}], batchNumber: [{}]", total, count, number);

        List<T[]> resultList = Lists.newArrayListWithExpectedSize(number);
        for (int i = 0; i < number; i++) {
            int begin = i * count;
            int end = begin + count;
            resultList.add(Arrays.copyOfRange(datas, begin, end > total ? total : end));
        }
        return resultList;
    }
}
