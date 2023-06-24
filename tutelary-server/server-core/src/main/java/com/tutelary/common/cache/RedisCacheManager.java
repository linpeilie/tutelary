package com.tutelary.common.cache;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.tutelary.utils.SplitterUtils;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheManager implements CacheManager {

    /**
     * 默认超时时间单位，毫秒
     */
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;

    private static final String ASTERISK = "*";

    /**
     * 批量操作时，一次批量提交的命令中命令的个数
     */
    private static final int BATCH_SIZE = 10;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public Iterable<String> getKeys(String prefix) {
        RKeys keys = redissonClient.getKeys();
        String redisKey = prefix + ASTERISK;
        return keys.getKeysByPattern(redisKey);
    }

    @Override
    public boolean expire(String key, long timeToLive) {
        return this.expire(key, timeToLive, DEFAULT_TIME_UNIT);
    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redissonClient.getKeys().expire(key, timeout, timeUnit);
    }

    @Override
    public boolean clearExpire(String key) {
        return redissonClient.getKeys().clearExpire(key);
    }

    @Override
    public boolean hasKey(String key) {
        return redissonClient.getKeys().remainTimeToLive(key) > -2;
    }

    @Override
    public <T> T get(String key) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    @Override
    public <V> void set(String key, V value) {
        redissonClient.getBucket(key).set(value);
    }

    @Override
    public <V> void set(String key, V value, long timeToLive) {
        this.set(key, value, timeToLive, DEFAULT_TIME_UNIT);
    }

    @Override
    public <V> void set(String key, V value, long timeToLive, TimeUnit timeUnit) {
        redissonClient.getBucket(key).set(value, timeToLive, timeUnit);
    }

    @Override
    public <V> boolean trySet(String key, V value) {
        return redissonClient.getBucket(key).trySet(value);
    }

    @Override
    public <V> boolean trySet(String key, V value, long timeToLive) {
        return this.trySet(key, value, timeToLive, DEFAULT_TIME_UNIT);
    }

    @Override
    public <V> boolean trySet(String key, V value, long timeToLive, TimeUnit timeUnit) {
        return redissonClient.getBucket(key).trySet(value, timeToLive, timeUnit);
    }

    private long delete(int batchSize, String... keys) {
        RKeys rKeys = redissonClient.getKeys();
        if (keys.length < batchSize) {
            return rKeys.delete(keys);
        }
        return SplitterUtils.split(keys, batchSize).stream().map(rKeys::delete)
            .reduce(Long::sum).orElse(0L);
    }

    @Override
    public long delete(String... keys) {
        if (ArrayUtil.isEmpty(keys)) {
            return 0;
        }
        return this.delete(BATCH_SIZE, keys);
    }

    @Override
    public long delete(Collection<String> keys) {
        if (CollectionUtil.isEmpty(keys)) {
            return 0;
        }
        String[] redisKeys = keys.toArray(new String[0]);
        return this.delete(keys);
    }

    @Override
    public long deleteByPrefix(String prefix) {
        String key = prefix + ASTERISK;
        return redissonClient.getKeys().deleteByPattern(key);
    }

    @Override
    public long incrementAndGet(String key) {
        return redissonClient.getAtomicLong(key).incrementAndGet();
    }

    @Override
    public long getAndIncrement(String key) {
        return redissonClient.getAtomicLong(key).getAndIncrement();
    }

    @Override
    public boolean setList(String key, Collection<?> collection) {
        return redissonClient.getList(key).addAll(collection);
    }

    @Override
    public List<Object> getList(String key) {
        return redissonClient.getList(key).readAll();
    }

    @Override
    public <V> void setMap(String key, Map<String, ? extends V> map) {
        redissonClient.getMap(key).putAll(map);
    }

    @Override
    public <V> Map<String, V> getMap(String key) {
        return redissonClient.getMap(key);
    }
}
