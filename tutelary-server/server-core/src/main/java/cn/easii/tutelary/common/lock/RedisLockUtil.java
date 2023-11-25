package cn.easii.tutelary.common.lock;

import cn.easii.tutelary.common.transaction.TransactionPostProcessor;
import cn.hutool.core.util.StrUtil;
import cn.easii.tutelary.common.exception.SystemBusyException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisLockUtil implements LockUtil {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private TransactionPostProcessor transactionPostProcessor;

    @Value("${redis.lock.waitTime:5000}")
    private long waitTime;

    @Value("${redis.lock.leaseTime:-1}")
    private long leaseTime;

    @Override
    public boolean tryLock(String lockKey) {
        return tryLock(waitTime, lockKey);
    }

    @Override
    public boolean tryLock(long waitTime, String lockKey) {
        return tryLock(waitTime, leaseTime, lockKey);
    }

    @Override
    public boolean tryLock(long waitTime, long leaseTime, String lockKey) {
        if (StrUtil.isBlank(lockKey)) {
            return true;
        }
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("获取锁失败，lockKey : [ {} ]", lockKey);
            throw new SystemBusyException();
        }
    }

    @Override
    public void unlock(String lockKey) {
        if (StrUtil.isBlank(lockKey)) {
            return;
        }
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public <Param> void executeWithLock(String lockKey, Consumer<Param> consumer, Param param) {
        this.executeWithLock(waitTime, lockKey, consumer, param);
    }

    @Override
    public <Param> void executeWithLock(long waitTime, String lockKey, Consumer<Param> consumer, Param param) {
        this.executeWithLock(waitTime, leaseTime, lockKey, consumer, param);
    }

    @Override
    public <Param> void executeWithLock(long waitTime, long leaseTime, String lockKey, Consumer<Param> consumer, Param param) {
        this.doLock(waitTime, leaseTime, lockKey, p -> {
            consumer.accept(p);
            return null;
        }, param);
    }

    @Override
    public <Result> Result executeWithLock(String lockKey, Supplier<Result> supplier) {
        return this.executeWithLock(waitTime, lockKey, supplier);
    }

    @Override
    public <Result> Result executeWithLock(long waitTime, String lockKey, Supplier<Result> supplier) {
        return this.executeWithLock(waitTime, leaseTime, lockKey, supplier);
    }

    @Override
    public <Result> Result executeWithLock(long waitTime, long leaseTime, String lockKey, Supplier<Result> supplier) {
        return this.doLock(waitTime, leaseTime, lockKey, o -> supplier.get(), null);
    }

    /**
     * 获取锁，执行方法后，自动释放锁
     *
     * @param lockKey  锁的key
     * @param function 执行函数
     * @param t        入参
     * @return function 函数执行的返回参数
     */
    @Override
    public <Param, Result> Result executeWithLock(String lockKey, Function<Param, Result> function, Param t) {
        return this.executeWithLock(waitTime, leaseTime, lockKey, function, t);
    }

    /**
     * 获取锁，执行方法后，自动释放锁
     *
     * @param waitTime 获取锁等待时间
     * @param lockKey  锁的key
     * @param function 执行函数
     * @param t        入参
     * @return function 函数执行的返回参数
     */
    @Override
    public <Param, Result> Result executeWithLock(long waitTime, String lockKey, Function<Param, Result> function, Param t) {
        return this.executeWithLock(waitTime, leaseTime, lockKey, function, t);
    }

    /**
     * 获取锁，执行方法后，自动释放锁
     *
     * @param waitTime  获取锁等待时间
     * @param leaseTime 获取到的锁，超时释放时间
     * @param lockKey   锁的key
     * @param function  执行函数
     * @param t         入参
     * @return function 函数执行的返回参数
     */
    @Override
    public <Param, Result> Result executeWithLock(long waitTime, long leaseTime, String lockKey, Function<Param, Result> function, Param t) {
        return doLock(waitTime, leaseTime, lockKey, function, t);
    }

    public <Param, Result> Result doLock(long waitTime, long leaseTime, String lockKey, Function<Param, Result> function, Param t) {
        boolean locked = tryLock(waitTime, leaseTime, lockKey);
        if (locked) {
            try {
                return function.apply(t);
            } finally {
                unlock(lockKey);
            }
        }
        throw new SystemBusyException();
    }

}