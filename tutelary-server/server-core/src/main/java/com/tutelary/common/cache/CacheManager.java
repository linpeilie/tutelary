package com.tutelary.common.cache;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface CacheManager {


    /**
     * 获取指定 key 的前缀，获取相应的 key 集合，每次请求 cache 时，都会尝试获取 count 数量的数据
     *
     * @param prefix cache key值前缀
     * @return
     */
    Iterable<String> getKeys(String prefix);

    /**
     * 设置指定 key 的数据存活时长（时间单位：毫秒）
     *
     * @param key        redis key的值
     * @param timeToLive 数据存活时间，时间单位：毫秒
     * @return 当 key 值所对应的数据存在，且过期时间设置成功时，返回true
     */
    boolean expire(String key, long timeToLive);

    /**
     * 设置指定key的数据存活时长
     *
     * @param key        redis key值
     * @param timeToLive 数据存活时间
     * @param timeUnit   数据存活时间timeToLive的时间单位
     * @return 当key值所对应的数据存在，且过期时间设置成功时，返回true
     */
    boolean expire(String key, long timeToLive, TimeUnit timeUnit);

    /**
     * 清除指定key的过期时间信息
     *
     * @param key cache key值
     * @return 当key值所对应的数据存在，且过期时间清除成功时，返回true
     */
    boolean clearExpire(String key);

    /**
     * 验证指定的key值是否存在
     *
     * @param key cache key值
     * @return true: 存在, false: 不存在
     */
    boolean hasKey(String key);

    /**
     * 查询指定key的数据
     *
     * @param key cache key值
     * @param <T>
     * @return key所对应的数据
     */
    <T> T get(String key);

    /**
     * 设置普通数据到cache中
     * 在后续不设置过期时间的情况下，该数据将永不过期
     *
     * @param key   cache key值
     * @param value 存储到cache中的数据
     * @param <V>
     */
    <V> void set(String key, V value);

    /**
     * 设置普通数据到cache中
     * 带有指定的存活时间（时间单位：毫秒）
     *
     * @param key        cache key值
     * @param value      存储到cache中的数据
     * @param timeToLive 数据存活时间，时间单位：毫秒
     * @param <V>
     */
    <V> void set(String key, V value, long timeToLive);

    /**
     * 设置普通数据到cache中
     * 带有指定的存活时间
     *
     * @param key        cache key值
     * @param value      存储到cache中的数据
     * @param timeToLive 数据存活时间
     * @param timeUnit   数据存活时间timeToLive的时间单位
     * @param <V>
     */
    <V> void set(String key, V value, long timeToLive, TimeUnit timeUnit);

    /**
     * 尝试设置普通数据到cache中，如果数据已存在，则返回false
     * 在后续不设置过期时间的情况下，该数据将永不过期
     *
     * @param key   cache key值
     * @param value 存储到cache中的数据
     * @param <V>
     * @return 当key值所对应的数据不存在，则设置成功时，返回true
     */
    <V> boolean trySet(String key, V value);

    /**
     * 尝试设置普通数据到cache中，如果数据已存在，则返回false
     * 带有指定的存活时间（时间单位：毫秒）
     *
     * @param key        cache key值
     * @param value      存储到cache中的数据
     * @param timeToLive 数据存活时间，时间单位：毫秒
     * @param <V>
     * @return 当key值所对应的数据不存在，则设置成功时，返回true
     */
    <V> boolean trySet(String key, V value, long timeToLive);

    /**
     * 尝试设置普通数据到cache中，如果数据已存在，则返回false
     * 带有指定的存活时间
     *
     * @param key        cache key值
     * @param value      存储到cache中的数据
     * @param timeToLive 数据存活时间，时间单位：毫秒
     * @param timeUnit   数据存活时间timeToLive的时间单位
     * @param <V>
     * @return 当key值所对应的数据不存在，则设置成功时，返回true
     */
    <V> boolean trySet(String key, V value, long timeToLive, TimeUnit timeUnit);

    /**
     * 删除指定key的数据
     *
     * @param keys cache key值
     * @return 返回成功删除的数量
     */
    long delete(String... keys);

    /**
     * 删除指定key的数据
     *
     * @param keys cache key集合
     * @return 返回成功删除的数量
     */
    long delete(Collection<String> keys);

    /**
     * 删除指定前缀key的数据
     *
     * @param prefix cache key值前缀
     * @return 实际删除的数量
     */
    long deleteByPrefix(String prefix);

    /**
     * 先递增1，然后再返回递增后的新值
     *
     * @param key cache key值
     * @return 变化之后的atomic值
     */
    long incrementAndGet(String key);

    /**
     * 先返回递增后的值，再递增1
     *
     * @param key
     * @return
     */
    long getAndIncrement(String key);

    /**
     * 保存集合到缓存中
     *
     * @param key        cache key值
     * @param collection 存储到cache中的集合数据
     * @return
     */
    boolean setList(String key, Collection<?> collection);

    /**
     * 获取集合
     *
     * @param key cache key值
     * @return
     */
    List<Object> getList(String key);

    /**
     * 保存 map 到缓存中
     *
     * @param key cache key值
     * @param map 存储到cache中的map数据
     * @return
     */
    <V> void setMap(String key, Map<String,? extends V> map);

    /**
     * 获取 map
     *
     * @param key cache key值
     * @return
     */
    <V> Map<String, V> getMap(String key);
}
